package y84107258.demo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.LitePal;

public class MainActivity extends Activity {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private EditText usernameEdit;
    private EditText passwordEdit;
    private CheckBox remPasswordBox;
    private TextView register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * 创建出KeepIt数据库
         */
        LitePal.getDatabase();

        setContentView(R.layout.activity_main);
        pref= PreferenceManager.getDefaultSharedPreferences(this);
        usernameEdit=(EditText)findViewById(R.id.input_username);
        passwordEdit=(EditText)findViewById(R.id.input_password);
        remPasswordBox=(CheckBox)findViewById(R.id.remPassword);
        boolean isRem=pref.getBoolean("remPass",false);
        if (isRem){
            String username=pref.getString("myusername","");
            String password=pref.getString("mypassword","");
            usernameEdit.setText(username);
            passwordEdit.setText(password);
            remPasswordBox.setChecked(true);
        }

        register=(TextView)findViewById(R.id.register);
        register.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        Intent intent=new Intent(this, AlarmService.class);
        startService(intent);
    }

    public void login (View view) {
        //如果设备上没有可接收的隐式Intent的应用，start会崩溃；
        String usernameInput=usernameEdit.getText().toString();
        String passwordInput=passwordEdit.getText().toString();
        if (usernameInput.equals("yz") && passwordInput.equals("123")){
            editor=pref.edit();
            if(remPasswordBox.isChecked()){
                editor.putBoolean("remPass",true);
                editor.putString("myusername",usernameInput);
                editor.putString("mypassword",passwordInput);
            }else{
                editor.clear();
            }
            editor.apply();
            Intent intent=new Intent(MainActivity.this, TodoListActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(MainActivity.this,"Invalid username or password",Toast.LENGTH_SHORT).show();
        }

    }

    //判断登录条件，快速注册
}
