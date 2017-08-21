package tutorial.com.movilidad1;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 * Fragmento de diálogo que permite la variante de enviar un email o SMS en la misma acción.
 *
 * EL código de los cuadros de diálogo está disponible en:
 * https://sekthdroid.wordpress.com/2013/02/06/fragments-dialogfragment-en-android/
 */
public class EnviarSmsoEmail extends DialogFragment
{
    public static int estadoEmail;
    public static int estadoSms;

    static EnviarSmsoEmail newInstance(String title)
    {
        EnviarSmsoEmail fragment = new EnviarSmsoEmail();
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
                .setTitle(title)
                .setPositiveButton(R.string.email, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                         EnviarSmsoEmail.this.estadoEmail = 1;
                        ((Configuracion) getActivity()).doPositiveClick();
                    }
                })
                .setNegativeButton(R.string.sms, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        EnviarSmsoEmail.this.estadoSms = 1;
                        ((Configuracion) getActivity()).doNegativeClick();
                    }
                })
                .setNeutralButton(R.string.DefaultEmail, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        EnviarSmsoEmail.this.estadoEmail = 0;
                        EnviarSmsoEmail.this.estadoSms = 0;
                        ((Configuracion) getActivity()).doNegativeClick();
                    }
                })
                .create();
    }
}