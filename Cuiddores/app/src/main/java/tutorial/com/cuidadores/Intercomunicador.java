package tutorial.com.cuidadores;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

public class Intercomunicador extends AppCompatActivity {

    public static DatagramSocket s;
    Boolean a = false;
    public Handler UIHandler;
    public Thread Thread1 = null;
    public static final int SERVERPORT = 2510;
    public static final String SERVERIP = MiPerfil.ipcuidador.getText().toString();
    public static int auxServer = 0;
    public static AudioRecord recorder = null;
    private static AudioTrack track = null;
    public static DataOutputStream mensaje;
    private Thread tx, rx;
    private Boolean stopped = true;
    public static int comprobarMicro = 0;
    public static int comprobarSonido = 0;
    Boolean c =false;
    Boolean b =false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intercomunicador);
        final Button botonMicro = (Button) findViewById(R.id.BotonMicro);
        final Button botonSonido = (Button) findViewById(R.id.BotonSonido);


        botonMicro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (c == true) {
                    botonMicro.setBackgroundDrawable(getResources().getDrawable(R.drawable.micro));
                    c = false;
                    comprobarMicro = 0;
                } else {
                    botonMicro.setBackgroundDrawable(getResources().getDrawable(R.drawable.nomicro));
                    c = true;
                    comprobarMicro = 1;
                }

            }

        });

        botonSonido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (b == false) {
                    botonSonido.setBackgroundDrawable(getResources().getDrawable(R.drawable.sonido));
                    b = true;
                    comprobarSonido = 0;

                } else {
                    botonSonido.setBackgroundDrawable(getResources().getDrawable(R.drawable.nosonido));
                    b = false;
                    comprobarSonido = 1;

                }

            }

        });





        try {
            System.out.println("");
            s = new DatagramSocket(9999);

        } catch (SocketException e) {
            e.printStackTrace();
        }

        final Button btn1 = (Button) findViewById(R.id.buttonPlay);

        cliente();

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (a == false) {
                    btn1.setText(R.string.stop);
                    button_start();
                    a = true;
                } else {
                    btn1.setText(R.string.start);
                    button_stop();
                    a = false;
                }
            }


        });

    }


    public synchronized void button_start() {
        if (stopped == true) {

            recorder = new AudioRecord(MediaRecorder.AudioSource.MIC, 8000,
                    AudioFormat.CHANNEL_CONFIGURATION_MONO, AudioFormat.ENCODING_PCM_16BIT,
                    AudioRecord.getMinBufferSize(8000, AudioFormat.CHANNEL_CONFIGURATION_MONO,
                            AudioFormat.ENCODING_PCM_16BIT));


            int minSize = AudioTrack.getMinBufferSize(8000, AudioFormat.CHANNEL_CONFIGURATION_MONO, AudioFormat.ENCODING_PCM_16BIT);

            track = new AudioTrack(AudioManager.MODE_RINGTONE,
                    8000, AudioFormat.CHANNEL_CONFIGURATION_MONO, AudioFormat.ENCODING_PCM_16BIT, minSize,
                    AudioTrack.MODE_STREAM);


            tx = new Thread(new GrabaAudio());

            rx = new Thread(new Runnable() {
                @Override
                public void run() {
                    //android.os.Process.setThreadPriority(Process.THREAD_PRIORITY_URGENT_AUDIO);
                    byte[] bufferRecB = new byte[128 * 2]; //bit para recibir el audio
                    short[] bufferRecS = new short[128]; //short para reproducir el audio
                    DatagramPacket dp = new DatagramPacket(bufferRecB, bufferRecB.length);
                    track.play();

                    while (stopped == false) {
                        try {
                            if (comprobarSonido == 0) {
                                s.receive(dp);
                                for (int i = 0; i < bufferRecS.length; i++) {
                                    bufferRecS[i] = (short) (bufferRecB[i * 2] * 256 + bufferRecB[(i * 2) + 1]);
                                }
                                track.write(bufferRecB, 0, bufferRecB.length); //reproducir audio recibido
                            }
                            } catch(IOException e){
                                e.printStackTrace();
                            } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    }

                }
            });

            if (recorder.getState() == AudioRecord.STATE_INITIALIZED) {
                stopped = false;
                Log.d("Audio", "Recorder iniciado correctamente");
                tx.start();
                rx.start();
            }
        }
    }

    public synchronized void button_stop() {

        stopped = true;
        if (recorder != null) {
            recorder.stop();
            recorder.release();
            recorder = null;
        }
        if (track != null) {
            track.stop();
            track.release();
            track = null;
        }
    }
    public void cliente() {

        UIHandler = new Handler();

        Thread1 = new Thread(new tutorial.com.cuidadores.Intercomunicador.Thread1());
        Thread1.start();
    }

    class Thread1 implements Runnable {
        public void run() {
            Socket socket = null;
            try {
                InetAddress serverAddr = InetAddress.getByName(SERVERIP);
                socket = new Socket(serverAddr, SERVERPORT);
                System.out.println("CLIENTE:Conexión establecida");

                tutorial.com.cuidadores.Intercomunicador.Thread2 commThread = new tutorial.com.cuidadores.Intercomunicador.Thread2(socket);
                new Thread(commThread).start();
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    class Thread2 implements Runnable {
        private Socket clientSocket;

        private BufferedReader input;
        private BufferedWriter output;

        public Thread2(Socket clientSocket) {

            this.clientSocket = clientSocket;

            try {
                mensaje = new DataOutputStream(clientSocket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        public void run() {

            try {

                mensaje.writeUTF(Hash.md5(MiPerfil.clave.getText().toString()));

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    public class GrabaAudio implements Runnable {
        @Override
        public void run () {
            //android.os.Process.setThreadPriority(Process.THREAD_PRIORITY_AUDIO);
            short buffer[];

            buffer = new short[128];
            byte[] vectores = new byte[128 * 2];
            DatagramPacket p;

            if (recorder.getState() == AudioRecord.STATE_INITIALIZED) {

                    recorder.startRecording();
                    while (recorder.getRecordingState() == AudioRecord.RECORDSTATE_RECORDING) {
                        recorder.read(buffer, 0, buffer.length); //capturar audio

                        for (int i = 0; i < buffer.length; i++) {
                            vectores[i * 2] = (byte) (buffer[i] / 256);
                            vectores[(i * 2) + 1] = (byte) (vectores[i * 2] % 256);
                        }
                        try {
                            p = new DatagramPacket(vectores, vectores.length, InetAddress.getByName("" + MiPerfil.ipcuidador.getText()), 9999);
                            if(comprobarMicro == 0) {
                                s.send(p);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

            } else
                button_stop();
        }
    }
}

