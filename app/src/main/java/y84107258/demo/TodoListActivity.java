package y84107258.demo;

import android.app.Activity;
import android.os.Bundle;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

public class TodoListActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BottomNavigationBar bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
//        bottomNavigationBar.addItem(new BottomNavigationItem(R.mipmap.ic_location_on_white_24dp,"home")).initialise();
        setContentView(R.layout.activity_todolist);
    }
}
