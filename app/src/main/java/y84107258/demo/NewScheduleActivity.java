package y84107258.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class NewScheduleActivity extends Activity implements ScheduleDialogFragment.Callback {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_schedule);
    }
    @Override
    public void onClick(String scheduleName, String startTime, String endTime) {

    }

    public void showScheduleDialogFragment(View view){
        ScheduleDialogFragment scheduleDialogFragment=new ScheduleDialogFragment();
        scheduleDialogFragment.show(getFragmentManager());
    }

}
