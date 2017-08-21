package tutorial.com.movilidad1;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 * Clase encargada de abrir el cuadro de diálogo que permite llamar al número de emergencias de forma manual o de forma
 * automática. Si se selecciona la opción 'automática', la variable estadoLLamada cambia su valor.
 */
public class LlamadaDirecta112 extends DialogFragment
{
   public static int estadoLlamada;

    static LlamadaDirecta112 newInstance(String title)
    {
            LlamadaDirecta112 fragment = new LlamadaDirecta112();
            Bundle args = new Bundle();
            args.putString("title", title);
            fragment.setArguments(args);
            return fragment;
    }
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState)
        {

            String title = getArguments().getString("title");
            return new AlertDialog.Builder(getActivity())
                    .setIcon(R.drawable.botonemergencias)
                    .setTitle(title)
                    .setPositiveButton(R.string.Automatica, new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            LlamadaDirecta112.this.estadoLlamada = 1;
                            ((Configuracion) getActivity()).doPositiveClick();
                        }
                    })
                    .setNegativeButton(R.string.Manual, new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            LlamadaDirecta112.this.estadoLlamada = 0;
                            ((Configuracion) getActivity()).doNegativeClick();
                        }
                    })
                    .create();
        }
    }