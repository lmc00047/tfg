package tutorial.com.movilidad1;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Se encarga de convertir la voz en texto, utilizando el API de google.
 */
public class VozTexto extends Activity
{

    private Button btn1;
    TextView grabar;
    private static final int RECOGNIZE_SPEECH_ACTIVITY = 1;

   @Override
   protected void onCreate(Bundle savedInstanceState)
   {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.voztexto);
       btn1 = (Button) findViewById(R.id.btnenviar);

       grabar = (TextView) findViewById(R.id.txtGrabarVoz);
       btn1.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               LanzarEmail();
           }

       });
   }

   @Override
   protected void onActivityResult(int requestCode, int resultCode, Intent data)
   {
       super.onActivityResult(requestCode, resultCode, data);

       switch (requestCode)
       {
           case RECOGNIZE_SPEECH_ACTIVITY:

               if (resultCode == RESULT_OK && null != data)
               {
                   ArrayList<String> speech = data
                           .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                   String strSpeech2Text = speech.get(0);

                   grabar.setText(strSpeech2Text);
               }

               break;
           default:
               break;
       }
   }

   public void onClickImgBtnHablar(View v)
   {
       Intent intentActionRecognizeSpeech = new Intent(
               RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

       // Configura el Lenguaje (Español-México)
       intentActionRecognizeSpeech.putExtra(
               RecognizerIntent.EXTRA_LANGUAGE_MODEL, "es-MX");
       try {
           startActivityForResult(intentActionRecognizeSpeech,
                   RECOGNIZE_SPEECH_ACTIVITY);
       } catch (ActivityNotFoundException a)
       {
           Toast.makeText(getApplicationContext(),
                   R.string.ErrorReconocimiento,
                   Toast.LENGTH_SHORT).show();
       }
   }
    public void LanzarEmail()
    {
        Intent i = new Intent(this.getApplicationContext(), EnviarEmail.class);
        i.putExtra("Siri",grabar.getText().toString());
        startActivity(i);
    }
}


