package y84107258.demo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.v("Reminder","...");
        Toast.makeText(context, "提醒", Toast.LENGTH_SHORT).show();
    }
}
