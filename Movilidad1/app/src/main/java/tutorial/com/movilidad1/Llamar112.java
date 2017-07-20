package tutorial.com.movilidad1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

public class Llamar112 extends AppCompatActivity {

    LlamadaDirecta112 estado = new LlamadaDirecta112();

    private EditText tlfcuidador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (LlamadaDirecta112.estadoLlamada==1) {

            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:3135165"));
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {


                          return;
            }
            startActivity(callIntent);



            }



        else {
            Intent llamar112 = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:112"));
            llamar112.setData(Uri.parse("tel:112"));
            startActivity(llamar112);
        }

    }
}
