package tutorial.com.movilidad1;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;


public class MiPerfil extends DialogFragment implements TextView.OnEditorActionListener {
    public static EditText name;
    private EditText email;


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
//creamos una instancia para el escuchador de eventos
        //Cuando haya una modificacion en el EditText.
        name.setOnEditorActionListener(this);
        name.requestFocus(); //recupere el

        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);//que salga el teclado visible
        getDialog().setTitle("Mi titulo"); //titulo del dialogo
        return view;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        NuevoDialogoListener activity =(NuevoDialogoListener) getActivity();
        Object texto;
        activity.FinalizaCuadroDialogo(name.getText().toString());
        activity.FinalizaCuadroDialogo(email.getText().toString());
        this.dismiss();
        return true;
    }
}



