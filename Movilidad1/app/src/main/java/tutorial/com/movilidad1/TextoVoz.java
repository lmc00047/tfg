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

/**
 * Esta Clase se encarga de convertir el texto en voz, utiliza para ello la clase TextToSpeech.
 * El código para convertir texto a voz puede encontrarse en:
 * https://www.youtube.com/watch?v=5pBr2WyPuDM&t=2164s
 *
 */
public class TextoVoz extends Activity implements TextToSpeech.OnInitListener
{
    private TextToSpeech tts;
    public static EditText etx;
    private Button botonReproducir;
    private Button botonEnviar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.siri);

        tts = new TextToSpeech(this, this);
        etx = (EditText) findViewById(R.id.etxescribir);
        botonReproducir = (Button) findViewById(R.id.btnreproducir);
        botonEnviar = (Button) findViewById(R.id.btnenviar);

        botonReproducir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speakOut();
            }

        });
        botonEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LanzarEmail();
            }

        });
    }
    @Override
    public void onInit(int status)
    {
        if (status == TextToSpeech.SUCCESS)
        {
            int result = tts.setLanguage(Locale.getDefault());
            if (result == TextToSpeech.LANG_NOT_SUPPORTED || result == TextToSpeech.LANG_MISSING_DATA)
            {
                Log.e("TTS", "Este lenguaje no es soportado");
            } else
                {
                botonReproducir.setEnabled(true);
                speakOut();
                }
        } else
            {
            Log.e("TTS", "Inicialización del lenguaje es fallida");
            }
    }

    private void speakOut()
    {
        String text = etx.getText().toString();
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    //Lanzar Activity Enviar Email

    public void LanzarEmail()
    {
        Intent i = new Intent(this.getApplicationContext(), EnviarEmail.class);
        i.putExtra("Siri", etx.getText().toString());
        startActivity(i);
    }
}

