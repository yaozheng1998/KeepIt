package y84107258.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void login (View view) {
        //如果设备上没有可接收的隐式Intent的应用，start会崩溃；
        Intent intent=new Intent(MainActivity.this, TodoListActivity.class);
        startActivity(intent);
    }
}
