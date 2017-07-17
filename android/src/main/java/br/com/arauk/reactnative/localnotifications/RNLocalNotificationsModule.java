package br.com.arauk.reactnative.localnotifications;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@SuppressWarnings("WeakerAccess")
public class RNLocalNotificationsModule extends ReactContextBaseJavaModule {

    private AlarmManager alarmManager;
    private ReactApplicationContext reactContext;

    public RNLocalNotificationsModule(ReactApplicationContext reactContext) {
        super(reactContext);

        this.reactContext = reactContext;

        alarmManager = (AlarmManager) reactContext.getSystemService(Context.ALARM_SERVICE);
    }

    @Override
    public String getName() {
        return "RNLocalNotifications";
    }

    @ReactMethod
    public void createNotification(Integer id, String title, String text, String datetime) {
        this.createAlarm(id, title, text, datetime, false);
    }

    @ReactMethod
    public void deleteNotification(Integer id) {
        this.deleteAlarm(id);
    }

    @ReactMethod
    public void updateNotification(Integer id, String title, String text, String datetime) {
        this.createAlarm(id, title, text, datetime, true);
    }

    private void createAlarm(Integer id, String title, String text, String datetime, boolean update) {
        if (update) {
            this.deleteAlarm(id);
        }

        Date dateToMillis = null;
        try {
            SimpleDateFormat desiredFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm");
            dateToMillis = desiredFormat.parse(datetime);
        } catch (ParseException paEx) {
            paEx.printStackTrace();
        }

        if (dateToMillis != null) {
            Intent intent = new Intent(reactContext, AlarmReceiver.class);
            intent.setAction("br.com.arauk.reactnative.localnotifications.showAlarm");
            intent.putExtra("id", id);
            intent.putExtra("title", title);
            intent.putExtra("text", text);
            intent.putExtra("datetime", datetime);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(reactContext, id, intent, 0);

            Long triggerAtMillis = dateToMillis.getTime();
            Calendar date = Calendar.getInstance();
            if (triggerAtMillis > date.getTimeInMillis()) {
                alarmManager.set(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);
            }
        }
    }

    private void deleteAlarm(Integer id) {
        Intent intent = new Intent(reactContext, AlarmReceiver.class);
        intent.setAction("br.com.arauk.reactnative.localnotifications.showAlarm");

        PendingIntent pendingIntent = PendingIntent.getBroadcast(reactContext, id, intent, PendingIntent.FLAG_NO_CREATE);
        if (pendingIntent != null) {
            pendingIntent.cancel();
        }

        alarmManager.cancel(pendingIntent);
    }

}
