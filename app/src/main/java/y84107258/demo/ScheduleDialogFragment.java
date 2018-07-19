package y84107258.demo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

public class ScheduleDialogFragment extends DialogFragment {
    public interface Callback{
        void onClick(String scheduleName, String startTime, String endTime);
    }
    private Callback callback;
    public void show(FragmentManager fragmentManager){
        show(fragmentManager,"ScheduleDialogFragment");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstance){
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        final View view=inflater.inflate(R.layout.new_schedule,null);
        builder.setView(view).setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        return builder.create();
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if(context instanceof Callback){
            callback=(Callback) context;
        }else{
            throw new RuntimeException(context.toString());
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        callback=null;
    }
}
