package y84107258.demo;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
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

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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
    private static final int MY_PERMISSIONS_REQUEST_CAMERA=0x008;
    private Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_schedule);
        this.image = (ImageView) this.findViewById(R.id.activity_image);

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
                String startTime=modifyTime(startPicker.getCurrentHour(), startPicker.getCurrentMinute());
                String endTime=modifyTime(endPicker.getCurrentHour(), endPicker.getCurrentMinute());

                if (activityName.equals("")){
                    Toast.makeText(NewScheduleActivity.this,"请填写活动名称",Toast.LENGTH_SHORT).show();
                }else if(startPicker.getCurrentHour()>endPicker.getCurrentHour() || (startPicker.getCurrentHour()==endPicker.getCurrentHour() && startPicker.getCurrentMinute()>endPicker.getCurrentMinute())){
                    Toast.makeText(NewScheduleActivity.this,"结束时间应晚于开始时间",Toast.LENGTH_SHORT).show();
                } else{
                    ArrayList<MyActivity> myActivities = getActFromGson(preferences.getString("activities", ""));
                    myActivities.add(new MyActivity(activityName, startTime, endTime, "","",""));
                    Gson gson = new Gson();
                    String jsonStr = gson.toJson(myActivities);
                    editor.putString("activities", jsonStr);
                    editor.apply();

                    Intent intent = new Intent(NewScheduleActivity.this, TodoListActivity.class);
                    startActivity(intent);
                }
            }
        });

        final TimePicker startPicker= (TimePicker)findViewById(R.id.start_time);
        startPicker.setIs24HourView(true);
        setNumberPickerTextSize(startPicker);
        resizeTimePicker(startPicker);
        startPicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hour, int minute) {

            }
        });

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

    public void viewGallery(View view) {
        intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        //这里调用有问题
        if (Build.VERSION.SDK_INT>22){
//            if (getApplicationContext().checkSelfPermission(Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},MY_PERMISSIONS_REQUEST_CAMERA);
//            }
        }
        startActivityForResult(intent,PHOTO_REQUEST_GALLERY);
    }

    @Override
    public void onRequestPermissionResult(int requestCode, String permissions[], int[] grantResults){
        switch(requestCode){
            case MY_PERMISSIONS_REQUEST_CAMERA:
                if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    startActivityForResult(intent,PHOTO_REQUEST_GALLERY);
                }else{
                    Log.v("NewScheduleActivity","需要权限。。");
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        }
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
                ContentResolver resolver=getContentResolver();
                Uri uri=data.getData();
                String scheme=uri.getScheme();
                String imagePath="";
                Log.v("NewScheduleActivity",uri.getPath());
//                crop(uri);
                try {
                    Bitmap bm=MediaStore.Images.Media.getBitmap(resolver,uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor =managedQuery(uri, filePathColumn, null, null, null);
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                imagePath = cursor.getString(columnIndex);
                Glide.with(this).load(imagePath).into(image);
//                Bitmap bitmap=getBitmapByPath(imagePath);
//                this.image.setImageBitmap(bitmap);
//                cursor.close();
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

    private String modifyTime(int hour, int minute){
        String afterHour=hour+"",afterMinute=minute+"";
        if (hour<10){
            afterHour="0"+hour;
        }
        if (minute<10){
            afterMinute="0"+minute;
        }
        return afterHour+":"+afterMinute;
    }

    /**
     * 获取bitmap
     * @param filePath
     * @return
     */
    public static Bitmap getBitmapByPath(String filePath) {
        return getBitmapByPath(filePath, null);
    }
    public static Bitmap getBitmapByPath(String filePath, BitmapFactory.Options opts) {
        FileInputStream fis = null;
        Bitmap bitmap =null;
        try {
            File file = new File(filePath);
            fis = new FileInputStream(file);
            bitmap = BitmapFactory.decodeStream(fis,null,opts);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        } finally{
            try {
                fis.close();
            } catch (Exception e) {}
        }
        return bitmap;
    }
}
