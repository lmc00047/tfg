package com.example.laura.apptfg;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.speech.RecognizerIntent;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.text.format.Time;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    //Declaración de variables
    private static final int  MY_SEND_SMS = 0;
    private String comments = null;
    private View mLayout;
    private static final int VOICE_RECOGNITION_REQUEST_CODE = 1;
    private Button bt_start;
    private Vector<String> nombres;
    private Vector<String> telefonos;
    protected PowerManager.WakeLock wakelock; //Pantalla siempre activa


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //Elegimos la vista de la actividad
        mLayout = findViewById(R.id.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        //Damos permisos al principio de la aplicación




		//Declaramos los botones de la vista
        final Button botonFecha = (Button) findViewById(R.id.button6);
        Button botonllamada = (Button) findViewById(R.id.button);
        final Button botonemergencia = (Button) findViewById(R.id.button4);
        Button botonvoz = (Button) findViewById(R.id.button2);


        //Obtener fecha actual
        Calendar calendarNow = Calendar.getInstance();
        final int dia = calendarNow.get(Calendar.DAY_OF_MONTH);
        final int mes = calendarNow.get(Calendar.MONTH);
        final int año = calendarNow.get(Calendar.YEAR);

        //Obtener hora actual y refrescar cada segundo la hora mostrada y la fecha
        final Runnable updateTask = new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
            public void run() {
                Time now = new Time();
                now.setToNow();
                String hora = now.format("%H:%M:%S"); //Hora:minutos:segundos
                botonFecha.setText(dia + "/" + mes + "/" + año + "\n" + "" + hora);
            }

        };

        //Timer para actualizar la hora cada segundo
        Timer timer = new Timer("DigitalClock");
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(updateTask);
            }
        }, 1, 1000);

        //OnClick del boton llamada (boton rojo)
        botonllamada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Llamar a una persona
                Intent llamar = new Intent(Intent.ACTION_DIAL);
                startActivity(llamar);
            }
        });

        //OnClick del boton emergencias (boton amarillo)
        botonemergencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                //Llamar directamente al 112
                Intent llamar112 = new Intent(Intent.ACTION_DIAL);
                llamar112.setData(Uri.parse("tel:112"));
                startActivity(llamar112);
            }

        });


        //OnClick Largo del boton emergencias (boton amarillo)
        botonemergencia.setOnLongClickListener(new View.OnLongClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public boolean onLongClick(View arg0) {
                mandarSMS(); //permisos para mandar el sms

                return true;
            }

        });

        //OnClick del boton Reconocimiento de voz
        botonvoz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                //Lanzamos el reconocimiento de voz
                startVoiceRecognitionActivity();
            }

        });

    }

    //Paso 1. Verificar permiso
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void mandarSMS() {

        //WRITE_EXTERNAL_STORAGE tiene implícito READ_EXTERNAL_STORAGE porque pertenecen al mismo grupo de permisos

        int writePermission = checkSelfPermission(Manifest.permission.SEND_SMS);

        if (writePermission != PackageManager.PERMISSION_GRANTED) {
            requestPermission();
        } else {
            try {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage("1234", null, "Hola Laura Este mensaje ha sido enviado", null, null);
                Toast.makeText(getApplicationContext(), "SMS enviado", Toast.LENGTH_LONG).show();
            } catch (Exception e) {


                Toast.makeText(getApplicationContext(), "Su SMS no pudo ser enviado", Toast.LENGTH_LONG).show();
            }
        }
    }


    //Paso 2: Solicitar permiso
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestPermission() {
        //shouldShowRequestPermissionRationale es verdadero solamente si ya se había mostrado
        //anteriormente el dialogo de permisos y el usuario lo negó
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.SEND_SMS)) {
            showSnackBar();
        } else {
            //si es la primera vez se solicita el permiso directamente
            requestPermissions(new String[]{Manifest.permission.SEND_SMS},
                     MY_SEND_SMS);
        }
    }

    //Paso 3: Procesar respuesta de usuario (Primera vez que lo pide)
    @RequiresApi(api = Build.VERSION_CODES.DONUT)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        //Si el requestCode corresponde al que usamos para solicitar el permiso y
        //la respuesta del usuario fue positiva
        if (requestCode == MY_SEND_SMS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //saveComments();

                try {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage("1234", null, "Hola Laura Este mensaje ha sido enviado", null, null);
                    Toast.makeText(getApplicationContext(), "SMS enviado", Toast.LENGTH_LONG).show();
                } catch (Exception e) {

                    Toast.makeText(getApplicationContext(), "Su SMS no pudo ser enviarse", Toast.LENGTH_LONG).show();
                }

            } else {
                showSnackBar();
            }
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

	//Reconocimiento de voz
    private void startVoiceRecognitionActivity() {
        // Definición del intent para realizar en análisis del mensaje
        Intent voz = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        // Indicamos el modelo de lenguaje para el intent
        voz.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        // Definimos el mensaje que aparecerá
        voz.putExtra(RecognizerIntent.EXTRA_PROMPT, "Diga, Llamar a ...");
        // Lanzamos la actividad esperando resultados
        startActivityForResult(voz, VOICE_RECOGNITION_REQUEST_CODE);
    }

    @Override
    //Recogemos los resultados del reconocimiento de voz
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Si el reconocimiento ha sido bueno
        if (requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == RESULT_OK) {
            //El intent nos envia un ArrayList aunque en este caso solo utilizaremos la pos.0
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            //Separo el texto en palabras.
            String[] palabras = matches.get(0).toString().split(" ");
            //Si la primera palabra es LLAMAR
            if (palabras[0].equals("llamar")) {
                for (int a = 0; a < nombres.size(); a++) {
                    //Busco el nombre que es la tercera posicion (LLAMAR A LORENA)
                    if (nombres.get(a).equals(palabras[2])) {
                        //Si la encuentra recojo el numero telf en el otro vector que coincidira con la posicion
						//ya que los hemos rellenado a la vez.
                        Intent vozLlamar = new Intent(Intent.ACTION_CALL);
                        vozLlamar.setData(Uri.parse("tel:" + telefonos.get(a)));
                        //Realizo la llamada
                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        startActivity(vozLlamar);
                        break;
                    }
                }
            }
        }
    }

    
}
