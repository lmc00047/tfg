package tutorial.com.cuidadores;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Esta clase se utiliza para realizar la configuración de los datos personales del usuario. Además
 * es la encargada de llamar a la vista configuración para visualizarla en pantalla. Esta clase
 * contiene un botón, que al presionarlo, llama a la vista miperfil.xml
 * Este botón tiene una finalidad:
 *  -btnperfil es el botón que hace que se abra la vista dónde se define la dirección ip del
 *  paciente y la contraseña de conexión para establecer la comunicación, que ha de ser la misma que
 *  la del paciente.
 */

public class Configuracion extends AppCompatActivity
{
    private Button btnperfil;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configuracion);
        btnperfil = (Button) findViewById(R.id.botonnombre);

        btnperfil.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0){
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



