package tutorial.com.movilidad1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Esta clase se utiliza para realizar la configuración de las llamadas, mensajes de texto y correo
 * electrónico, así como los datos personales del usuario. Además es la encargada de llamar a la
 * vista configuración para visualizarla en pantalla.
 *
 * Esta clase contiene cuatro botones. Al hacer click sobre cada uno de ellos abre un cuadro de
 * dialogo,  * excepto el botón de mi perfil que abre una vista.
 * Cada botón tiene una finalidad:
 *  -btnmsgsms es el botón encargado de abrir el cuadro de dialogo que pregunta si se quiere enviar
 *  sms cuando se envíe un email, si se quiere enviar un email cuando se envíe un sms o por defecto,
 *  que quiere decir que solo se envía el sms o solo se envía el email.
 *  -btnleer es el botón que hace que se active o no la lectura automática de mensajes de texto, si
 *  se activa esta opción cuando al usuario le llegue un SMS de su cuidador, éste será leído en voz
 *  alta.
 *  -btnperfil es el botón que hace que se abra la vista donde se define el nombre de usuario,
 *  el email y contraseña del correo electrónico del usuario, el email del cuidador y el teléfono
 *  del cuidador, así como la dirección IP del móvil del cuidador y la clave de conexión, que serán
 *  usados en otras clases.
 *  -btnllamada es el botón que te permite establecer una llamada de forma automática o manual al
 *  número de emergencias 112.
 *
 *  EL código acerca de los cuadros de diálogo está disponible en:
 *  https://sekthdroid.wordpress.com/2013/02/06/fragments-dialogfragment-en-android/
 *
 */
public class Configuracion extends AppCompatActivity
{
    public  Button btnllamada;
    private Button btnmsgsms;
    private Button btnleer;
    private Button btnperfil;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configuracion);

        btnmsgsms = (Button) findViewById(R.id.botonEnviarsms);
        btnleer = (Button) findViewById(R.id.botonleerSms);
        btnperfil = (Button) findViewById(R.id.botonnombre);
        btnllamada = (Button)findViewById(R.id.botonLlamada112);

        btnllamada.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                LlamadaDirecta112 dialogFragment = LlamadaDirecta112
                        .newInstance("¿De qué manera quiere llamar a emergencias?");
                dialogFragment.show(getSupportFragmentManager(), "dialog");
            }
        });

        btnleer.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                EscucharMensajes dialogFragment = EscucharMensajes
                        .newInstance("¿Quiere escuchar los mensajes?");
                dialogFragment.show(getSupportFragmentManager(), "dialog");
            }
        });

        btnmsgsms.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                EnviarSmsoEmail dialogFragment = EnviarSmsoEmail
                    .newInstance("¿Quiere enviar sms o email?");
                dialogFragment.show(getSupportFragmentManager(), "dialog");
            }
        });
        btnperfil.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                lanzarMiperfil();
            }
        });
    }

    public void doPositiveClick(){
    }

    public void doNegativeClick(){
    }

    public void lanzarMiperfil()
    {
        Intent i = new Intent(this, MiPerfil.class);
        startActivity(i);
    }
}



