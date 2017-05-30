package tutorial.com.movilidad1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Locale;

public class TextoVoz extends Activity implements TextToSpeech.OnInitListener {

    private TextToSpeech tts;
    private EditText etx;
    private Button btn;
    private Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.siri);

        tts = new TextToSpeech(this, this);
        etx = (EditText) findViewById(R.id.etxescribir);
        btn = (Button) findViewById(R.id.btnreproducir);
        btn1 = (Button) findViewById(R.id.btnenviar);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speakOut();
            }

        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LanzarEmail();
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
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }


    //Lanzar Activity Enviar Email

    public void LanzarEmail() {
        Intent i = new Intent(this.getApplicationContext(), EnviarEmail.class);
        i.putExtra("Siri", etx.getText().toString());
        startActivity(i);
    }



    }

