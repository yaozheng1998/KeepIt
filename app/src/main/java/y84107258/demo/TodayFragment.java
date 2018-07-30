package y84107258.demo;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

import y84107258.demo.model.MyActivity;

public class TodayFragment extends Fragment {
    private SharedPreferences preferencess;
    private SharedPreferences.Editor editor;

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
//        ArrayList<MyActivity> myActivities=new ArrayList<>();
//        myActivities.add(new MyActivity("阅读","10:00","12:00",""));
//        myActivities.add(new MyActivity("玩耍","13:00","18:00",""));
//        Gson gson=new Gson();
//        String jsonStr=gson.toJson(myActivities);
//        editor.putString("activities",jsonStr);
//        editor.apply();

//        手动清空今日活动界面
//        editor.clear();
//        editor.commit();

        RecyclerView recyclerView=view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        RecyclerViewAdapter adapter=new RecyclerViewAdapter(getActivity(),getActFromGson(preferencess.getString("activities",""))                                                                                                                                        );

        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new MyItemDecoration(view.getContext(),LinearLayoutManager.VERTICAL,10, getResources().getColor(R.color.divide_gray_color)));

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
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
}
