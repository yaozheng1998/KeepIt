package y84107258.demo;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends Activity {
    private EditText usernameEdit;
    private EditText passwordEdit;
    private EditText confirmPassEdit;
    private EditText phoneNumEdit;
    private EditText verifyCodeEdit;
    private Button sendButton;
    private Button registerButton;
    private TextView linklogin;
    private String code="";

    CountDownTimer timer=new CountDownTimer(60000,1000) {
        @Override
        public void onTick(long l) {
            sendButton.setText(l/1000+"秒");
            sendButton.setEnabled(false);

        }

        @Override
        public void onFinish() {
            sendButton.setEnabled(true);
            sendButton.setText("发送验证码");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        usernameEdit=findViewById(R.id.input_name);
        passwordEdit=findViewById(R.id.input_password);
        confirmPassEdit=findViewById(R.id.input_password_twice);
        phoneNumEdit=findViewById(R.id.input_phoneNum);
        verifyCodeEdit=findViewById(R.id.input_code);
        sendButton=findViewById(R.id.verifyButton);
        registerButton=findViewById(R.id.btn_signup);
        linklogin=findViewById(R.id.link_login);

        linklogin.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        linklogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = phoneNumEdit.getText().toString().trim();
                if (isValidPhoneNum(phone)) {
                    view.setEnabled(false);
                    timer.start();
                    for (int i = 0; i < 4; i++) {
                        int k = (int) (Math.random() * 10);
                        code += k;
                    }
                    PendingIntent pi = PendingIntent.getActivity(RegisterActivity.this, 0, new Intent(), 0);
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phone, null, "【KeepIt】验证码" + code, pi, null);
                    Toast.makeText(RegisterActivity.this, "短信已发送", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(RegisterActivity.this, "电话格式不正确！", Toast.LENGTH_LONG).show();
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (usernameEdit.getText().toString().trim().length()==0){
                    Toast.makeText(RegisterActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
                }else if (passwordEdit.getText().toString().trim().length()==0 || confirmPassEdit.getText().toString().trim().length()==0){
                    Toast.makeText(RegisterActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
                }else if (!(passwordEdit.getText().toString().trim()).equals(confirmPassEdit.getText().toString().trim())){
                    Toast.makeText(RegisterActivity.this,"两次密码不同，请重新填写",Toast.LENGTH_SHORT).show();
                    passwordEdit.setText("");
                    confirmPassEdit.setText("");
                }else if (verifyCodeEdit.getText().toString().trim().length()==0){
                    Toast.makeText(RegisterActivity.this,"验证码不能为空",Toast.LENGTH_SHORT).show();
                }else{

                }
            }
        });
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        timer.cancel();
    }

    private boolean isValidPhoneNum(String phoneNum){
        Matcher m = null;
        if(phoneNum.trim().length()>0){
            Pattern p = Pattern.compile("^((13[0-9])|(15[0-3])|(15[7-9])|(18[0,5-9]))\\d{8}$");
            m= p.matcher(phoneNum);
        }
        else{
            return false;
        }
        return m.matches();
    }
}
