package tutorial.com.movilidad1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Configuracion extends AppCompatActivity implements MiPerfil.NuevoDialogoListener {

    public Button btnllamada;
    private Button btnmsgsms;
    private Button btnleer;
    private Button btnperfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configuracion);

        btnmsgsms = (Button) findViewById(R.id.botonEnviarsms);
        btnleer = (Button) findViewById(R.id.botonleerSms);
        btnperfil = (Button) findViewById(R.id.botonnombre);
        btnllamada = (Button)findViewById(R.id.botonLlamada112);

        btnllamada.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                LlamadaDirecta112 dialogFragment = LlamadaDirecta112
                        .newInstance("¿De qué manera quiere llamar a emergencias?");
                dialogFragment.show(getSupportFragmentManager(), "dialog");
            }

        });

        btnleer.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                EscucharMensajes dialogFragment = EscucharMensajes
                        .newInstance("¿Quiere escuchar los mensajes?");
                dialogFragment.show(getSupportFragmentManager(), "dialog");
            }

        });

        btnmsgsms.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View arg0) {
        // TODO Auto-generated method stub
        EnviarSmsoEmail dialogFragment = EnviarSmsoEmail
                .newInstance("¿Quiere enviar sms o email?");
        dialogFragment.show(getSupportFragmentManager(), "dialog");
    }

});
        btnperfil.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0){
                dialogoPersonalizado();
            }
        });
    }


    public void doPositiveClick(){
        //Toast.makeText(this, R.string.si, Toast.LENGTH_SHORT).show();
    }

    public void doNegativeClick(){
       // Toast.makeText(this, R.string.no, Toast.LENGTH_SHORT).show();
    }



    public void dialogoPersonalizado() {
        MiPerfil dialogoPersonalizado = new MiPerfil();
        dialogoPersonalizado.show(getSupportFragmentManager(), "personalizado");

        android.app.Fragment frag = getFragmentManager().findFragmentByTag("personalizado"); //encuentra por etiqueta al personalizado
        if (frag != null) {// el fragmento no esta visible y esta en memoria aun, lo borramos
            getFragmentManager().beginTransaction().remove(frag).commit();
        }
    }
    /**
     * Termiana dialogo personalizado. Recibimos el texto por el fragment y lo mostramos en pantalla
     * @param texto
     */
    @Override
    public void FinalizaCuadroDialogo(String texto) {
        Toast.makeText(this,texto,Toast.LENGTH_SHORT).show();
        Toast.makeText(this,texto,Toast.LENGTH_LONG).show();
    }
}



