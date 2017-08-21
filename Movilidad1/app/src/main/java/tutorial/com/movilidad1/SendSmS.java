package tutorial.com.movilidad1;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsManager;
import android.widget.Toast;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

/**
 * Actividad encargada de enviar SMS a emergencias. También permite el envío de un email al cuidador si está activada la opción
 * SMS-Email en la sección Mi perfil.
 * Además contiene el permiso para poder enviar SMS
 */

public class SendSmS extends Activity
{
    private static final int MY_WRITE_EXTERNAL_STORAGE = 0;
    Session session;
    ProgressDialog pdialog;
    Context context;
    public static int writePermission;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        verifyPermission();
    }

    //Paso 1. Verificar permiso
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void verifyPermission()
    {
        //grupo de permisos

        writePermission = checkSelfPermission(Manifest.permission.SEND_SMS);
        writePermission = 5668;

        if (writePermission != PackageManager.PERMISSION_GRANTED)
        {
            requestPermission();

        } else
            {
            try
            {
                if(MiPerfil.name.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(),R.string.errorsMs, Toast.LENGTH_LONG).show();
                    Intent cuidador = new Intent(this.getApplicationContext(), Configuracion.class);
                    startActivity(cuidador);

                }else
                    {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage("112", null, "Hola soy " + MiPerfil.name.getText().toString() + " y tengo una urgencia", null, null);
                    Toast.makeText(getApplicationContext(), "SMS enviado", Toast.LENGTH_LONG).show();

                    if (EnviarSmsoEmail.estadoEmail == 1)
                    {
                        EnviarEmail send = new EnviarEmail();
                        send.EnviarEmail(MiPerfil.emailcuidador.getText().toString(), "Emergencia", "Hola soy " + MiPerfil.name.getText().toString() + " y tengo una urgencia");
                    }
                }
            } catch (Exception e)
            {
                Toast.makeText(getApplicationContext(),R.string.errorsMs, Toast.LENGTH_LONG).show();
                Intent cuidador = new Intent(this.getApplicationContext(), Configuracion.class);
                startActivity(cuidador);
            }
        }
    }


    //Paso 2: Solicitar permiso
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void requestPermission()
    {
        //shouldShowRequestPermissionRationale es verdadero solamente si ya se había mostrado
        //anteriormente el dialogo de permisos y el usuario lo negó
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.SEND_SMS))
        {
            Toast.makeText(getApplicationContext(), R.string.permiso, Toast.LENGTH_LONG).show();
            requestPermissions(new String[]{Manifest.permission.SEND_SMS},
                    MY_WRITE_EXTERNAL_STORAGE);
        } else
            {
            //si es la primera vez se solicita el permiso directamente
            requestPermissions(new String[]{Manifest.permission.SEND_SMS},
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
                try {

                    if(MiPerfil.name.getText().toString().equals(""))
                    {
                        Toast.makeText(getApplicationContext(), R.string.errorsMs, Toast.LENGTH_LONG).show();
                        Intent cuidador = new Intent(this.getApplicationContext(), Configuracion.class);
                        startActivity(cuidador);

                    }else
                        {
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage("112", null, "Hola soy " + MiPerfil.name.getText().toString() + " y tengo una urgencia", null, null);
                        Toast.makeText(getApplicationContext(), "SMS enviado", Toast.LENGTH_LONG).show();


                        if (EnviarSmsoEmail.estadoEmail == 1)
                        {
                            EnviarEmail.rec = MiPerfil.email.getText().toString();
                            EnviarEmail.subject = "Emergencia";
                            context = this;
                            Properties props = new Properties();
                            props.put("mail.smtp.host", "smtp.gmail.com");
                            props.put("mail.smtp.socketFactory.port", "465");
                            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                            props.put("mail.smtp.auth", "true");
                            props.put("mail.smtp.port", "465");
                            session = Session.getDefaultInstance(props, new Authenticator() {
                                protected PasswordAuthentication getPasswordAuthentication() {
                                    return new PasswordAuthentication(MiPerfil.email.getText().toString(), MiPerfil.pass.getText().toString());
                                }
                            });

                            pdialog = ProgressDialog.show(context, "", "Enviando Email...", true);
                            EnviarEmail.RetreiveFeedTask task = new EnviarEmail.RetreiveFeedTask();
                            task.execute();
                        }
                    }

                } catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(), R.string.errorsMs, Toast.LENGTH_LONG).show();
                    Intent cuidador = new Intent(this.getApplicationContext(), Configuracion.class);
                    startActivity(cuidador);
                }

            } else
                {
                Toast.makeText(getApplicationContext(), R.string.permiso, Toast.LENGTH_LONG).show();
            }
        }
    }
}