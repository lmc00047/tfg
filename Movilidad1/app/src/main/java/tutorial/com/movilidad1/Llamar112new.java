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
import android.widget.Toast;

/**
 * Clase que permite la realización de llamadas a emergencias.
 * Si en Configuración se establece la opción automática entonces cuando el botón encargado de esta función
 * sea pulsado, se realizará una llamada de forma automática.
 */

public class Llamar112new extends AppCompatActivity{

    private static final int MY_WRITE_EXTERNAL_STORAGE = 0;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        verifyPermissionLlamada();
    }

    //Paso 1. Verificar permiso
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void verifyPermissionLlamada()
    {
        //grupo de permisos

        int writePermission = checkSelfPermission(Manifest.permission.CALL_PHONE);
        writePermission = 554;
        if (writePermission != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissionLlamada();
        } else {
            try {
                if (LlamadaDirecta112.estadoLlamada == 1) {
                    Intent llamar112 = new Intent(Intent.ACTION_CALL, Uri.parse("tel:122"));
                    llamar112.setData(Uri.parse("tel:122"));
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    startActivity(llamar112);
                }else
                {
                    Intent llamar112 = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:112"));
                    llamar112.setData(Uri.parse("tel:112"));
                    startActivity(llamar112);
                }

                } catch(Exception e)
                {
                    Toast.makeText(getApplicationContext(), R.string.errorllamada, Toast.LENGTH_LONG).show();
                }
            }

    }
    //Paso 2: Solicitar permiso
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void requestPermissionLlamada()
    {
        //shouldShowRequestPermissionRationale es verdadero solamente si ya se había mostrado
        //anteriormente el dialogo de permisos y el usuario lo negó
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CALL_PHONE))
        {
            Toast.makeText(getApplicationContext(), R.string.permiso, Toast.LENGTH_LONG).show();
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE},
                    MY_WRITE_EXTERNAL_STORAGE);
        } else
        {
            //si es la primera vez se solicita el permiso directamente
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE},
                    MY_WRITE_EXTERNAL_STORAGE);
        }
    }

    //Paso 3: Procesar respuesta de usuario
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //Si el requestCode corresponde al que usamos para solicitar el permiso y
        //la respuesta del usuario fue positiva
        if (requestCode == MY_WRITE_EXTERNAL_STORAGE)
        {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                try
                {
                    if (LlamadaDirecta112.estadoLlamada == 1)
                    {
                        Intent llamar112 = new Intent(Intent.ACTION_CALL, Uri.parse("tel:122"));
                        llamar112.setData(Uri.parse("tel:122"));
                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        startActivity(llamar112);
                    }else {
                        Intent llamar112 = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:112"));
                        llamar112.setData(Uri.parse("tel:112"));
                        startActivity(llamar112);
                    }

                } catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(), R.string.errorllamada, Toast.LENGTH_LONG).show();
                    Intent cuidador = new Intent(this.getApplicationContext(), Configuracion.class);
                    startActivity(cuidador);
                }
            }else
            {
                Toast.makeText(getApplicationContext(), R.string.permiso, Toast.LENGTH_LONG).show();
            }
        }
    }
}


