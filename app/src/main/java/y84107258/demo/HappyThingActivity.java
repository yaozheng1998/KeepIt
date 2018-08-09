package y84107258.demo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import y84107258.demo.util.HttpUtil;

public class HappyThingActivity extends Activity {
    private TextView dateEdit;
    private TextView dayEdit;
    private ImageView todayImg;
    private TextView motto;
    Date date=new Date();

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.happy_thing);

        pref= PreferenceManager.getDefaultSharedPreferences(this);
        editor=pref.edit();

        dateEdit=findViewById(R.id.todayDate);
        dayEdit=findViewById(R.id.todayDay);
        dateEdit.setText(getDate());
        dayEdit.setText(getDay());
        todayImg=findViewById(R.id.todayImg);
        motto=findViewById(R.id.motto);

        findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HappyThingActivity.this, TodoListActivity.class);
                intent.putExtra("id",0);
                startActivity(intent);
            }
        });

        /**
         * 从Bing获取每日一图
         */
        String img=pref.getString("img",null);
        if (img!=null){
            Glide.with(this).load(img).into(todayImg);
        }else{
            loadImg();
        }
        todayImg.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(HappyThingActivity.this);
                builder.setItems(new String[]{getResources().getString(R.string.save_picture)}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        todayImg.setDrawingCacheEnabled(true);
                        Bitmap imageBitmap=todayImg.getDrawingCache();
                        if (imageBitmap!=null){
                            new SaveImageTask().execute(imageBitmap);
                        }
                    }
                });
                builder.show();
                return true;
            }
        });

        /**
         * 从金山词霸获取每日一句
         */
        String content=pref.getString("sen",null);
        if (content!=null){
            motto.setText(content);
        }else{
            loadSen();
        }

        //每次都清空不太ok
//        pref.edit().clear().apply();
        //为了简便，每次打开app都重新加载图片；其实应该每日0点设置定时任务；
        editor.remove("img");
        editor.remove("sen");
        editor.commit();
    }

    private class SaveImageTask extends AsyncTask<Bitmap, Void, String> {
        @Override
        protected String doInBackground(Bitmap... params) {
            String result = getResources().getString(R.string.save_picture_fail);
            try {
                String sdcard = Environment.getExternalStorageDirectory().toString();

                File file = new File(sdcard + "/Download");
                if (!file.exists()) {
                    file.mkdirs();
                }

                File imageFile = new File(file.getAbsolutePath(),new Date().getTime()+".jpg");
                FileOutputStream outStream = null;
                outStream = new FileOutputStream(imageFile);
                Bitmap image = params[0];
                image.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
                outStream.flush();
                outStream.close();
                result = getResources().getString(R.string.save_picture_success,  file.getAbsolutePath());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getApplicationContext(),result, Toast.LENGTH_SHORT).show();
            todayImg.setDrawingCacheEnabled(false);
        }
    }


    private String getDay(){
        String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    private String getDate(){
        SimpleDateFormat dateformat = new SimpleDateFormat("MMMM dd,yyyy", Locale.ENGLISH);
        return dateformat.format(date);
    }

    private void loadImg(){
        String requestImg="http://guolin.tech/api/bing_pic";
        HttpUtil.sendOkHttpRequest(requestImg, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bingImg=response.body().string();
                editor=PreferenceManager.getDefaultSharedPreferences(HappyThingActivity.this).edit();
                editor.putString("img",bingImg);
                editor.apply();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(HappyThingActivity.this).load(bingImg).into(todayImg);
                    }
                });
            }
        });
    }

    private void loadSen(){
        String requestSen="http://open.iciba.com/dsapi/";
        HttpUtil.sendOkHttpRequest(requestSen, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JSONObject jsonObject=new JSONObject(response.body().string());
                    final String cibaSen = jsonObject.getString("content");
                    editor=PreferenceManager.getDefaultSharedPreferences(HappyThingActivity.this).edit();
                    editor.putString("sen",cibaSen);
                    editor.apply();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            motto.setText(cibaSen);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
