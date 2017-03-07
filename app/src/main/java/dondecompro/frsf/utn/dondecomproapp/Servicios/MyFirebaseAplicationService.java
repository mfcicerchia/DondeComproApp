package dondecompro.frsf.utn.dondecomproapp.Servicios;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import dondecompro.frsf.utn.dondecomproapp.MainActivity;
import dondecompro.frsf.utn.dondecomproapp.NuevoPedidoActivity;

/**
 * Created by Martin on 06/03/2017.
 */

public class MyFirebaseAplicationService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {


        if ((remoteMessage.getNotification() != null) && (remoteMessage.getData() != null)) {

            String titulo = remoteMessage.getNotification().getTitle();
            String texto = remoteMessage.getNotification().getBody();
            String producto = remoteMessage.getData().get("producto");
            String precio = remoteMessage.getData().get("precio");

            Log.d(TAG, "NOTIFICACION RECIBIDA");
            Log.d(TAG, "Título: " + titulo);
            Log.d(TAG, "Texto: " + texto);
            Log.d(TAG, "PROMOCIONES (datos recibidos)");
            Log.d(TAG, "Producto: " + remoteMessage.getData().get("producto"));
            Log.d(TAG, "Precio: " + remoteMessage.getData().get("precio"));

            //Opcional: mostramos la notificación en la barra de estado
            showNotification(titulo, texto, producto, precio);
            //showNotification(titulo, texto);
        }

    }

    private void showNotification(String title, String text) {

         /*===============================================================================================*/
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String strRingtonePreference = prefs.getString("ringtone_1", "DEFAULT_RINGTONE_URI");
        Uri defaultSoundUri = Uri.parse(strRingtonePreference);
         /*==============================================================================================*/

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(android.R.drawable.stat_sys_warning)
                        .setContentTitle(title)
                        .setContentText(text)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setTicker("DondeComproApp");

        Intent intent = new Intent(MyFirebaseAplicationService.this, NuevoPedidoActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("title", title);
        intent.putExtra("description", text);


        PendingIntent contIntent = PendingIntent.getActivity(
                MyFirebaseAplicationService.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        notificationBuilder.setContentIntent(contIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }


    private void showNotification(String title, String text, String producto, String precio) {

        /*===============================================================================================*/
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String strRingtonePreference = prefs.getString("ringtone_1", "DEFAULT_RINGTONE_URI");
        Uri defaultSoundUri = Uri.parse(strRingtonePreference);
         /*==============================================================================================*/

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(android.R.drawable.stat_sys_warning)
                        .setContentTitle(title)
                        .setContentText(text)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setTicker("DondeComproApp");

        Intent intent = new Intent(MyFirebaseAplicationService.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("title", title);
        intent.putExtra("description", text);
        intent.putExtra("producto", producto);
        intent.putExtra("precio", precio);


        PendingIntent contIntent = PendingIntent.getActivity(
                MyFirebaseAplicationService.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        notificationBuilder.setContentIntent(contIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }

}
