package y84107258.demo;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import y84107258.demo.model.MyActivity;
import y84107258.demo.util.ActGsonUtil;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements ItemTouchHelperCallback.SwipedListener {
    private TextView whetherFinish;
    private List<MyActivity> data;
    private LayoutInflater inflater;
    private ItemClickListener itemClickListener;
    private Drawable pic;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    RecyclerViewAdapter(Context context, List<MyActivity> data){
        pic=context.getResources().getDrawable(R.drawable.test);
        this.inflater=LayoutInflater.from(context);
        this.data=data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.schedule_row, parent, false);
        whetherFinish= view.findViewById(R.id.finish);
        whetherFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"打卡成功！",Toast.LENGTH_SHORT).show();
            }
        });

        preferences=view.getContext().getSharedPreferences("activities",0);
        editor=preferences.edit();

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MyActivity oneActivity=data.get(position);
        holder.myTextView.setText(oneActivity.getActivityName());
        holder.timeView.setText(oneActivity.getStartTime()+" - "+oneActivity.getEndTime());
        holder.myImageView.setImageDrawable(pic);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onItemRight(int position) {

    }

    @Override
    public void onItemLeft(int position) {
        data.remove(position);
        Gson gson = new Gson();
        String jsonStr = gson.toJson(data);
        editor.putString("activities", jsonStr);
        editor.apply();
    }

    /**
     * ViewHolder实现常用方法，比如：点击事件、长按事件等
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView myImageView;
        TextView myTextView;
        TextView timeView;

        ViewHolder(View itemView){
            super(itemView);
            myImageView=itemView.findViewById(R.id.schedule_photo);
            myTextView=itemView.findViewById(R.id.schedule_name);
            timeView=itemView.findViewById(R.id.schedule_time);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            //点击item事件
            if(itemClickListener!=null){
                itemClickListener.onItemClick(view, getAdapterPosition());
            }
        }


    }

    public void setClickListener(ItemClickListener itemClickListener){
        this.itemClickListener=itemClickListener;
    }

    public interface ItemClickListener{
        void onItemClick(View view, int position);
    }


}
