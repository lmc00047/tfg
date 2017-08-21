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
 * En esta clase se realiza el reconocimiento de voz, es decir, el usuario puede acceder a todas las funcionalidades de la
 * aplicación mediante comandos de voz
 *
 * El código para la aplicación de reconocimiento de voz con la API de google está disponible en:
 * https://www.youtube.com/watch?v=-VqxuQMIcaE
 *
 */
public class ReconocimientoVoz extends Activity
{

    private Button btn1;
    TextView grabar;
    private static final int RECOGNIZE_SPEECH_ACTIVITY = 1;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.reconocimientovoz);

       btn1 = (Button) findViewById(R.id.btnenviar);
       grabar = (TextView) findViewById(R.id.txtGrabarVoz);
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
                    switch (strSpeech2Text)
                    {
                        case "emergencias":
                            Intent emergencias = new Intent(this.getApplicationContext(), Llamar112new.class);
                            startActivity(emergencias);
                            break;

                        case "emergencia":
                            Intent emergencia = new Intent(this.getApplicationContext(), Llamar112new.class);
                            startActivity(emergencia);
                            break;

                        case "urgencia":
                            Intent urgencia = new Intent(this.getApplicationContext(), SendSmS.class);
                            startActivity(urgencia);
                            break;

                        case "112":
                            Intent numero = new Intent(this.getApplicationContext(), Llamar112new.class);
                            startActivity(numero);
                            break;

                        case "Contacto":
                            Intent contacto = new Intent(this.getApplicationContext(), LlamarContacto.class);
                            startActivity(contacto);
                            break;

                        case "cuidador":
                            Intent cuidador = new Intent(this.getApplicationContext(), LlamarContacto.class);
                            startActivity(cuidador);
                            break;

                        case "llamar":
                            Intent llamar = new Intent(this.getApplicationContext(), LlamarContacto.class);
                            startActivity(llamar);
                            break;

                         case "voz":
                            Intent voz = new Intent(this.getApplicationContext(), VozTexto.class);
                            startActivity(voz);
                            break;

                        case "texto":
                            Intent texto = new Intent(this.getApplicationContext(), TextoVoz.class);
                            startActivity(texto);
                            break;

                        case "intercomunicador":
                            Intent intercomunicador = new Intent(this.getApplicationContext(), Intercomunicador.class);
                            startActivity(intercomunicador);
                            break;

                        case "grabar":
                            Intent grabar = new Intent(this.getApplicationContext(), Intercomunicador.class);
                            startActivity(grabar);
                            break;

                        case "audio":
                            Intent audio = new Intent(this.getApplicationContext(), Intercomunicador.class);
                            startActivity(audio);
                            break;
                    }

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

        // Configura el Lenguaje (Español)
        intentActionRecognizeSpeech.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL, "es-MX");
        try
        {
            startActivityForResult(intentActionRecognizeSpeech,
                    RECOGNIZE_SPEECH_ACTIVITY);
        } catch (ActivityNotFoundException a)
        {
            Toast.makeText(getApplicationContext(),
                    R.string.ErrorReconocimiento,
                    Toast.LENGTH_SHORT).show();
        }
    }
}


