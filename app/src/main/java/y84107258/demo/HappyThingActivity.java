package y84107258.demo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

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

        /**
         * 从金山词霸获取每日一句
         */
        String content=pref.getString("sen",null);
        if (content!=null){
            motto.setText(content);
        }else{
            loadSen();
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
