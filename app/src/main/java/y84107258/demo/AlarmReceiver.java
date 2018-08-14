package y84107258.demo;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"reminder~~",Toast.LENGTH_SHORT).show();
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification=new Notification.Builder(context).setContentTitle("提示").setContentText("打卡").setSmallIcon(R.drawable.happy).build();
        manager.notify(1, notification);

        Intent i = new Intent(context, AlarmService.class);
        context.startService(i);

    }
}
