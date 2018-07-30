package y84107258.demo;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import y84107258.demo.model.MyActivity;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private TextView whetherFinish;
    private List<MyActivity> data;
    private LayoutInflater inflater;
    private ItemClickListener itemClickListener;
    private Drawable pic;

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
