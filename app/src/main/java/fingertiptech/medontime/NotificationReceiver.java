package fingertiptech.medontime;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String message = intent.getStringExtra("toastMessage");
        String medicationId = intent.getStringExtra("medicationId");
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
♂