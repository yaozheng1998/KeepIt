package y84107258.demo.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import y84107258.demo.util.SQLiteUtil;

public class UserDao {
    private static final int DB_VERSION=1;
    private Context context;
    private SQLiteUtil sqLiteUtil;
    private SQLiteDatabase db;

    public UserDao(Context context){
        this.context=context;
        sqLiteUtil=new SQLiteUtil(context,DB_VERSION);
    }

    public void add(){
        db=sqLiteUtil.getWritableDatabase();
        db.beginTransaction();
        db.execSQL("insert into "+sqLiteUtil.TABLE_USER+" ("+sqLiteUtil.TABLE_USER_COLUMN_USERNAME+") values ('uuu'"+")");
    }

    public List<String> getAllUsers(){
        List<String> users=new ArrayList<>();
        db=sqLiteUtil.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from "+sqLiteUtil.TABLE_USER, null);
        while (cursor.moveToNext()){
            String name=cursor.getString(cursor.getColumnIndex(sqLiteUtil.TABLE_USER_COLUMN_USERNAME));
            System.out.println(name);
            users.add(name);
        }
        cursor.close();
        db.close();
        return users;
    }


}
