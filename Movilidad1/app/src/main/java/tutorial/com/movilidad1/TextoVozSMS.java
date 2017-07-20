package tutorial.com.movilidad1;

import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Locale;

public class TextoVozSMS extends Activity implements TextToSpeech.OnInitListener {

    private TextToSpeech tts;
    public static EditText etx;
    private Button btn;
    MessageReceiver cadena =new MessageReceiver();
    public String textoSiri;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.siri4);

            tts = new TextToSpeech(this, this);
            etx = (EditText) findViewById(R.id.etxescribir);
            btn = (Button) findViewById(R.id.btnreproducir);


        etx.setText(cadena.str.toString());

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speakOut();
            }
        });
    }

    @Override
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

    private void speakOut() {
        String text = etx.getText().toString();
        tts.speak(cadena.str, TextToSpeech.QUEUE_FLUSH, null);
    }






    }

