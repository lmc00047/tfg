package tutorial.com.movilidad1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Esta clase contiene cuatro botones. Al hacer click sobre cada uno de ellos abre un cuadro de dialogo.
 * Cada botón tiene una finalidad:
 *  -btnmsgsms es el botón encargado de abrir el cuadro de dialogo que pregunta si se quiere enviar sms cuando
 *  se envíe un email, si se quiere enviar un email cuando se envíe un sms o por defecto, que quiere decir que
 *  solo se envía el sms o solo se envía el email.
 *  -btnleer es el botón que hace que se active o no la lectura automática de mensajes de texto, si se activa esta opción
 *  cuando al usuario le llegue un SMS de su cuidador, éste será leído en voz alta.
 *  -btnperfil es el botón que hace que se abra el cuadro de diálogo donde se define el nombre de usuario,
 *   el email y contraseña del correo electrónico del usuario, el email del cuidador y el teléfono del cuidador, así como la dirección IP
 *   del móvil del cuidador y la clave de conexión, que serán usados en otras clases.
 *  -btnllamada es el botón que te permite establecer una llamada de forma automática o manual al número de emergencias 112.
 *
 *  Es la encargada de llamar a la vista configuración para visualizarla en pantalla.
 */
public class Configuracion extends AppCompatActivity implements MiPerfil.NuevoDialogoListener
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

        btnllamada.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                LlamadaDirecta112 dialogFragment = LlamadaDirecta112
                        .newInstance("¿De qué manera quiere llamar a emergencias?");
                dialogFragment.show(getSupportFragmentManager(), "dialog");
            }
        });

        btnleer.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                EscucharMensajes dialogFragment = EscucharMensajes
                        .newInstance("¿Quiere escuchar los mensajes?");
                dialogFragment.show(getSupportFragmentManager(), "dialog");
            }
        });

        btnmsgsms.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                EnviarSmsoEmail dialogFragment = EnviarSmsoEmail
                    .newInstance("¿Quiere enviar sms o email?");
                dialogFragment.show(getSupportFragmentManager(), "dialog");
            }
        });
        btnperfil.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0){
                dialogoPersonalizado();
            }
        });
    }

    public void doPositiveClick(){
    }

    public void doNegativeClick(){
    }

    /**
     * Método para abrir el dialogo personalizado que pertenece al botón de Mi Perfil.
     */
    public void dialogoPersonalizado()
    {
        MiPerfil dialogoPersonalizado = new MiPerfil();
        dialogoPersonalizado.show(getSupportFragmentManager(), "personalizado");

        //Encuentra por el nombre de la etiqueta el cuadro personalizado
        android.app.Fragment frag = getFragmentManager().findFragmentByTag("personalizado");

        // Si el fragmento no está visible y está aún en memoria, lo borramos
        if (frag != null)
        {
            getFragmentManager().beginTransaction().remove(frag).commit();
        }
    }
    /**
     * Termina dialogo personalizado. Recibimos el texto por el fragmento y lo mostramos en pantalla
     * @param texto
     */
    @Override
    public void FinalizaCuadroDialogo(String texto)
    {
        Toast.makeText(this,texto,Toast.LENGTH_SHORT).show();
        Toast.makeText(this,texto,Toast.LENGTH_LONG).show();
    }
}



