package tutorial.com.cuidadores;

import android.app.Activity;
import android.os.Bundle;

/**
 * Esta clase se encarga de llamar a la vista política, para así poder visualizar toda la política de
 * privacidad referida a los datos personales.
 */
public class Politica extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.politica3);
    }
}
