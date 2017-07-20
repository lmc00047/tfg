package tutorial.com.movilidad1;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.speech.tts.TextToSpeech;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
    //Declaración de variables
    private View mLayout;
    public View microfono;
    protected PowerManager.WakeLock wakelock; //Pantalla siempre activa
    private static final int MY_WRITE_EXTERNAL_STORAGE = 0;
    int aux = 0;
    public Thread sms;
    public static int aux_sms = 0;
    public static String mensaje = "";
    public static TextToSpeech tts;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLayout = findViewById(R.id.fragmentbotones);
        microfono = findViewById(R.id.microfono);
        tts = new TextToSpeech(this, this);

        button_start();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); //pantalla activa

        // if (aux == 0) {
        requestPermission();
        requestPermissionLlamada();
        requestPermissionMicrofono();


        //Si es la primera vez que se instala la aplicación, entonces muestra el cuadro de dialogo del perfil
        switch (getFirstTimeRun()) {
            case 0:

                dialogoPersonalizado();
                break;

        }


    }

    //Método para saber si ha sido la primera vez que se ha instalado la aplicación o no.
    private int getFirstTimeRun() {
        SharedPreferences sp = getSharedPreferences("MYAPP", 0);
        int result, currentVersionCode = BuildConfig.VERSION_CODE;
        int lastVersionCode = sp.getInt("FIRSTTIMERUN", -1);
        if (lastVersionCode == -1) result = 0;
        else
            result = (lastVersionCode == currentVersionCode) ? 1 : 2;
        sp.edit().putInt("FIRSTTIMERUN", currentVersionCode).apply();
        return result;

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

        if (id == R.id.acercade) {
            Toast.makeText(this, R.string.acerca, Toast.LENGTH_LONG).show();
            Intent i = new Intent(getApplicationContext(), AcercaDe.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //********************************Permiso Sms**************************************************
    //Paso 2: Solicitar permiso
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void requestPermission() {
        //shouldShowRequestPermissionRationale es verdadero solamente si ya se había mostrado
        //anteriormente el dialogo de permisos y el usuario lo negó

        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.SEND_SMS)) {
        } else {
            //si es la primera vez se solicita el permiso directamente
            requestPermissions(new String[]{Manifest.permission.SEND_SMS},
                    MY_WRITE_EXTERNAL_STORAGE);
        }
    }

    //*********************************Permiso llamada********************************************
    //Paso 2: Solicitar permiso
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void requestPermissionLlamada() {
        //shouldShowRequestPermissionRationale es verdadero solamente si ya se había mostrado
        //anteriormente el dialogo de permisos y el usuario lo negó
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CALL_PHONE)) {
        } else {
            //si es la primera vez se solicita el permiso directamente
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE},
                    MY_WRITE_EXTERNAL_STORAGE);
        }
    }

    //*********************************Permiso microfono**********************************
    //Paso 2: Solicitar permiso
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void requestPermissionMicrofono() {
        //shouldShowRequestPermissionRationale es verdadero solamente si ya se había mostrado
        //anteriormente el dialogo de permisos y el usuario lo negó
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.RECORD_AUDIO)) {
        } else {
            //si es la primera vez se solicita el permiso directamente
            requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO},
                    MY_WRITE_EXTERNAL_STORAGE);
        }
    }

    //*********************************FIN************************************************
    public void dialogoPersonalizado() {
        MiPerfil dialogoPersonalizado = new MiPerfil();
        dialogoPersonalizado.show(getSupportFragmentManager(), "personalizado");

        android.app.Fragment frag = getFragmentManager().findFragmentByTag("personalizado"); //encuentra por etiqueta al personalizado
        if (frag != null) {// el fragmento no esta visible y esta en memoria aun, lo borramos
            getFragmentManager().beginTransaction().remove(frag).commit();
        }
    }
    public void button_start() {
        sms = new Thread(new Runnable() {
            @Override
            public void run() {

                while (true){
                    //Toast.makeText(getApplicationContext(),"holaaaaaaaaaaaa",Toast.LENGTH_SHORT).show();
                   if( aux_sms == 1){
                       //Intent i = new Intent(getApplicationContext(),TextoVozSMS.class);
                       //startActivity(i);
                       tts.speak(mensaje, TextToSpeech.QUEUE_FLUSH, null);
                   }
                }
            }
        });

        sms.start();
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.getDefault());
            if (result == TextToSpeech.LANG_NOT_SUPPORTED || result == TextToSpeech.LANG_MISSING_DATA) {
                Log.e("TTS", "Este lenguaje no es soportado");
            } else {
                //btn.setEnabled(true);
                speakOut();
            }
        } else {
            Log.e("TTS", "Inicialización del lenguaje es fallida");
        }
    }

    public void speakOut() {
        String text = MessageReceiver.str;
        tts.speak(mensaje, TextToSpeech.QUEUE_FLUSH, null);
    }
}




