package y84107258.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class HappyThingActivity extends Activity {
    private TextView dateEdit;
    private TextView dayEdit;
    Date date=new Date();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.happy_thing);

        dateEdit=findViewById(R.id.todayDate);
        dayEdit=findViewById(R.id.todayDay);
        dateEdit.setText(getDate());
        dayEdit.setText(getDay());

        findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HappyThingActivity.this, TodoListActivity.class);
                intent.putExtra("id",0);
                startActivity(intent);
            }
        });
    }

    private String getDay(){
        String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    private String getDate(){
        SimpleDateFormat dateformat = new SimpleDateFormat("MMMM dd,yyyy", Locale.ENGLISH);
        return dateformat.format(date);
    }
}
