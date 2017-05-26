package tutorial.com.movilidad1;

import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import java.util.Vector;

public class MainActivity extends AppCompatActivity {
    //Declaración de variables
    private static final int MY_WRITE_EXTERNAL_STORAGE = 0;
    private String comments = null;
    private View mLayout;
    public View microfono;
    public View corazon;
    public View configuracion;
    private static final int VOICE_RECOGNITION_REQUEST_CODE = 1;
    private Button bt_start;
    private Vector<String> nombres;
    private Vector<String> telefonos;
    protected PowerManager.WakeLock wakelock; //Pantalla siempre activa

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLayout = findViewById(R.id.fragmentbotones);
        microfono = findViewById(R.id.microfono);
        configuracion = findViewById(R.id.configuracion);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); //pantalla activa
    }

    public void onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        microfono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

            }

        });
        corazon.setOnClickListener(new View.OnClickListener() {
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
        if (id == R.id.microfono) {
            Toast.makeText(this, R.string.reconocimiento, Toast.LENGTH_LONG).show();

            Intent i = new Intent(getApplicationContext(), ReconocimientoVoz.class);
            startActivity(i);

            return true;
        }

        if (id == R.id.configuracion) {
            Toast.makeText(this, R.string.configuracion, Toast.LENGTH_LONG).show();
            return true;
        }
        if (id == R.id.personalizacion) {
            Toast.makeText(this, R.string.personalizacion, Toast.LENGTH_LONG).show();
            return true;
        }
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





}




