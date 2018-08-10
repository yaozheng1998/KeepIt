package y84107258.demo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.litepal.crud.DataSupport;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import y84107258.demo.model.MyActivity;
import y84107258.demo.util.ActGsonUtil;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements ItemTouchHelperCallback.SwipedListener {
    private TextView whetherFinish;
    private List<MyActivity> data;
    private LayoutInflater inflater;
    private ItemClickListener itemClickListener;
    private Drawable pic;
    private LinearLayout details;
    Date d=new Date();

    RecyclerViewAdapter(Context context, List<MyActivity> data){
        pic=context.getResources().getDrawable(R.drawable.test);
        this.inflater=LayoutInflater.from(context);
        this.data=data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.schedule_row, parent, false);
        whetherFinish= view.findViewById(R.id.finishView);
        whetherFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (whetherFinish.getText().equals("已打卡")){
                    Toast.makeText(view.getContext(),"已经打过卡啦~",Toast.LENGTH_SHORT).show();
                }else {
                    MyActivity activity=new MyActivity();
                    activity.setChecked(true);
//                    activity.updateAll("activityName=?",((TextView)view.findViewById(R.id.schedule_name)).getText().toString());
                    whetherFinish.setText("已打卡");
                    Toast.makeText(view.getContext(), "打卡成功！", Toast.LENGTH_SHORT).show();
                }
            }
        });

        details=view.findViewById(R.id.schedule_detail);
        details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent((Activity)view.getContext(), ScheduleInfoActivity.class);
                //在Intent中传输数据
                String actName=((TextView)view.findViewById(R.id.schedule_name)).getText().toString();
                intent.putExtra("s_title",actName);
                intent.putExtra("s_date",getDate()+"     "+getDay());
                intent.putExtra("s_time",((TextView)view.findViewById(R.id.schedule_time)).getText().toString());
                intent.putExtra("s_des",getDesFromSP(actName));
                view.getContext().startActivity(intent);
            }
        });
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MyActivity oneActivity=data.get(position);
        holder.myTextView.setText(oneActivity.getActivityName());
        holder.timeView.setText(oneActivity.getStartTime()+" - "+oneActivity.getEndTime());
        holder.myImageView.setImageDrawable(pic);
        if (oneActivity.isChecked()){
            holder.isEndView.setText("已打卡");
        }else{
            holder.isEndView.setText("打卡");
        }
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
        String name=data.get(position).getActivityName();
        DataSupport.deleteAll(MyActivity.class,"activityName=?",name);
        //可以实现自动页面刷新，需要使用同一个List
        //换成DataSupport后左滑删除失效
        notifyDataSetChanged();
    }

    /**
     * ViewHolder实现常用方法，比如：点击事件、长按事件等
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView myImageView;
        TextView myTextView;
        TextView timeView;
        TextView isEndView;

        ViewHolder(View itemView){
            super(itemView);
            myImageView=itemView.findViewById(R.id.schedule_photo);
            myTextView=itemView.findViewById(R.id.schedule_name);
            timeView=itemView.findViewById(R.id.schedule_time);
            isEndView=itemView.findViewById(R.id.finishView);
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

    private String getDay(){
        String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    private String getDate(){
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        return dateformat.format(d);
    }

    private String getDesFromSP(String actName){
        String result="";
        ArrayList<MyActivity> myActivities= (ArrayList<MyActivity>) DataSupport.findAll(MyActivity.class);
        for (int i=0;i<myActivities.size();i++){
            if (myActivities.get(i).getActivityName().equals(actName)){
                result=myActivities.get(i).getDescription();
            }
        }
        return result;
    }
}
