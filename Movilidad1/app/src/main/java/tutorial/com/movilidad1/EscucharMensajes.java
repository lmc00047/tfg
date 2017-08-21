package tutorial.com.movilidad1;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 * Es la clase del fragmento de diálogo que permite poder escuchar los SMS en voz alta.
 * Si en el cuadro de dialogo se selecciona la opción 'SI' quiere decir que los SMS serán leídos en voz alta,
 * cambiando el valor de la variable estadoEscuchar.
 */
public class EscucharMensajes extends DialogFragment
{
    public static int estadoEscuchar;

    static EscucharMensajes newInstance(String title)
    {
        EscucharMensajes fragment = new EscucharMensajes();
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
                .setIcon(R.drawable.botonmensaje)
                .setTitle(title)
                .setPositiveButton(R.string.no, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        ((Configuracion) getActivity()).doPositiveClick();
                    }
                })
                .setNegativeButton(R.string.si, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        EscucharMensajes.this.estadoEscuchar = 1;
                        ((Configuracion) getActivity()).doNegativeClick();
                    }
                })
                .create();
    }
}