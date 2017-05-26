package tutorial.com.movilidad1;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

    public class Fragment1 extends DialogFragment {
        static Fragment1 newInstance(String title) {
            Fragment1 fragment = new Fragment1();
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
                    .setIcon(R.drawable.abc)
                    .setTitle(title)
                    .setPositiveButton(R.string.Automatica, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub
                            ((Configuracion) getActivity()).doPositiveClick();
                        }
                    })
                    .setNegativeButton(R.string.Manual, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub
                            ((Configuracion) getActivity()).doNegativeClick();
                        }
                    })
                    .create();
        }
    }