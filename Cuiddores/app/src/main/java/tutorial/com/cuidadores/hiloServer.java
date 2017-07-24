package tutorial.com.cuidadores;


import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;

public class hiloServer extends Thread {

    Socket so;
    BufferedReader entradaDesdeCliente = null;
    public String fraseCliente;
    DataInputStream entrada;
    DatagramSocket s;

    public static int auxEstConex = 0;

    //SERVIDOR//

    public hiloServer(Socket so) {
        try {
            entrada = new DataInputStream(so.getInputStream());
        } catch (IOException e)
        {
            System.out.println("Error en el flujo de datos de entrada");
        }
    }
    public void run() {
        System.out.println("HILO CREADO");
        //Recepcion de mensaje
        while (true) {

                try {
                    fraseCliente = entrada.readUTF();
                    System.out.println(fraseCliente);

                    if (fraseCliente.equals(Hash.md5(MiPerfil.clave.getText().toString())))

                    {
                        System.out.println("CONTRASEÑA CORRECTA");
                        auxEstConex = 1;

                    }
                } catch (IOException e) {
                    e.printStackTrace();


                }
                System.out.println(fraseCliente);

            }

    }

    public void recibir () {
        try {
            s=new DatagramSocket(9999);
        } catch (SocketException e) {
            e.printStackTrace();
        }

    }
    Thread rx;
    Boolean stopped =false;

    public void button_start(){
        stopped =false;
        rx = new Thread(new Runnable() {
            @Override
            public void run() {
                //android.os.Process.setThreadPriority(Process.THREAD_PRIORITY_URGENT_AUDIO);
                byte[] bufferRecB =new byte [128*2]; //bit para recibir el audio
                short[] bufferRecS =new short[128]; //short para reproducir el audio
                DatagramPacket dp= new DatagramPacket (bufferRecB,bufferRecB.length);
                AudioTrack track;

                int minSize = AudioTrack.getMinBufferSize(8000, AudioFormat.CHANNEL_CONFIGURATION_MONO, AudioFormat.ENCODING_PCM_16BIT);

                track= new AudioTrack(AudioManager.MODE_RINGTONE,
                        8000,AudioFormat.CHANNEL_CONFIGURATION_MONO,AudioFormat.ENCODING_PCM_16BIT,minSize,
                        AudioTrack.MODE_STREAM);
                track.play();

                while (stopped==false){
                    try{
                        s.receive(dp);
                    }catch (IOException e){
                        e.printStackTrace();
                    }

                    for (int i =0; i< bufferRecS.length; i++){
                        bufferRecS[i]=(short) (bufferRecB[i*2]*256+bufferRecB[(i*2)+1]);
                        System.out.println("ESTOY RECIBIENDOOOOOOOOOOOOOOOOOOO");
                    }
                    track.write(bufferRecB,0,bufferRecB.length); //reproducir audio recibido

                }
            }
        });

        rx.start();
    }
    }

