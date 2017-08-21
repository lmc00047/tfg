package tutorial.com.cuidadores;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;
/**
 *Clase principal de la aplicación, la cuál da acceso a las distintas utilidades de la misma.
 * EL código para comprobar la versión de la aplicación está disponible en:
 * https://es.stackoverflow.com/questions/3243/saber-cuando-la-app-es-lanzada-por-primera-vez-en-android
 */

public class MainActivity extends AppCompatActivity
{
    //Declaración de variables
    private View mLayout;
    protected PowerManager.WakeLock wakelock; //Pantalla siempre activa
    private static final int MY_WRITE_EXTERNAL_STORAGE = 0;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLayout = findViewById(R.id.fragmentbotones);
        nuevoHiloServidor();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); //pantalla activa

        //Si es la primera vez que se instala la aplicación, entonces muestra el cuadro de dialogo del perfil
        switch (getFirstTimeRun())
        {
            case 0:
                LanzarMiperfil();
                requestPermissionMicrofono();
                break;
        }
    }

    //Método para saber si ha sido la primera vez que se ha instalado la aplicación o no.
    private int getFirstTimeRun()
    {
        SharedPreferences sp = getSharedPreferences("MYAPP", 0);
        int result, currentVersionCode = BuildConfig.VERSION_CODE;
        int lastVersionCode = sp.getInt("FIRSTTIMERUN", -1);
        if (lastVersionCode == -1) result = 0;
        else
            result = (lastVersionCode == currentVersionCode) ? 1 : 2;
        sp.edit().putInt("FIRSTTIMERUN", currentVersionCode).apply();
        return result;
    }
    public void nuevoHiloServidor()
    {
        hiloServidor nuevoHilo = new hiloServidor();
        nuevoHilo.start();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menuopciones, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.acercade)
        {
            Toast.makeText(this, R.string.acerca, Toast.LENGTH_LONG).show();
            Intent i = new Intent(getApplicationContext(), AcercaDe.class);
            startActivity(i);
            return true;
        }
        if (id == R.id.configuracion)
        {
            Toast.makeText(this, R.string.configuracion, Toast.LENGTH_LONG).show();
            Intent j = new Intent(getApplicationContext(), Configuracion.class);
            startActivity(j);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //*********************************Permiso microfono**********************************
    //Solicitar permiso
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void requestPermissionMicrofono()
    {
        //shouldShowRequestPermissionRationale es verdadero solamente si ya se había mostrado
        //anteriormente el dialogo de permisos y el usuario lo negó
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.RECORD_AUDIO))
        {
        } else {
            //si es la primera vez se solicita el permiso directamente
            requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO},
                    MY_WRITE_EXTERNAL_STORAGE);
        }
    }

    public void LanzarMiperfil()
    {
        Intent i = new Intent(this, MiPerfil.class);
        startActivity(i);
    }
}




