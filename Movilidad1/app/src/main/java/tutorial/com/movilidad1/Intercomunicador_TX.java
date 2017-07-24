package tutorial.com.movilidad1;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * Created by LauraMoreno on 21/07/2017.
 */

public class Intercomunicador_TX extends AppCompatActivity {
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
        final Button btn1 =(Button)findViewById(R.id.buttonPlay);

        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(a==false){
                    btn1.setText(R.string.stop);
                    button_start();
                    a=true;
                }else {
                    btn1.setText(R.string.start);
                    button_stop();
                    a=false;
                }
            }


        });
    }
    Thread tx;
    Boolean stopped =false;

    public void button_start(){
        stopped =false;
        tx = new Thread(new Runnable() {
            @Override
            public void run() {
                //android.os.Process.setThreadPriority(Process.THREAD_PRIORITY_AUDIO);
                short buffer[];
                AudioRecord recorder = new AudioRecord(MediaRecorder.AudioSource.MIC,8000,
                        AudioFormat.CHANNEL_CONFIGURATION_MONO,AudioFormat.ENCODING_PCM_16BIT,
                        AudioRecord.getMinBufferSize(8000, AudioFormat.CHANNEL_CONFIGURATION_MONO,
                                AudioFormat.ENCODING_PCM_16BIT));

                buffer = new short[128];
                byte [] vectores= new byte[128*2];
                DatagramPacket p;
                EditText e1= (EditText)findViewById(R.id.editText);
                recorder.startRecording();
                while (stopped ==false){
                    recorder.read(buffer,0,buffer.length); //capturar audio

                    for (int i=0; i< buffer.length; i++){
                        vectores[i*2] =(byte) (buffer[i]/256);
                        vectores [(i*2)+1]=(byte) (vectores [i*2]%256);
                    }
                    try{
                        p =new DatagramPacket(vectores,vectores.length, InetAddress.getByName(""+e1.getText()),9999);
                        // System.out.println(""+InetAddress.getByName(""+e1.getText()));
                        s.send(p);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });



        tx.start();
    }

    public void button_stop(){
        stopped=true;
    }


}


