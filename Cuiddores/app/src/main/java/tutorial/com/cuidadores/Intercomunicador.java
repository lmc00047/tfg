package tutorial.com.cuidadores;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
/**
 * Esta clase provee a la aplicación de la funcionalidad de un intercomunicador entre el usuario y
 * el cuidador. Para ello se utilizan dos hebras, una para la transmisión de mensajes de voz
 * enviados por el usuario mediante UDP y otra para la recepción de mensajes de voz enviados por el
 * cuidador mediante una hebra UDP.
 *
 * El código para los permisos en versiones superiores a la versión 6 está disponible en:
 * https://androidstudiofaqs.com/tutoriales/dar-permisos-a-aplicaciones-en-android-studio
 * El código que permite enviar datagramas de audio está disponible en:
 * https://www.youtube.com/watch?v=-0iAp6-gCOs
 */
public class Intercomunicador extends AppCompatActivity
{
    private static final int MY_WRITE_EXTERNAL_STORAGE = 0;
    public static DatagramSocket s;
    Boolean a = false;
    public Handler UIHandler;
    public Thread Thread1 = null;
    public static final int SERVERPORT = 2510;
    public static final String SERVERIP = MiPerfil.ipcuidador.getText().toString();
    public static AudioRecord recorder = null;
    private static AudioTrack track = null;
    public static DataOutputStream mensaje;
    private Thread tx, rx;
    private Boolean stopped = true;
    public static int comprobarMicro = 0;
    public static int comprobarSonido = 0;
    Boolean c =false;
    Boolean b =false;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intercomunicador);
        final Button botonMicro = (Button) findViewById(R.id.BotonMicro);
        final Button botonSonido = (Button) findViewById(R.id.BotonSonido);
        final Button btn1 = (Button) findViewById(R.id.buttonPlay);

        botonMicro.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                if (c == true)
                {
                    botonMicro.setBackgroundDrawable(getResources().getDrawable(R.drawable.micro));
                    c = false;
                    comprobarMicro = 0;
                } else
                    {
                    botonMicro.setBackgroundDrawable(getResources().getDrawable(R.drawable.nomicro));
                    c = true;
                    comprobarMicro = 1;
                    }
            }
        });

        botonSonido.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                if (b == false)
                {
                    botonSonido.setBackgroundDrawable(getResources().getDrawable(R.drawable.sonido));
                    b = true;
                    comprobarSonido = 0;

                } else
                    {
                    botonSonido.setBackgroundDrawable(getResources().getDrawable(R.drawable.nosonido));
                    b = false;
                    comprobarSonido = 1;

                    }
            }
        });

        btn1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (a == false)
                {
                    btn1.setText(R.string.stop);
                    button_start();
                    a = true;
                } else
                    {
                    btn1.setText(R.string.start);
                    button_stop();
                    a = false;
                    }
            }
        });

        verifyPermissionMicrofono();
    }

    //Verificar permiso del micrófono
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void verifyPermissionMicrofono()
    {
        int writePermission = checkSelfPermission(Manifest.permission.RECORD_AUDIO);
        if (writePermission != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissionMicrofono();
        } else {
            try {
                try {
                    System.out.println("");
                    s = new DatagramSocket(9999);

                } catch (SocketException e)
                {
                    e.printStackTrace();
                }
                cliente();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    //Solicitar permiso
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void requestPermissionMicrofono()
    {
        //shouldShowRequestPermissionRationale es verdadero solamente si ya se había mostrado
        //anteriormente el dialogo de permisos y el usuario lo negó
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.RECORD_AUDIO))
        {
            requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO},
                    MY_WRITE_EXTERNAL_STORAGE);
        } else {
            //si es la primera vez se solicita el permiso directamente
            requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO},
                    MY_WRITE_EXTERNAL_STORAGE);
        }
    }

    //Procesar respuesta de usuario
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        //Si el requestCode corresponde al que usamos para solicitar el permiso y
        //la respuesta del usuario fue positiva
        if (requestCode == MY_WRITE_EXTERNAL_STORAGE)
        {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                try {
                } catch (Exception e)
                {
                  Toast.makeText(getApplicationContext(), R.string.errormicro, Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), R.string.errormicro, Toast.LENGTH_LONG).show();
            }
        }
    }
    public synchronized void button_start()
    {
        if (stopped == true)
        {
            recorder = new AudioRecord(MediaRecorder.AudioSource.MIC, 8000,
                    AudioFormat.CHANNEL_CONFIGURATION_MONO, AudioFormat.ENCODING_PCM_16BIT,
                    AudioRecord.getMinBufferSize(8000, AudioFormat.CHANNEL_CONFIGURATION_MONO,
                            AudioFormat.ENCODING_PCM_16BIT));
            int minSize = AudioTrack.getMinBufferSize(8000, AudioFormat.CHANNEL_CONFIGURATION_MONO, AudioFormat.ENCODING_PCM_16BIT);
            track = new AudioTrack(AudioManager.MODE_RINGTONE,
                    8000, AudioFormat.CHANNEL_CONFIGURATION_MONO, AudioFormat.ENCODING_PCM_16BIT, minSize,
                    AudioTrack.MODE_STREAM);

            tx = new Thread(new Intercomunicador.GrabaAudio());
            rx = new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    byte[] bufferRecB = new byte[128 * 2]; //Recibir el audio
                    short[] bufferRecS = new short[128]; //Reproducir el audio
                    DatagramPacket dp = new DatagramPacket(bufferRecB, bufferRecB.length);
                    track.play();

                    while (stopped == false)
                    {
                        try {
                            if (comprobarSonido == 0)
                            {
                                s.receive(dp);
                                for (int i = 0; i < bufferRecS.length; i++)
                                {
                                    bufferRecS[i] = (short) (bufferRecB[i * 2] * 256 + bufferRecB[(i * 2) + 1]);
                                }
                                track.write(bufferRecB, 0, bufferRecB.length); //Reproducir audio recibido
                            }
                        } catch(IOException e)
                        {
                            e.printStackTrace();
                        } catch (Throwable throwable)
                        {
                            throwable.printStackTrace();
                        }
                    }
                }
            });
            if (recorder.getState() == AudioRecord.STATE_INITIALIZED)
            {
                stopped = false;
                Log.d("Audio", "Recorder iniciado correctamente");
                tx.start();
                rx.start();
            }
        }
    }
    public synchronized void button_stop()
    {
        stopped = true;
        if (recorder != null)
        {
            recorder.stop();
            recorder.release();
            recorder = null;
        }
        if (track != null)
        {
            track.stop();
            track.release();
            track = null;
        }
    }
    public void cliente()
    {
        UIHandler = new Handler();
        Thread1 = new Thread(new Intercomunicador.Thread1());
        Thread1.start();
    }

    class Thread1 implements Runnable
    {
        public void run()
        {
            Socket socket = null;
            try {
                InetAddress serverAddr = InetAddress.getByName(SERVERIP);
                socket = new Socket(serverAddr, SERVERPORT);
                System.out.println("CLIENTE:Conexión establecida");
                Intercomunicador.Thread2 commThread = new Intercomunicador.Thread2(socket);
                new Thread(commThread).start();
                return;
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    class Thread2 implements Runnable
    {
        private Socket clientSocket;
        private BufferedReader input;
        private BufferedWriter output;

        public Thread2(Socket clientSocket)
        {
            this.clientSocket = clientSocket;
            try {
                mensaje = new DataOutputStream(clientSocket.getOutputStream());
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        public void run()
        {
            try {
                mensaje.writeUTF(Hash.md5(MiPerfil.clave.getText().toString()));
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    public class GrabaAudio implements Runnable
    {
        @Override
        public void run ()
        {
            short buffer[];
            buffer = new short[128];
            byte[] vectores = new byte[128 * 2];
            DatagramPacket p;
            if (recorder.getState() == AudioRecord.STATE_INITIALIZED)
            {
                recorder.startRecording();
                while (recorder.getRecordingState() == AudioRecord.RECORDSTATE_RECORDING)
                {
                    recorder.read(buffer, 0, buffer.length); //capturar audio
                    for (int i = 0; i < buffer.length; i++)
                    {
                        vectores[i * 2] = (byte) (buffer[i] / 256);
                        vectores[(i * 2) + 1] = (byte) (vectores[i * 2] % 256);
                    }
                    try {
                        p = new DatagramPacket(vectores, vectores.length, InetAddress.getByName("" + MiPerfil.ipcuidador.getText()), 9999);
                        if(comprobarMicro == 0)
                        {
                            s.send(p);
                        }
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }

            } else
                button_stop();
        }
    }
}

