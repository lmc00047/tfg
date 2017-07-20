package tutorial.com.movilidad1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import java.util.Locale;

public class MessageReceiver extends BroadcastReceiver{

    public static String str = " ";
    private TextToSpeech tts;
    public static EditText etx;
    private Button btn;
    public String textoSiri;
    public String numero;

    @Override
    public void onReceive(Context context, Intent intent) {
        //get message passed in


        Bundle bundle = intent.getExtras();
        SmsMessage[] messages;
        MainActivity.aux_sms=0;
        if (EscucharMensajes.estadoEscuchar == 1) {

            if (bundle != null) {
                Object[] pdus = (Object[]) bundle.get("pdus");
                messages = new SmsMessage[pdus.length];

                for (int i = 0; i < messages.length; i++) {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    str += "Has recibido un mensaje del cuidador";
                    //str += messages[i].getOriginatingAddress();
                    str += "\n";
                    str += "\n";
                    str += "\n";str += "\n";

                    str += "el contenido del mensaje es ";
                    str += messages[i].getMessageBody();
                    str += "\n";
                    numero = messages[i].getOriginatingAddress();
                }
                if (MiPerfil.tlfcuidador.getText().toString().equals(numero)) {
                    //display the message
                   //Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
                    MainActivity.mensaje = str;
                    MainActivity.aux_sms=1;

                    str = "";

                    //send a broadcast intent to update the SMS received in a text view
                    Intent broadcastIntent = new Intent();
                    broadcastIntent.setAction("SMS_RECEIVED_ACTION");
                    broadcastIntent.putExtra("message", str);
                    context.sendBroadcast(broadcastIntent);
                }


            }
            MainActivity.aux_sms=0;
        }
   }
    public void onInit(int status) {

        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.getDefault());
            if (result == TextToSpeech.LANG_NOT_SUPPORTED || result == TextToSpeech.LANG_MISSING_DATA) {
                Log.e("TTS", "Este lenguaje no es soportado");
            } else {
                btn.setEnabled(true);
                speakOut();
            }
        } else {
            Log.e("TTS", "Inicialización del lenguaje es fallida");
        }
    }

    public void speakOut() {
            String text = str;
       tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

}

