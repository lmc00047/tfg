package tutorial.com.cuidadores;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

        nuevoHiloServidor();

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); //pantalla activa



    }


    public void nuevoHiloServidor(){
        hiloServidor nuevoHilo = new hiloServidor();
        nuevoHilo.start();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuopciones, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.acercade) {
            Toast.makeText(this, R.string.acerca, Toast.LENGTH_LONG).show();
            Intent i = new Intent(getApplicationContext(), AcercaDe.class);
            startActivity(i);
            return true;
        }
        if (id == R.id.configuracion) {
            Toast.makeText(this, R.string.configuracion, Toast.LENGTH_LONG).show();
            Intent j = new Intent(getApplicationContext(), Configuracion.class);
            startActivity(j);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }










}




