package fingertiptech.medontime;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class NotificationAndAlarm extends Application {

    public static final String NOTIFICATION_CHANNEL_ID = "NotificationChannel";
    public static final String ALARM_CHANNEL_ID = "AlarmChannel";

    @Override
    public void onCreate() {
        super.onCreate();
        
        createNotificationChannel();
        createAlarmChannel();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(
                    NOTIFICATION_CHANNEL_ID,
                    "Medication Reminder Notification",
                    NotificationManager.IMPORTANCE_HIGH
            );
            notificationChannel.setDescription("It is time to take your medication");

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    private void createAlarmChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel alarmChannel = new NotificationChannel(
                    ALARM_CHANNEL_ID,
                    "Medication Reminder Alarm",
                    NotificationManager.IMPORTANCE_HIGH
            );
            alarmChannel.setDescription("This is time to take your medication");

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(alarmChannel);
        }
    }
}
