package tutorial.com.cuidadores;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceActivity;

/**
 * Created by LauraMoreno on 31/07/2017.
 */

public class Preferencias extends PreferenceActivity {

    public SharedPreferences sharedpreferences = getSharedPreferences("Perfil", Context.MODE_PRIVATE);

}
