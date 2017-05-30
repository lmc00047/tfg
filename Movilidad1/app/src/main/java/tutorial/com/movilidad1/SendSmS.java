package tutorial.com.movilidad1;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SendSmS extends Activity {

    private static final int MY_WRITE_EXTERNAL_STORAGE = 0;
    private View mLayout;
    private EditText name;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        verifyPermission();
    }

    //Paso 1. Verificar permiso
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void verifyPermission() {

        //WRITE_EXTERNAL_STORAGE tiene implícito READ_EXTERNAL_STORAGE porque pertenecen al mismo
        //grupo de permisos

        int writePermission = checkSelfPermission(Manifest.permission.SEND_SMS);

        if (writePermission != PackageManager.PERMISSION_GRANTED) {
            requestPermission();
        } else {
            try {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage("5554", null, "Hola "+MiPerfil.name.getText().toString()+" Este mensaje ha sido enviado", null, null);
                Toast.makeText(getApplicationContext(), "SMS enviado", Toast.LENGTH_LONG).show();
            } catch (Exception e) {


                Toast.makeText(getApplicationContext(), "SMS FAIL", Toast.LENGTH_LONG).show();
            }
        }
    }


    //Paso 2: Solicitar permiso
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void requestPermission() {
        //shouldShowRequestPermissionRationale es verdadero solamente si ya se había mostrado
        //anteriormente el dialogo de permisos y el usuario lo negó
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.SEND_SMS)) {
            showSnackBar();
        } else {
            //si es la primera vez se solicita el permiso directamente
            requestPermissions(new String[]{Manifest.permission.SEND_SMS},
                    MY_WRITE_EXTERNAL_STORAGE);
        }
    }

    //Paso 3: Procesar respuesta de usuario
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        //Si el requestCode corresponde al que usamos para solicitar el permiso y
        //la respuesta del usuario fue positiva
        if (requestCode == MY_WRITE_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //saveComments();
                try {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage("5554", null, "Hola "+MiPerfil.name.getText().toString()+" Este mensaje ha sido enviado", null, null);
                    Toast.makeText(getApplicationContext(), "SMS enviado", Toast.LENGTH_LONG).show();
                } catch (Exception e) {


                    Toast.makeText(getApplicationContext(), "SMS FAIL", Toast.LENGTH_LONG).show();
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





}