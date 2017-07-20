package tutorial.com.movilidad1;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;


public class PermisoMicrofono extends AppCompatActivity{

    private static final int MY_WRITE_EXTERNAL_STORAGE = 0;
    private View mLayout;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        verifyPermissionMicrofono();
    }

    //Paso 1. Verificar permiso
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void verifyPermissionMicrofono() {

        //WRITE_EXTERNAL_STORAGE tiene implícito READ_EXTERNAL_STORAGE porque pertenecen al mismo
        //grupo de permisos

        int writePermission = checkSelfPermission(Manifest.permission.RECORD_AUDIO);

        if (writePermission != PackageManager.PERMISSION_GRANTED) {
            requestPermissionMicrofono();
        } else {
            try {
            } catch (Exception e) {

           }

        }
    }

    //Paso 2: Solicitar permiso
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void requestPermissionMicrofono() {
        //shouldShowRequestPermissionRationale es verdadero solamente si ya se había mostrado
        //anteriormente el dialogo de permisos y el usuario lo negó
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.RECORD_AUDIO)) {
            requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO},
                    MY_WRITE_EXTERNAL_STORAGE);
        } else {
            //si es la primera vez se solicita el permiso directamente
            requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO},
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
                try {


                } catch (Exception e) {
                  Toast.makeText(getApplicationContext(), R.string.errormicro, Toast.LENGTH_LONG).show();


                }

            } else {
                Toast.makeText(getApplicationContext(), R.string.errormicro, Toast.LENGTH_LONG).show();

            }
        }
    }








}

