package tutorial.com.movilidad1;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class FragmentBotones extends Fragment
{

    private View mLayout;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragmentbotones, container, false);
        Button botonllamada = (Button) view.findViewById(R.id.button);
        Button botonRtp = (Button) view.findViewById(R.id.botonAzul);
        Button botonSms = (Button) view.findViewById(R.id.botonVerde);
        Button botonEmergencias = (Button) view.findViewById(R.id.botonAmarillo);




//-----------------------------------------BOTON ROJO----------------------------------------------------//
        //OnClick del boton llamada
        botonllamada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Llamar a una persona
                lanzarLlamada();
            }
        });

//-----------------------------------------FIN BOTON ROJO------------------------------------------------//


//-----------------------------------------BOTON AMARILLO------------------------------------------------//
    //OnClick del boton llamada Emergencias
        botonEmergencias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
            lanzarLlamada112();



            }

        });
    //OnLongClick del boton llamada Emergencias
      botonEmergencias.setOnLongClickListener(new View.OnLongClickListener() {
        //@RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public boolean onLongClick(View arg0) {
          lanzarSms();
            //verifyPermission();

            return true;

        }

        });


//-----------------------------------------FIN BOTON AMARILLO----------------------------------------------------//


//-----------------------------------------BOTON VERDE----------------------------------------------------//
    //OnClick del boton Mensaje (De voz a texto)
        botonSms.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg1) {
                lanzarVozTexto();
            }
        });

    //onLongClick del boton Mensje (De texto a voz)
        botonSms.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View arg0) {
                lanzarSiri();
                return true;

            }
        });

//-----------------------------------------FIN BOTON VERDE------------------------------------------------//


//-----------------------------------------BOTON AZUL----------------------------------------------------//
    //OnClick del boton RTP
        botonRtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                ////Escucharmsg();
                lanzarmicrofono();

        }

        });


//
//  OnClick Largo del boton emergencias
       botonRtp.setOnLongClickListener(new View.OnLongClickListener() {
           @Override
            public boolean onLongClick(View arg0) {

               lanzarIntercomunicador();

               return true;
            }

       });
//-----------------------------------------FIN BOTON AZUL------------------------------------------------//
        return view;

    }
    //Lanzar Activity Reconocimiento de voz
       public void lanzarReconocimiento()
    {
        Intent i = new Intent(getContext(), ReconocimientoVoz.class);
        startActivity(i);
    }


    //Lanzar Activity Texto a Voz- Siri
    public void lanzarSiri()
    {
        Intent i = new Intent(getContext(), TextoVoz.class);
        startActivity(i);
    }

    //Lanzar Activity Voz-->Texto
    public void lanzarVozTexto()
    {
        Intent i = new Intent(getContext(), VozTexto.class);
        startActivity(i);
    }

    //Lanzar llamada al 112
    public void lanzarLlamada112()
    {
        Intent i = new Intent(getContext(), Llamar112.class);
        startActivity(i);
    }

    //Lanzar llamada a favorito
    public void lanzarLlamada()
    {
        Intent i = new Intent(getContext(), LlamarContacto.class);
        startActivity(i);
    }

    //Lanzar Sms a 112
    public void lanzarSms()
    {
        Intent i = new Intent(getContext(), SendSmS.class);
        startActivity(i);
    }
    //Lanzar permiso microfono
    public void lanzarmicrofono()
    {
        Intent i = new Intent(getContext(), PermisoMicrofono.class);
        startActivity(i);
    }
    //Lanzar intercomunicador
    public void lanzarIntercomunicador()
    {
        Intent i = new Intent(getContext(), Intercomunicador.class);
        startActivity(i);
    }

}

