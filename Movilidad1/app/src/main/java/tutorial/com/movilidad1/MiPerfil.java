package tutorial.com.movilidad1;

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

/**
 * Esta clase es la encargada de guardar las variables obtenidas de la vista Mi perfil.
 * Además de obtener las variables se encarga de guardarlas y así las demás clases puedan
 * acceder a ellas.
 */
public class MiPerfil extends DialogFragment implements TextView.OnEditorActionListener
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


    public interface NuevoDialogoListener
    {
        void FinalizaCuadroDialogo(String texto);
    }
    //Constructor requerido para el DiaogFragment
    public MiPerfil(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState)
    {
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
        chec= (CheckBox)view.findViewById(R.id.check);


        //creamos una instancia para el escuchador de eventos
        //Cuando haya una modificación en el EditText.
        name.setOnEditorActionListener(this);
        //El puntero aparece en el nombre de usuario
        name.requestFocus();


        botonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                nameS = name.getText().toString();
                emailS = email.getText().toString();
                passS = pass.getText().toString();
                emailcuidadorS = emailcuidador.getText().toString();
                tlfcuidadorS = tlfcuidador.getText().toString();
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

        name.setText(nameS);
        email.setText(emailS);
        pass.setText(passS);
        emailcuidador.setText(emailcuidadorS);
        tlfcuidador.setText(tlfcuidadorS);
        clave.setText(claveS);
        ipcuidador.setText(ipcuidadorS);

        getDialog().setTitle("Mi titulo"); //titulo del dialogo
        return view;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
    {
        NuevoDialogoListener activity =(NuevoDialogoListener) getActivity();
        Object texto;
        //Aparece el nombre de usuario por pantalla
        activity.FinalizaCuadroDialogo(name.getText().toString());
        this.dismiss();
        return true;
    }
}



