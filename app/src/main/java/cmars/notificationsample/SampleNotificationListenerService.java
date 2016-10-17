package cmars.notificationsample;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.support.annotation.RequiresApi;
import android.support.v4.content.LocalBroadcastManager;

import static cmars.notificationsample.Const.ACTION_NOTIFICATION_POSTED;
import static cmars.notificationsample.Const.EXTRA_TITLE;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class SampleNotificationListenerService extends NotificationListenerService {
    public SampleNotificationListenerService() {
    }

    private boolean connected;

    @Override
    public void onListenerConnected() {
        super.onListenerConnected();
        connected=true;
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);

        if(connected) {
            Notification notification = sbn.getNotification();
            String packageName = sbn.getPackageName();
            LocalBroadcastManager.getInstance(this)
                    .sendBroadcast(new Intent(ACTION_NOTIFICATION_POSTED).putExtra(EXTRA_TITLE, packageName));
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }
}
