package tutorial.com.movilidad1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

/**
 * Created by LauraMoreno on 09/06/2017.
 */

public class permiso extends AppCompatActivity {
    private static final int MY_WRITE_EXTERNAL_STORAGE = 0;
    private View mLayout;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        verifyPermissionLlamada();
    }

    //Paso 1. Verificar permiso
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void verifyPermissionLlamada() {

        //WRITE_EXTERNAL_STORAGE tiene implícito READ_EXTERNAL_STORAGE porque pertenecen al mismo
        //grupo de permisos

        int writePermission = checkSelfPermission(Manifest.permission.CALL_PHONE);

        if (writePermission != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLlamada();
        } else {
            try {
                Intent llamar112 = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + MiPerfil.tlfcuidador.getText().toString()));
                llamar112.setData(Uri.parse("tel:" + MiPerfil.tlfcuidador.getText().toString()));
                startActivity(llamar112);

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Para poder llamar al cuidador hay que establecer el número de telefono en configuración.", Toast.LENGTH_LONG).show();
                Intent cuidador = new Intent(this.getApplicationContext(), Configuracion.class);
                startActivity(cuidador);

            }

        }
    }


    //Paso 2: Solicitar permiso
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void requestPermissionLlamada() {
        //shouldShowRequestPermissionRationale es verdadero solamente si ya se había mostrado
        //anteriormente el dialogo de permisos y el usuario lo negó
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CALL_PHONE)) {
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE},
                    MY_WRITE_EXTERNAL_STORAGE);
        } else {
            //si es la primera vez se solicita el permiso directamente
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE},
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
                    Intent llamar112 = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + MiPerfil.tlfcuidador.getText().toString()));
                    llamar112.setData(Uri.parse("tel:" + MiPerfil.tlfcuidador.getText().toString()));
                    startActivity(llamar112);

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Para poder llamar al cuidador hay que establecer el número de telefono en configuración.", Toast.LENGTH_LONG).show();
                    Intent cuidador = new Intent(this.getApplicationContext(), Configuracion.class);
                    startActivity(cuidador);

                }
            }
        }
    }








}