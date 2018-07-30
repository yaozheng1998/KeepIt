package y84107258.demo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import y84107258.demo.model.MyActivity;

public class NewScheduleActivity extends Activity implements ScheduleDialogFragment.Callback {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private static final int PHOTO_REQUEST_CAMERA=1;
    private static final int PHOTO_REQUEST_GALLERY=2;
    private static final int PHOTO_REQUEST_CUT=3;
    private ImageView image;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_schedule);
        this.image = (ImageView) this.findViewById(R.id.image);

        preferences=getSharedPreferences("activities",0);
        editor=preferences.edit();

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(NewScheduleActivity.this, TodoListActivity.class);
                intent.putExtra("id",0);
                startActivity(intent);
            }
        });

        //确认按钮监听
        findViewById(R.id.confirm_new).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String activityName=((EditText)findViewById(R.id.activity_name)).getText().toString();
                TimePicker startPicker=(TimePicker)findViewById(R.id.start_time);
                TimePicker endPicker=(TimePicker)findViewById(R.id.end_time);
                String startTime=startPicker.getCurrentHour()+"-"+startPicker.getCurrentMinute();
                String endTime=endPicker.getCurrentHour()+"-"+endPicker.getCurrentMinute();

                if (activityName.equals("")){
                    Toast.makeText(NewScheduleActivity.this,"请填写活动名称",Toast.LENGTH_SHORT).show();
                }else {
                    ArrayList<MyActivity> myActivities = getActFromGson(preferences.getString("activities", ""));
                    myActivities.add(new MyActivity(activityName, startTime, endTime, ""));
                    Gson gson = new Gson();
                    String jsonStr = gson.toJson(myActivities);
                    editor.putString("activities", jsonStr);
                    editor.apply();

                    Intent intent = new Intent(NewScheduleActivity.this, TodoListActivity.class);
                    startActivity(intent);
                }
            }
        });

        TimePicker startPicker= (TimePicker)findViewById(R.id.start_time);
        startPicker.setIs24HourView(true);
        setNumberPickerTextSize(startPicker);
        resizeTimePicker(startPicker);

        TimePicker endPicker=(TimePicker) findViewById(R.id.end_time);
        endPicker.setIs24HourView(true);
        setNumberPickerTextSize(endPicker);
        resizeTimePicker(endPicker);
    }
    @Override
    public void onClick(String scheduleName, String startTime, String endTime) {

    }

    public void showScheduleDialogFragment(View view){
        ScheduleDialogFragment scheduleDialogFragment=new ScheduleDialogFragment();
        scheduleDialogFragment.show(getFragmentManager());
    }

    /**
     * 从相册选取图片
     * @param view
     */
    public void viewGallery(View view) {
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        //这里调用有问题
        startActivityForResult(intent,PHOTO_REQUEST_GALLERY);
    }

    private void crop(Uri uri){
        Intent intent=new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop","true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);

        intent.putExtra("outputFormat", "JPEG");
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("return-data", true);

        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==PHOTO_REQUEST_GALLERY){
            if (data==null){
            }else {
                Uri uri=data.getData();
                Log.v("NewScheduleActivity",uri.getPath());
//                crop(uri);
                new DownloadImageTask((ImageView) findViewById(R.id.activity_image)).doInBackground("http://java.sogeti.nl/JavaBlog/wp-content/uploads/2009/04/android_icon_256.png");
            }
        }else if(requestCode==PHOTO_REQUEST_CUT){
            if(data!=null){
                Bitmap bitmap=data.getParcelableExtra("data");
                this.image.setImageBitmap(bitmap);
            }
        }
    }

    private List<NumberPicker> findNumberPicker(ViewGroup viewGroup){
        List<NumberPicker> npList=new ArrayList<NumberPicker>();
        View child=null;
        if(viewGroup!=null){
            for(int i=0;i<viewGroup.getChildCount();i++){
                child=viewGroup.getChildAt(i);
                if (child instanceof NumberPicker){
                    npList.add((NumberPicker)child);
                }
                else if (child instanceof LinearLayout){
                    List<NumberPicker> result=findNumberPicker((ViewGroup) child);
                    if (result.size()>0){
                        return result;
                    }
                }
            }
        }
        return npList;
    }

    private EditText findEditText(NumberPicker np){
        if(np!=null){
            for(int i=0;i<np.getChildCount();i++){
                View child=np.getChildAt(i);
                if(child instanceof EditText){
                    return (EditText)child;
                }
            }
        }
        return null;
    }

    private void setNumberPickerTextSize(ViewGroup viewGroup){
        List<NumberPicker> npList=findNumberPicker(viewGroup);
        if(npList!=null){
            for(NumberPicker np:npList){
                EditText et=findEditText(np);
                et.setFocusable(false);
                et.setGravity(Gravity.CENTER);
                et.setTextSize(10);
            }
        }
    }

    private void resizeTimePicker(TimePicker tp){
        List<NumberPicker> npList=findNumberPicker(tp);
        for(NumberPicker np:npList){
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(100, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(10,0,10,0);
            np.setLayoutParams(params);
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap>{
        ImageView bmImage;
        public DownloadImageTask(ImageView bmImage){
            this.bmImage=bmImage;
        }
        @Override
        protected Bitmap doInBackground(String... urls) {
            String urldisplay=urls[0];
            Bitmap mIcon=null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon;
        }
        protected void onPostExecute(Bitmap result){
            bmImage.setImageBitmap(result);
        }
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
