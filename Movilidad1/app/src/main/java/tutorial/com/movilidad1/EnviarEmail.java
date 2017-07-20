package tutorial.com.movilidad1;
        import android.app.Activity;
import android.app.ProgressDialog;
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
 * Esta clase permite enviar un email a una dirección de correo electrónico.
 * También si se activa en configuración la opción de enviar sms junto con el email, aquí esta la parte del sms que se envía.
 */
public class EnviarEmail extends Activity implements View.OnClickListener, Serializable {

    static Session session;
    static ProgressDialog pdialog;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sendemail);

        context = this;

        Button login = (Button) findViewById(R.id.btn_submit);
        reciep = (EditText) findViewById(R.id.et_to);
        sub = (EditText) findViewById(R.id.et_sub);
        msg = (EditText) findViewById(R.id.et_text);

       textoSiri = getIntent().getExtras().getString("Siri");

        reciep.setText(MiPerfil.emailcuidador.getText().toString());

        msg.setText(textoSiri);

        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        rec = reciep.getText().toString();
        subject = sub.getText().toString();
        textMessage = msg.getText().toString();
        //Toast.makeText(getApplicationContext(),MiPerfil.email.getText().toString(), Toast.LENGTH_LONG).show();

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
//"appTFGLaura@gmail.com", "appTFGLaura16"
        session = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(MiPerfil.email.getText().toString(), MiPerfil.pass.getText().toString());
            }
        });

        RetreiveFeedTask task = new RetreiveFeedTask();
        //pdialog = ProgressDialog.show(context, "", "Enviando mensaje...", true);
        task.execute();
       Toast.makeText(getApplicationContext(), "Email enviado", Toast.LENGTH_LONG).show();
        if(EnviarSmsoEmail.estadoSms==1)
        {
            try {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(MiPerfil.tlfcuidador.getText().toString(), null, textMessage, null, null);
                Toast.makeText(getApplicationContext(), "SMS enviado", Toast.LENGTH_LONG).show();
            } catch (Exception e) {


                Toast.makeText(getApplicationContext(), "ERROR SMS. Para poder enviar SMS hay que establecer un nombre en configuración.", Toast.LENGTH_LONG).show();
                Intent cuidador = new Intent(this.getApplicationContext(), Configuracion.class);
                startActivity(cuidador);
            }
        }
    }

    public static class RetreiveFeedTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
//            Toast.makeText(context, "SMS Sergio", Toast.LENGTH_LONG).show();
            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(rec));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(rec));
                message.setSubject(subject);
                message.setContent(textMessage, "text/html; charset=utf-8");
                Transport.send(message);
            } catch (MessagingException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
           /* pdialog.dismiss();
            reciep.setText("");
            msg.setText("");
            sub.setText("");*/
            //Toast.makeText(, R.string.enviarmensaje, Toast.LENGTH_LONG).show();
        }
    }

    public static void EnviarEmail(String rect, String subject2, String textMessage2)
    {
        rec = rect;
        subject = subject2;
        textMessage = textMessage2;

     // Toast.makeText(getApplicationContext(),MiPerfil.email.getText().toString(), Toast.LENGTH_LONG).show();

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
//"appTFGLaura@gmail.com", "appTFGLaura16"
        session = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(MiPerfil.email.getText().toString(), MiPerfil.pass.getText().toString());
            }
        });

       // pdialog = ProgressDialog.show(context, "", "Enviando mensaje...", true);

        RetreiveFeedTask task = new RetreiveFeedTask();
        task.execute();
    }
}
