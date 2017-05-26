package tutorial.com.movilidad1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Configuracion extends AppCompatActivity {

    private Button btnllamada;
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
                Fragment1 dialogFragment = Fragment1
                        .newInstance("¿De qué manera quiere llamar a emergencias?");
                dialogFragment.show(getSupportFragmentManager(), "dialog");
            }

        });

    }

    public void doPositiveClick(){
        Toast.makeText(this, R.string.Automatica, Toast.LENGTH_SHORT).show();
    }

    public void doNegativeClick(){
        Toast.makeText(this, R.string.Manual, Toast.LENGTH_SHORT).show();
    }
}

