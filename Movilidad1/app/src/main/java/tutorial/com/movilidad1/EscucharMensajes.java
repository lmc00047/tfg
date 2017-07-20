package tutorial.com.movilidad1;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class EscucharMensajes extends DialogFragment {
    public static int estadoEscuchar;

    static EscucharMensajes newInstance(String title) {
        EscucharMensajes fragment = new EscucharMensajes();
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        String title = getArguments().getString("title");
        return new AlertDialog.Builder(getActivity())
                .setIcon(R.drawable.botonmensaje)
                .setTitle(title)
                .setPositiveButton(R.string.no, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        ((Configuracion) getActivity()).doPositiveClick();
                    }
                })
                .setNegativeButton(R.string.si, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        EscucharMensajes.this.estadoEscuchar = 1;
                        ((Configuracion) getActivity()).doNegativeClick();
                    }
                })

                .create();
    }
}