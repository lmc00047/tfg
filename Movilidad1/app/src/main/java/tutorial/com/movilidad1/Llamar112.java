package tutorial.com.movilidad1;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class Llamar112 extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Llamar directamente al 112
        Intent llamar112 = new Intent(Intent.ACTION_DIAL);
        llamar112.setData(Uri.parse("tel:112"));
        startActivity(llamar112);
    }
}
