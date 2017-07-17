package br.com.arauk.reactnative.localnotifications;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("br.com.arauk.reactnative.localnotifications.showAlarm")) {
            Integer id = intent.getExtras().getInt("id", 0);
            String title = intent.getExtras().getString("title", "");
            String text = intent.getExtras().getString("text", "");

            String packageName = context.getPackageName();
            Resources packageResources = context.getResources();

            Intent resultIntent = null;
            try {
                resultIntent = new Intent(context, Class.forName(packageName + ".MainActivity"));
            } catch (ClassNotFoundException ignore) {
            }

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context);
            notificationBuilder.setSmallIcon(packageResources.getIdentifier("notification_small", "drawable", packageName));
            if (resultIntent != null) {
                notificationBuilder.setContentIntent(PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT));
            }
            notificationBuilder.setContentText(text);
            notificationBuilder.setContentTitle(title);
            notificationBuilder.setDefaults(NotificationCompat.DEFAULT_ALL);

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(packageName + ".notification", id, notificationBuilder.build());
        }
    }
}
