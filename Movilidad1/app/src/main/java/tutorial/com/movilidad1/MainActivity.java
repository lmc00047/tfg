package tutorial.com.movilidad1;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //Declaración de variables
    private View mLayout;
    public View microfono;
    protected PowerManager.WakeLock wakelock; //Pantalla siempre activa
    private static final int MY_WRITE_EXTERNAL_STORAGE = 0;
    int aux = 0;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLayout = findViewById(R.id.fragmentbotones);
        microfono = findViewById(R.id.microfono);


        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); //pantalla activa

        if (aux == 0) {
            requestPermission();
            aux = 1;
        }
    }

    public void onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        microfono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

            }

        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuopciones, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //String idS;
        //idS = id+"";
        //Toast.makeText(this,idS, Toast.LENGTH_LONG).show();

        if (id == R.id.microfono) {
            Toast.makeText(this, R.string.reconocimiento, Toast.LENGTH_LONG).show();

            Intent i = new Intent(getApplicationContext(), ReconocimientoVoz.class);
            startActivity(i);

            return true;
        }

        if (id == R.id.configuracion) {
            Toast.makeText(this, R.string.configuracion, Toast.LENGTH_LONG).show();
            Intent j = new Intent(getApplicationContext(), Configuracion.class);
            startActivity(j);

            return true;
        }
        /*if (id == R.id.personalizacion) {
            Toast.makeText(this, R.string.personalizacion, Toast.LENGTH_LONG).show();
            return true;
            Intent i = new Intent(getApplicationContext(), AcercaDe.class);
            startActivity(i);
        }*/
        if (id == R.id.acercade) {
            Toast.makeText(this, R.string.acerca, Toast.LENGTH_LONG).show();
            Intent i = new Intent(getApplicationContext(), AcercaDe.class);
            startActivity(i);
            return true;
        }
        /*if (id == R.id.corazon) {
            Toast.makeText(this, "Constantes vitales", Toast.LENGTH_LONG).show();
            Intent i = new Intent(getApplicationContext(), ReconocimientoVoz.class);
            startActivity(i);
            return true;
        }*/


        return super.onOptionsItemSelected(item);
    }

    //Paso 2: Solicitar permiso
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void requestPermission() {
        //shouldShowRequestPermissionRationale es verdadero solamente si ya se había mostrado
        //anteriormente el dialogo de permisos y el usuario lo negó
        Toast.makeText(this, "OLEEEEEEE", Toast.LENGTH_LONG).show();
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.SEND_SMS)) {
            showSnackBar();
        } else {
            //si es la primera vez se solicita el permiso directamente
            requestPermissions(new String[]{Manifest.permission.SEND_SMS},
                    MY_WRITE_EXTERNAL_STORAGE);
        }
    }

    /**
     * Método para mostrar el snackbar de la aplicación.
     * Snackbar es un componente de la librería de diseño 'com.android.support:design:23.1.0'
     * y puede ser personalizado para realizar una acción, como por ejemplo abrir la actividad de
     * configuración de nuestra aplicación.
     */
    private void showSnackBar() {
        Snackbar.make(mLayout, R.string.permission_sms_send,
                Snackbar.LENGTH_LONG)
                .setAction(R.string.settings, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openSettings();
                    }
                })
                .show();
    }

    /**
     * Abre el intento de detalles de configuración de nuestra aplicación
     */
    public void openSettings() {
        Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }



}




