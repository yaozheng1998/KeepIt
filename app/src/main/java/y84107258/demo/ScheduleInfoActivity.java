package y84107258.demo;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;

import y84107258.demo.model.MyActivity;
import y84107258.demo.util.ActGsonUtil;

public class ScheduleInfoActivity extends Activity{
    private TextView title;
    private TextView date;
    private TextView time;
    private TextView alarm;
    private TextView description;
    private ImageButton back;
    private ImageButton delete;

    private TextInputLayout title_layout;
    private EditText title_edit;
    private TextInputLayout des_layout;
    private EditText des_edit;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private FloatingActionButton update;
    private Button confirm_update;

    private Spinner spinner;
    private String s_title;
    private String s_des;
    private String s_alarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_info);
        initComponent();

        Intent intent=getIntent();
        s_title=intent.getStringExtra("s_title");
        String s_date=intent.getStringExtra("s_date");
        String s_time=intent.getStringExtra("s_time");
        s_des=intent.getStringExtra("s_des");
        s_alarm=intent.getStringExtra("s_alarm");
        title.setText(s_title);
        date.setText(s_date);
        time.setText(s_time);
        description.setText(s_des);

        spinner.setSelection(Integer.parseInt(s_alarm) / 5);
        spinner.setPrompt("请选择提醒时间");
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                int span=0;
                String[] types=getResources().getStringArray(R.array.reminder_labels);
                switch (position){
                    case 0:break;
                    case 1:span=5;break;
                    case 2:span=10;break;
                    case 3:span=15;break;
                    default:break;
                }
                MyActivity activity=new MyActivity();
                activity.setAlarmTime(span+"");
                activity.updateAll("activityName=?",s_title);


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                /**
                 * 会报错view no found， 因为直接调用fragment没有加载到container；既然是首页，直接跳转到Todo即可
                 */
//                ScheduleInfoActivity scheduleInfoActivity=(ScheduleInfoActivity)view.getContext();
//                scheduleInfoActivity.getActivityFragment();
                Intent intent = new Intent(ScheduleInfoActivity.this, TodoListActivity.class);
                startActivity(intent);
            }
        });

        /**
         * db中删除，页面刷新跳转
         */
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ScheduleInfoActivity scheduleInfoActivity = (ScheduleInfoActivity) view.getContext();
                ConfirmDialog confirmDialog=new ConfirmDialog();
                confirmDialog.show("提示", "删除后不可恢复，确认删除?", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        DataSupport.deleteAll(MyActivity.class,"activityName=?",s_title);
                        Intent intent = new Intent(ScheduleInfoActivity.this, TodoListActivity.class);
                        startActivity(intent);
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(ScheduleInfoActivity.this,"取消",Toast.LENGTH_SHORT).show();
                    }
                },getFragmentManager());
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (confirm_update.getVisibility()==View.VISIBLE){
                    setInvisible();
                }else {
                    setVisible();
                }
            }
        });

        confirm_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newTitle=title_edit.getText().toString();
                String newDes=des_edit.getText().toString();
                MyActivity activity=new MyActivity();
                activity.setActivityName(newTitle);
                activity.setDescription(newDes);
                activity.updateAll("activityName=?",s_title);
                setInvisible();
                Intent intent = new Intent(ScheduleInfoActivity.this, TodoListActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }

    private void initComponent(){
        title=findViewById(R.id.detail_alarm_title);
        date=findViewById(R.id.detail_alarm_date);
        time=findViewById(R.id.detail_alarm_start_end_time);
        spinner=findViewById(R.id.detail_alarm_remind);
        description=findViewById(R.id.detail_alarm_description);
        back=findViewById(R.id.left_alarm_back);
        delete=findViewById(R.id.tv_delete);
        update=findViewById(R.id.update_fab);
        description=findViewById(R.id.detail_alarm_description);

        title_layout=findViewById(R.id.title_layout);
        title_edit=(EditText) findViewById(R.id.title_edit);
        des_layout=findViewById(R.id.des_layout);
        des_edit=(EditText)findViewById(R.id.des_edit);
        confirm_update=findViewById(R.id.confirm_update);

        setInvisible();
    }

    private void setVisible(){
        title.setVisibility(View.INVISIBLE);
        description.setVisibility(View.INVISIBLE);
        confirm_update.setVisibility(View.VISIBLE);
        title_layout.setVisibility(View.VISIBLE);
        title_edit.setText(s_title);
        des_layout.setVisibility(View.VISIBLE);
        des_edit.setText(s_des);
    }

    private void setInvisible(){
        title.setVisibility(View.VISIBLE);
        description.setVisibility(View.VISIBLE);
        confirm_update.setVisibility(View.INVISIBLE);
        title_layout.setVisibility(View.INVISIBLE);
        des_layout.setVisibility(View.INVISIBLE);
    }

    private void getActivityFragment(){
        fragmentManager=getFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();
        TodayFragment todayFragment=new TodayFragment();
        fragmentTransaction.replace(R.id.fragment_container, todayFragment).commit();
    }
}
