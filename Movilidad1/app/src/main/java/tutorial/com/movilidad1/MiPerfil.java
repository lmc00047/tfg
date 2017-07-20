package tutorial.com.movilidad1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MiPerfil extends DialogFragment implements TextView.OnEditorActionListener {
    public static EditText name;
    public static EditText email;
    public static EditText pass;
    public static EditText emailcuidador;
    public static EditText tlfcuidador;
    public static EditText ipcuidador;
    public static Button botonGuardar;
    public static TextView politica;
    public static EditText clave;
    public static String nameS, emailS, passS, emailcuidadorS, tlfcuidadorS;


    public interface NuevoDialogoListener{
        void FinalizaCuadroDialogo(String texto);
    }
//Constructor requerido para el DiaogFragment
    public MiPerfil(){

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        View view = inflater.inflate(R.layout.miperfil, container);
        name = (EditText) view.findViewById(R.id.usuario);//queremos conseguir el texto
        email = (EditText) view.findViewById(R.id.email);
        pass = (EditText) view.findViewById(R.id.pass);
        emailcuidador = (EditText) view.findViewById(R.id.emailCuidador);
        tlfcuidador = (EditText) view.findViewById(R.id.telefono);
        botonGuardar = (Button) view.findViewById(R.id.guardar);
        ipcuidador=(EditText)view.findViewById(R.id.DirIp);
        clave= (EditText)view.findViewById(R.id.clave);
        politica = (Button) view.findViewById(R.id.botonpolitica);


        //creamos una instancia para el escuchador de eventos
        //Cuando haya una modificacion en el EditText.
        tlfcuidador.setOnEditorActionListener(this);
        tlfcuidador.requestFocus(); //dónde se pone el cursor cuando lo abres


        botonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                nameS = name.getText().toString();
                emailS = email.getText().toString();
                passS = pass.getText().toString();
                emailcuidadorS = emailcuidador.getText().toString();
                tlfcuidadorS = tlfcuidador.getText().toString();

            }

        });
        politica.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0){
                Intent i = new Intent(getContext(), Politica.class);
                startActivity(i);
            }
        });

        name.setText(nameS);
        email.setText(emailS);
        pass.setText(passS);
        emailcuidador.setText(emailcuidadorS);
        tlfcuidador.setText(tlfcuidadorS);


        //getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);//que salga el teclado visible
        getDialog().setTitle("Mi titulo"); //titulo del dialogo
        return view;


    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        NuevoDialogoListener activity =(NuevoDialogoListener) getActivity();
        Object texto;
        activity.FinalizaCuadroDialogo(name.getText().toString());//Toast nombre usuario
        this.dismiss();
        return true;
    }
}



