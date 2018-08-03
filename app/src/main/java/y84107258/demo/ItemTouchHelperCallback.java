package y84107258.demo;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.widget.Toast;

public class ItemTouchHelperCallback extends ItemTouchHelper.Callback{
    private SwipedListener swipedListener;
    public ItemTouchHelperCallback(SwipedListener swipedListener){
        this.swipedListener=swipedListener;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        final int dragFlags=ItemTouchHelper.UP|ItemTouchHelper.DOWN;
        final int swipeFlags=ItemTouchHelper.START|ItemTouchHelper.END;
        return makeMovementFlags(dragFlags,swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        if (direction==ItemTouchHelper.END){

        }else if (direction==ItemTouchHelper.START){
            //左滑;具体删除逻辑需要在接口回调中实现
            Log.v("OITEM","lefttttttttttttttttt");
            swipedListener.onItemLeft(viewHolder.getAdapterPosition());
        }
    }

    public interface SwipedListener{
        void onItemRight(int position);
        void onItemLeft(int position);
    }
}
