package edu.scu.cheryl.arttherapy;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

/**
 * Created by XiangM on 16-3-3.
 */
public class UnlockIntercepter  extends BroadcastReceiver{
    final int notificationId = 1111;
    @Override
    public void onReceive(Context context, Intent intent) {
        int requestCode=2222;
        Intent resultIntent = new Intent(context, DrawActivity.class);
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(context, requestCode, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.ic_notification)
                        .setContentTitle("Draw something!")
                        .setContentText("Hello, there! Click me!")
                        .setContentIntent(resultPendingIntent)
                        .setAutoCancel(true);
        // .addAction(R.drawable.ic_fish, "Fish", resultPendingIntent);

        mBuilder.setStyle(createBigContent());

        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        mNotificationManager.notify(notificationId, mBuilder.build());
    }
    NotificationCompat.InboxStyle createBigContent() {
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        String[] events = {"Do you wanna build a snowman", "Come on lets go and play", "I never see you anymore", "We used to be best buddy"};
        // Sets a title for the Inbox in expanded layout
        inboxStyle.setBigContentTitle("Do you wanna draw something");
        // Moves events into the expanded layout
        for (int i=0; i < events.length; i++) {
            inboxStyle.addLine(events[i]);
        }
        inboxStyle.setSummaryText("Come back please");
        return inboxStyle;
    }
}
