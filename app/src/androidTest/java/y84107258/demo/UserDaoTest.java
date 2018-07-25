package y84107258.demo;

import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import org.junit.Test;

import y84107258.demo.dao.UserDao;
import y84107258.demo.util.SQLiteUtil;

public class UserDaoTest extends AndroidTestCase{
    @Test
    public void createDB(){
        SQLiteUtil sqLiteUtil=new SQLiteUtil(getContext(),1);
        SQLiteDatabase db=sqLiteUtil.getWritableDatabase();
        db.close();
    }
    @Test
    public void testAdd(){
        UserDao dao=new UserDao(getContext());
        dao.add();
    }
    @Test
    public void testGetAllUsers(){
        UserDao dao=new UserDao(getContext());
        dao.getAllUsers();
    }


}
