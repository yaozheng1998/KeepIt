package y84107258.demo;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

public class TodoListActivity extends Activity {
    private TodayFragment todayFragment;
    private HistoryFragment historyFragment;
    private PersonalFragment personalFragment;
    private FragmentTransaction transaction;
    private Fragment nowFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todolist);

//        setCustomActionBar();

        BottomNavigationBar bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.ic_today,"今日活动"))
                .addItem(new BottomNavigationItem(R.drawable.ic_history, "打卡统计"))
                .addItem(new BottomNavigationItem(R.drawable.ic_person, "个人信息"))
                .setFirstSelectedPosition(0)
                .initialise();
        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                switch (position){
                    case 0:
                        switchFragment(todayFragment);
                        break;
                    case 1:
                        switchFragment(historyFragment);
                        break;
                    case 2:
                        switchFragment(personalFragment);
                        break;
                        default:
                            break;
                }
            }
            @Override
            public void onTabUnselected(int position) {
            }
            @Override
            public void onTabReselected(int position) {
            }
        });
        initFragment();
    }

    private void initFragment(){
        todayFragment=new TodayFragment();
        historyFragment=new HistoryFragment();
        personalFragment=new PersonalFragment();

        transaction=getFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container, todayFragment).commit();
        nowFragment=todayFragment;
    }

    private void switchFragment(Fragment fragment){
        if(nowFragment!=fragment){
            if(!fragment.isAdded()){
                getFragmentManager().beginTransaction().hide(nowFragment).add(R.id.fragment_container,fragment).commit();
            }else{
                getFragmentManager().beginTransaction().hide(nowFragment).show(fragment).commit();
            }
            nowFragment=fragment;
        }
    }

    //设置自定义ActionBar
    private void setCustomActionBar(){
        ActionBar actionBar=getActionBar();
        actionBar.setCustomView(R.layout.actionbar_layout);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
    }
}
