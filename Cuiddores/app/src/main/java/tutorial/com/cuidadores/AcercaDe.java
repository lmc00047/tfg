package tutorial.com.cuidadores;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Es la clase que se encarga de llamar a la vista acercade.xml en la cual aparece una breve
 * descripción, la autora y el icono de la aplicación.
 */
public class AcercaDe extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acercade);
    }
}
