package tutorial.com.movilidad1;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Created by LauraMoreno on 21/07/2017.
 */

public class Intercomunicador_RX extends AppCompatActivity{
    DatagramSocket s;
    Boolean a = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intercomunicador);

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

                int minSize = AudioTrack.getMinBufferSize(8000,AudioFormat.CHANNEL_CONFIGURATION_MONO, AudioFormat.ENCODING_PCM_16BIT);

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

    public void button_stop(){
        stopped=true;
    }


}


