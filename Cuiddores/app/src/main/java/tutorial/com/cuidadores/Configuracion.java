package tutorial.com.cuidadores;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Esta clase contiene cuatro botones. Al hacer click sobre cada uno de ellos abre un cuadro de dialogo.
 * Cada botón tiene una finalidad:
 *  -btnmsgsms es el botón encargado de abrir el cuadro de dialogo que pregunta si se quiere enviar sms cuando
 *  se envíe un email, si se quiere enviar un email cuando se envíe un sms o por defecto, que quiere decir que
 *  solo se enviía el sms o solo se envía el email.
 *  -btnleer es el botón que hace que se active o no la lectura automatica de mensajes de texto.
 *  -btnperfil es el botón que hace que se abra el cuadro de diálogo donde se define el nombre de usuario,
 *   el email y contraseña del usuario, el email del cuidador y el teéfono del cuidador, que será usados en otras clases.
 *  -btnllamada es el botón que te permite establecer una llamada de forma automática o manual al nñumero de emergencias 112.
 *
 *
 */
public class Configuracion extends AppCompatActivity  {

    private Button btnperfil;

    /**
     * Método que llama a la vista configuración.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configuracion);


        btnperfil = (Button) findViewById(R.id.botonnombre);

        btnperfil.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0){
                lanzarMiperfil();
            }
        });
    }


    public void doPositiveClick(){
        //Toast.makeText(this, R.string.si, Toast.LENGTH_SHORT).show();
    }

    public void doNegativeClick(){
       // Toast.makeText(this, R.string.no, Toast.LENGTH_SHORT).show();
    }



public void lanzarMiperfil() {
    Intent i = new Intent(this, MiPerfil.class);
    startActivity(i);
}
}



