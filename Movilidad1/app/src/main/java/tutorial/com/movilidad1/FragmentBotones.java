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

/**
 * Esta clase se encarga de realizar las funcionalidades de los botones, es decir, cuando un botón es pulsado llama
 * a la clase correspondiente que realiza la acción requerida.
 */
public class FragmentBotones extends Fragment
{
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragmentbotones, container, false);
        Button botonllamada = (Button) view.findViewById(R.id.button);
        Button botonRtp = (Button) view.findViewById(R.id.botonAzul);
        Button botonSms = (Button) view.findViewById(R.id.botonVerde);
        Button botonEmergencias = (Button) view.findViewById(R.id.botonAmarillo);

    //------------------------------BOTÓN ROJO O BOTÓN LLAMADA------------------------------------//
        //OnClick del boton llamada al cuidador
        botonllamada.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Llamar a una persona
                lanzarLlamada();
            }
        });

    //------------------------BOTÓN AMARILLO O BOTÓN EMERGENCIAS----------------------------------//
    //OnClick del boton llamada a Emergencias (112)
        botonEmergencias.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
            lanzarLlamada112();
            }
        });

    //OnLongClick del boton enviar SMS a Emergencias
      botonEmergencias.setOnLongClickListener(new View.OnLongClickListener()
      {
        @Override
        public boolean onLongClick(View arg0)
        {
          lanzarSms();
            return true;
        }
        });

    //----------------------------BOTÓN VERDE O BOTÓN MENSAJES------------------------------------//
    //OnClick del boton Mensaje (De voz a texto)
        botonSms.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg1)
            {
                lanzarVozTexto();
            }
        });

    //onLongClick del boton Mensje (De texto a voz)
        botonSms.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View arg0)
            {
                lanzarSiri();
                return true;
            }
        });

    //--------------------------BOTÓN AZUL O BOTÓN INTERCOMUNICADOR-------------------------------//
    //OnClick del boton RTP
        botonRtp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                lanzarIntercomunicador();
            }
        });

    //-----------------------------------FIN BOTONES----------------------------------------------//
        return view;

    }

    //Lanzar Actividad Reconocimiento de voz
       public void lanzarReconocimiento()
    {
        Intent i = new Intent(getContext(), ReconocimientoVoz.class);
        startActivity(i);
    }

    //Lanzar Activity Texto a Voz
    public void lanzarSiri()
    {
        Intent i = new Intent(getContext(), TextoVoz.class);
        startActivity(i);
    }

    //Lanzar Activity Voz a Texto
    public void lanzarVozTexto()
    {
        Intent i = new Intent(getContext(), VozTexto.class);
        startActivity(i);
    }

    //Lanzar llamada a emergencias(112)
    public void lanzarLlamada112()
    {
        Intent i = new Intent(getContext(), Llamar112new.class);
        startActivity(i);
    }

    //Lanzar llamada al cuidador
    public void lanzarLlamada()
    {
        Intent i = new Intent(getContext(), LlamarContacto.class);
        startActivity(i);
    }

    //Lanzar Sms a emergencias (112)
    public void lanzarSms()
    {
        Intent i = new Intent(getContext(), SendSmS.class);
        startActivity(i);
    }

    //Lanzar Función Intercomunicador
    public void lanzarIntercomunicador()
    {
        Intent i = new Intent(getContext(), Intercomunicador.class);
        startActivity(i);
    }
}



