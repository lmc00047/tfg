package tutorial.com.movilidad1;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class EnviarSmsoEmail extends DialogFragment {
    static EnviarSmsoEmail newInstance(String title) {
        EnviarSmsoEmail fragment = new EnviarSmsoEmail();
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
                .setTitle(title)
                .setPositiveButton(R.string.email, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        ((Configuracion) getActivity()).doPositiveClick();
                    }
                })
                .setNegativeButton(R.string.sms, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        ((Configuracion) getActivity()).doNegativeClick();
                    }
                })
                .setNeutralButton(R.string.ambos, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        ((Configuracion) getActivity()).doNegativeClick();
                    }
                })

                .create();
    }
}