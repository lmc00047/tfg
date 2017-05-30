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

public class ReconocimientoVoz extends Activity {
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
   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       super.onActivityResult(requestCode, resultCode, data);

       switch (requestCode) {
           case RECOGNIZE_SPEECH_ACTIVITY:

               if (resultCode == RESULT_OK && null != data) {

                   ArrayList<String> speech = data
                           .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                   String strSpeech2Text = speech.get(0);
                    switch (strSpeech2Text){

                        case "emergencias":

                            Intent emergencias = new Intent(this.getApplicationContext(), Llamar112.class);
                            startActivity(emergencias);

                            break;
                        case "emergencia":

                            Intent emergencia = new Intent(this.getApplicationContext(), Llamar112.class);
                            startActivity(emergencia);

                            break;
                        case "112":

                            Intent numero = new Intent(this.getApplicationContext(), Llamar112.class);
                            startActivity(numero);

                            break;

                        case "Laura":

                            Intent laura = new Intent(this.getApplicationContext(), LlamarContacto.class);
                            startActivity(laura);

                            break;
                        case "favorito":

                            Intent favorito = new Intent(this.getApplicationContext(), LlamarContacto.class);
                            startActivity(favorito);

                            break;
                        case "llamar":

                            Intent llamar = new Intent(this.getApplicationContext(), LlamarContacto.class);
                            startActivity(llamar);

                            break;
                        case "enviar":

                            Intent enviar = new Intent(this.getApplication(), EnviarEmail.class);
                            startActivity(enviar);

                            break;
                        case "email":

                            Intent email = new Intent(this.getApplicationContext(), EnviarEmail.class);
                            startActivity(email);

                            break;
                        case "correo":

                            Intent correo = new Intent(this.getApplicationContext(), EnviarEmail.class);
                            startActivity(correo);

                            break;
                         case "voz":

                            Intent voz = new Intent(this.getApplicationContext(), VozTexto.class);
                            startActivity(voz);

                            break;
                        case "texto":

                            Intent texto = new Intent(this.getApplicationContext(), TextoVoz.class);
                            startActivity(texto);

                            break;
                       /* case "intercomunicador":

                            Intent r = new Intent(this.getApplicationContext(), Intercomunicador.class);
                            startActivity(r);

                            break;

                             case "grabar":

                            Intent y = new Intent(this.getApplicationContext(), Intercomunicador.class);
                            startActivity(y);

                            break;

                             case "audio":

                            Intent u = new Intent(this.getApplicationContext(), Intercomunicador.class);
                            startActivity(u);

                            break;

                            */

                    }

               }

               break;
           default:

               break;
       }
   }
    public void onClickImgBtnHablar(View v) {

        Intent intentActionRecognizeSpeech = new Intent(
                RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        // Configura el Lenguaje (Español)
        intentActionRecognizeSpeech.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL, "es-MX");
        try {
            startActivityForResult(intentActionRecognizeSpeech,
                    RECOGNIZE_SPEECH_ACTIVITY);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    R.string.ErrorReconocimiento,
                    Toast.LENGTH_SHORT).show();
        }

    }


}


