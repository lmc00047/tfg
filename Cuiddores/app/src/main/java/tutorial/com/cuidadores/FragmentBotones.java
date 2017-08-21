package tutorial.com.cuidadores;

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
 * Esta clase se encarga de realizar la funcionalidad de la aplicación, es decir, cuando el botón
 * del intercomunicador es pulsado llama a la clase Intercomunidor.java realizando así la acción
 * requerida.
 */
public class FragmentBotones extends Fragment
{
    private View mLayout;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragmentbotones, container, false);
        Button botonRtp = (Button) view.findViewById(R.id.botonAzul);

    //-----------------------------------------BOTON AZUL-----------------------------------------//
    //OnClick del boton intercomunicador
        botonRtp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0) {
            LanzarIntercomunicador();
        }
        });

    //-----------------------------------------FIN BOTON AZUL-------------------------------------//
        return view;

    }
        public void LanzarIntercomunicador()
        {
            Intent i = new Intent(getContext(), Intercomunicador.class);
            startActivity(i);
        }
}

