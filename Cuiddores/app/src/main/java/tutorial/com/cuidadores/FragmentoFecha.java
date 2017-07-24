package tutorial.com.cuidadores;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


public class FragmentoFecha extends Fragment
{
    static Timer timer=null;
    int i = 0;
    private Button botonFecha =  null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragmentofecha, container, false);
        botonFecha = (Button) view.findViewById(R.id.button6);
        final View frag = view.findViewById(R.id.fragment4);

        //Obtener fecha actual
        Calendar calendarNow = Calendar.getInstance();
        final int monthDay = calendarNow.get(Calendar.DAY_OF_MONTH);
        final int month = calendarNow.get(Calendar.MONTH);
        final int year = calendarNow.get(Calendar.YEAR);

        //OnClick del boton fecha
        botonFecha.setOnLongClickListener(new View.OnLongClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public boolean onLongClick(View arg0) {
                frag.setVisibility(View.VISIBLE);
                return true;
            }
        });

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        if(timer!=null){
            timer.cancel();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        //Obtener hora actual y refrescar cada segundo la hora mostrada y la fecha
        final Runnable updateTask = new Runnable() {
            public void run() {
                Date fecha= new Date();;
                SimpleDateFormat formato= new SimpleDateFormat("dd/MM/yyyy");

                SimpleDateFormat formato2= new SimpleDateFormat("HH:mm:ss");
                String fechas= formato.format(fecha)+"\n"+formato2.format(fecha);
                botonFecha.setText(fechas);
            }

        };

        //Timer para actualizar la hora cada segundo
        timer = new Timer("DigitalClock");
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(updateTask);
            }
        }, 1, 1000);


    }
}
