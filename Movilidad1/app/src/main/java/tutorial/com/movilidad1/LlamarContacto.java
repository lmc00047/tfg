package tutorial.com.movilidad1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class LlamarContacto extends Activity{
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            //Llamar directamente a un contacto favorito
            Intent intent = new Intent(Intent.ACTION_DIAL);
            startActivity(intent);

        }
}
