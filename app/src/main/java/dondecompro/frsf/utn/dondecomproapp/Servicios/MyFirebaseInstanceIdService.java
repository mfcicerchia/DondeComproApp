package dondecompro.frsf.utn.dondecomproapp.Servicios;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;


/**
 * Created by Martin on 06/03/2017.
 */

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {


    private static final String TAG = "MyFirebaseIDService";
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        Log.d(TAG, "Refreshed token: SERVICIO CREADO!!!!");
//    }

    @Override
    public void onTokenRefresh() {
        //Se obtiene el token actualizado
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        Log.d(TAG, "Token actualizado: " + refreshedToken);


        //saveTokenToPrefs(refreshedToken);
    }

//    private void saveTokenToPrefs(String _token){
//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putString("registration_id", _token);
//        editor.apply();
//    }
//    private String getTokenFromPrefs(){
//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
//        return preferences.getString("registration_id", null);
//    }


//    public static final String TAG = "PROMOCIONES";
//    @Override
//    public void onTokenRefresh() {
//        super.onTokenRefresh();
//
//        String token = FirebaseInstanceId.getInstance().getToken();
//        Log.d(TAG, "Token: "+token);
//    }
//}
}