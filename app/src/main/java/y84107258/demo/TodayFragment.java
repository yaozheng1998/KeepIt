package y84107258.demo;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;

public class TodayFragment extends Fragment {
    private SwipeMenuRecyclerView menuRecyclerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_today, container, false);
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
        recyclerView.addItemDecoration(new MyItemDecoration(getContext(),LinearLayoutManager.VERTICAL,10, getResources().getColor(R.color.divide_gray_color)));

//        menuRecyclerView=view.findViewById(R.id.recycler_view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
    }

}
