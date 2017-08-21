package tutorial.com.movilidad1;
        import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Esta clase permite enviar un email a la dirección de correo electrónico del cuidador establecida
 * en la sección de Mi perfil, ofreciendo la posibilidad de enviar un SMS cuando se envía el email.
 * Esta opción se establece en el menú Configuración, cambiando una variable.
 *
 * El código para poder enviar email está disponible en:
 * https://www.youtube.com/watch?v=i-7tUdtFbIg&feature=youtu.be
 *
 * El código para establecer los permisos requeridos a partir de la versión 6 está disponible en:
 * https://androidstudiofaqs.com/tutoriales/dar-permisos-a-aplicaciones-en-android-studio
 *
 */
public class EnviarEmail extends Activity implements View.OnClickListener, Serializable
{
    static Session session;
    static Context context;
    static EditText reciep, sub, msg;
    static String rec, subject, textMessage, textoSiri;

    public EnviarEmail(){}

    public EnviarEmail(String rec, String subject, String textMessage)
    {
        this.rec = rec;
        this.subject = subject;
        this.textMessage = textMessage;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sendemail);

        context = this;
        Button login = (Button) findViewById(R.id.btn_submit);
        reciep = (EditText) findViewById(R.id.et_to);
        sub = (EditText) findViewById(R.id.et_sub);
        msg = (EditText) findViewById(R.id.et_text);

        reciep.setText(MiPerfil.emailcuidador.getText().toString());

        //Recoge la salida de la clase VozTexto.java y lo muestra
        textoSiri = getIntent().getExtras().getString("Siri");
        msg.setText(textoSiri);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        rec = reciep.getText().toString();
        subject = sub.getText().toString();
        textMessage = msg.getText().toString();

        // Construcción del email
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        //Establece la autenticación del correo electrónico comprobando la contraseña y dirección del email.

        if (MiPerfil.email.getText().toString().equals("") || MiPerfil.pass.getText().toString().equals("")|| MiPerfil.emailcuidador.getText().toString().equals("") ){

            Toast.makeText(this,R.string.ErrorEmail,Toast.LENGTH_SHORT).show();
        }
        else {
            session = Session.getDefaultInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(MiPerfil.email.getText().toString(), MiPerfil.pass.getText().toString());
                }
            });
        }
        RetreiveFeedTask task = new RetreiveFeedTask();
        AsyncTask<String, Void, String> aux = task.execute();
        Toast.makeText(getApplicationContext(), ""+aux.getStatus(), Toast.LENGTH_LONG).show();

        /**
         * Si la opción Email-SMS está activada, entonces además de enviar el email, enviará un SMS al cuidador
         * con el mismo contenido que aparece en el email.
         */
        if(EnviarSmsoEmail.estadoSms==1)
        {
            try {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(MiPerfil.tlfcuidador.getText().toString(), null, textMessage, null, null);
                Toast.makeText(getApplicationContext(), R.string.SMS, Toast.LENGTH_LONG).show();
            } catch (Exception e)
            {
                Toast.makeText(getApplicationContext(), R.string.ErrorSMS, Toast.LENGTH_LONG).show();
                Intent cuidador = new Intent(this.getApplicationContext(), Configuracion.class);
                startActivity(cuidador);
            }
        }
    }

    public static class RetreiveFeedTask extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... params)
        {
            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(rec));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(rec));
                message.setSubject(subject);
                message.setContent(textMessage, "text/html; charset=utf-8");
                Transport.send(message);
            } catch (MessagingException e)
            {
                e.printStackTrace();
            } catch (Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result){
        }
    }

    public static void EnviarEmail(String rect, String subject2, String textMessage2)
    {
        rec = rect;
        subject = subject2;
        textMessage = textMessage2;

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

        RetreiveFeedTask task = new RetreiveFeedTask();
        task.execute();
    }
}
