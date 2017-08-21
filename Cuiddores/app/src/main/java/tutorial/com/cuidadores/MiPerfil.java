package tutorial.com.cuidadores;

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
    public static EditText ipcuidador;
    public static Button botonGuardar;
    public static TextView politica;
    public static EditText clave;
    public static CheckBox chec;
    public static String ipcuidadorS, claveS;
    public SharedPreferences prefe;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.miperfil);

        botonGuardar = (Button) findViewById(R.id.guardar);
        ipcuidador=(EditText)findViewById(R.id.DirIp);
        clave= (EditText)findViewById(R.id.clave);
        politica = (Button)findViewById(R.id.botonpolitica);
        chec= (CheckBox)findViewById(R.id.check);
        prefe=getSharedPreferences("datos", Context.MODE_PRIVATE);
        mostrar();

        botonGuardar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
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
        return ;
    }
    public void ejecutar()
    {
        SharedPreferences.Editor editor=prefe.edit();
        editor.putString("ipCuidador", ipcuidadorS);
        editor.putString("claveCuidador", claveS);
        editor.commit();
    }

    public void mostrar()
    {
        clave.setText(prefe.getString("claveCuidador","").toString());
        ipcuidador.setText(prefe.getString("ipCuidador","").toString());
    }
}



