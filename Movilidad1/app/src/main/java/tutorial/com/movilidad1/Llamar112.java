package tutorial.com.movilidad1;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

public class Llamar112 extends Activity {

    LlamadaDirecta112 estado = new LlamadaDirecta112();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (estado.getEstado() == 1) {
            /*Toast.makeText(getApplicationContext(),"oleeeeeeeeeeeeeee",Toast.LENGTH_SHORT).show();
            Intent in=new Intent(Intent.ACTION_CALL,Uri.parse("610369910"));
            try{
                startActivity(in);
            }

            catch (android.content.ActivityNotFoundException ex){
                Toast.makeText(getApplicationContext(),"yourActivity is not founded",Toast.LENGTH_SHORT).show();
            }*/

            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:0377778888"));

            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            startActivity(callIntent);


        }
        else {
           // Toast.makeText(this, "OLEEEE222222", Toast.LENGTH_SHORT).show();


            Intent llamar112 = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:112"));
            llamar112.setData(Uri.parse("tel:112"));
            startActivity(llamar112);
        }

    }
}
