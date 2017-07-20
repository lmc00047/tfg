package tutorial.com.movilidad1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;



public class AcercaDe extends AppCompatActivity {
    /**
     * Es el metodo que se llama cuando se crea una actividad,
     * siendo acercade una vista dónde aparece una breve descripción de l aplicación
     * la autora y el icono de la aplicación
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acercade);


    }
}
