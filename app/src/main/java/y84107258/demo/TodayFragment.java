package y84107258.demo;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;

public class TodayFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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
        ArrayList<String> names=new ArrayList<>();
        names.add("Reading");
        names.add("Listening");
        names.add("Speaking");
        names.add("Writing");
        names.add("Singing");

        RecyclerView recyclerView=view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        RecyclerViewAdapter adapter=new RecyclerViewAdapter(getActivity(),names);
//        adapter.setClickListener();
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new MyItemDecoration(view.getContext(),LinearLayoutManager.VERTICAL,10, getResources().getColor(R.color.divide_gray_color)));

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
    }

    public void addNewSchedule(){

    }

}
