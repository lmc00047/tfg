package tutorial.com.movilidad1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Esta clase es la encargada de guardar las variables obtenidas de la vista Mi perfil, siendo éstas
 * datos personales del usuario.
 * Además de obtener las variables se encarga de guardarlas como preferencias compartidas y así las
 * demás clases puedan acceder a ellas.
 */
public class MiPerfil extends AppCompatActivity
{
    public static EditText name;
    public static EditText email;
    public static EditText pass;
    public static EditText emailcuidador;
    public static EditText tlfcuidador;
    public static EditText ipcuidador;
    public static Button botonGuardar;
    public static TextView politica;
    public static EditText clave;
    public static CheckBox chec;
    public static String nameS, emailS, passS, emailcuidadorS, tlfcuidadorS,ipcuidadorS,claveS;
    public SharedPreferences prefe;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.miperfil);

        name = (EditText) findViewById(R.id.usuario);
        email = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.pass);
        emailcuidador = (EditText) findViewById(R.id.emailCuidador);
        tlfcuidador = (EditText) findViewById(R.id.telefono);
        botonGuardar = (Button) findViewById(R.id.guardar);
        ipcuidador=(EditText) findViewById(R.id.DirIp);
        clave= (EditText) findViewById(R.id.clave);
        politica = (Button) findViewById(R.id.botonpolitica);
        chec= (CheckBox)findViewById(R.id.check);
        prefe=getSharedPreferences("datos", Context.MODE_PRIVATE);
        mostrar();

        botonGuardar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                nameS = name.getText().toString();
                emailS = email.getText().toString();
                passS = pass.getText().toString();
                emailcuidadorS = emailcuidador.getText().toString();
                tlfcuidadorS = tlfcuidador.getText().toString();
                ipcuidadorS = ipcuidador.getText().toString();
                claveS = clave.getText().toString();
                ejecutar();
            }
        });

        politica.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                Intent i = new Intent(getApplication(), Politica.class);
                startActivity(i);
            }
        });
        return;
    }

    public void ejecutar()
    {
        SharedPreferences.Editor editor=prefe.edit();
        editor.putString("name", nameS);
        editor.putString("email", emailS);
        editor.putString("pass", passS);
        editor.putString("emailcuidador", emailcuidadorS);
        editor.putString("tlfcuidador", tlfcuidadorS);
        editor.putString("ipcuidador", ipcuidadorS);
        editor.putString("clave", claveS);
        editor.commit();
    }

    public void mostrar()
    {
        name.setText(prefe.getString("name","").toString());
        email.setText(prefe.getString("email","").toString());
        pass.setText(prefe.getString("pass","").toString());
        emailcuidador.setText(prefe.getString("emailcuidador","").toString());
        tlfcuidador.setText(prefe.getString("tlfcuidador","").toString());
        ipcuidador.setText(prefe.getString("ipcuidador","").toString());
        clave.setText(prefe.getString("clave","").toString());
    }
}



