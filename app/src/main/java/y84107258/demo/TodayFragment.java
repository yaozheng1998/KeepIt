package y84107258.demo;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Fragment;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import y84107258.demo.model.MyActivity;

public class TodayFragment extends Fragment {
    private SharedPreferences preferencess;
    private SharedPreferences.Editor editor;
    private ItemTouchHelper.Callback callback;
    private ItemTouchHelper itemTouchHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        preferencess=getActivity().getSharedPreferences("activities",0);
        editor=preferencess.edit();

        View view=inflater.inflate(R.layout.fragment_today, container, false);
        view.findViewById(R.id.newSchedule).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ScheduleDialogFragment scheduleDialogFragment=new ScheduleDialogFragment();
//                scheduleDialogFragment.show(getFragmentManager());
                Intent intent=new Intent(getActivity(), NewScheduleActivity.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.happyThing).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), HappyThingActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        RecyclerView recyclerView=view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        RecyclerViewAdapter adapter=new RecyclerViewAdapter(getActivity(), DataSupport.findAll(MyActivity.class));

        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new MyItemDecoration(view.getContext(),LinearLayoutManager.VERTICAL,10, getResources().getColor(R.color.divide_gray_color)));

        //设置左滑删除操作
        callback=new ItemTouchHelperCallback(adapter);
        itemTouchHelper=new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        //设置每日零点的定时任务，清空今日活动列表;
        startRemind();
    }

    public static ArrayList<MyActivity> getActFromGson(String raw){
        ArrayList<MyActivity> result=new ArrayList<>();
        Gson gson=new Gson();
        ArrayList<MyActivity> acts=gson.fromJson(raw,new TypeToken<List<MyActivity>>(){}.getType());
        if (acts!=null) {
            for (MyActivity a : acts) {
                result.add(a);
            }
        }
        return result;
    }

    private void startRemind(){
        Calendar mCalendar=Calendar.getInstance();
        mCalendar.setTimeInMillis(System.currentTimeMillis());
        long systemTime=System.currentTimeMillis();
        mCalendar.setTimeInMillis(System.currentTimeMillis());
        mCalendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        mCalendar.set(Calendar.HOUR_OF_DAY,16);
        mCalendar.set(Calendar.MINUTE,59);
        mCalendar.set(Calendar.SECOND,0);
        mCalendar.set(Calendar.MILLISECOND,0);
        long selectTime=mCalendar.getTimeInMillis();
        if (systemTime>selectTime){
            mCalendar.add(Calendar.DAY_OF_MONTH,1);
        }
        Intent intent=new Intent(getActivity(),AlarmReceiver.class);
        PendingIntent pi=PendingIntent.getBroadcast(getActivity(),0, intent,0);
        AlarmManager am=(AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        //先设置每五分钟提醒一次
        am.setRepeating(AlarmManager.RTC_WAKEUP,mCalendar.getTimeInMillis(),1000*60*2,pi);
    }
    private void stopRemind(){
        Intent intent=new Intent(getActivity(),AlarmReceiver.class);
        PendingIntent pi=PendingIntent.getBroadcast(getActivity(),0,intent,0);
        AlarmManager am=(AlarmManager)getContext().getSystemService(Context.ALARM_SERVICE);
        am.cancel(pi);
    }
}
