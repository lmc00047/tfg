package tutorial.com.cuidadores;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;


public class MiPerfil extends DialogFragment implements TextView.OnEditorActionListener {

    public static EditText ipcuidador;
    public static Button botonGuardar;
    public static TextView politica;
    public static EditText clave;
    public static CheckBox chec;
    public static String ipcuidadorS, claveS;


    public interface NuevoDialogoListener{
        void FinalizaCuadroDialogo(String texto);
    }
//Constructor requerido para el DiaogFragment
    public MiPerfil(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        View view = inflater.inflate(R.layout.miperfil, container);

        botonGuardar = (Button) view.findViewById(R.id.guardar);
        ipcuidador=(EditText)view.findViewById(R.id.DirIp);
        clave= (EditText)view.findViewById(R.id.clave);
        politica = (Button) view.findViewById(R.id.botonpolitica);
        chec= (CheckBox)view.findViewById(R.id.check);




        //creamos una instancia para el escuchador de eventos
        //Cuando haya una modificacion en el EditText.


        botonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                ipcuidadorS = ipcuidador.getText().toString();
               claveS = clave.getText().toString();


            }

        });
        politica.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0){
                Intent i = new Intent(getContext(), Politica.class);
                startActivity(i);
            }
        });


        ipcuidador.setText(ipcuidadorS);
        clave.setText(claveS);


        //getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);//que salga el teclado visible
        getDialog().setTitle("Mi titulo"); //titulo del dialogo
        return view;


    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        NuevoDialogoListener activity =(NuevoDialogoListener) getActivity();
        Object texto;
        this.dismiss();
        return true;
    }
}



