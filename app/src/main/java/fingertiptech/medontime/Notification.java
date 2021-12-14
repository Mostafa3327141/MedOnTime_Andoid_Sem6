package fingertiptech.medontime;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

/**
 * This Notification class extends Application and it runs at runtime.
 * It is registered in the manifest file
 * This is used to make the channel for sending notifications
 */
public class Notification extends Application {

    public static final String NOTIFICATION_CHANNEL_ID = "NotificationChannel";

    @Override
    public void onCreate() {
        super.onCreate();
        
        createNotificationChannel();
    }

    /**
     * This method create notification channel
     * It checks the android version to see if it supports notification
     * It uses NotificationChannel and NotificationManager to build the notification channel
     */
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
}
