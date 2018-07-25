package y84107258.demo.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteUtil extends SQLiteOpenHelper {
    public static final String DBNAME="keep.db";
    public static final String TABLE_USER="User";
    public static final String TABLE_USER_COLUMN_USERNAME="username";
    public static final String TABLE_ACTIVITY="MyActivity";
    public static final String TABLE_ACTIVITY_COLUMN_ACTIVITYNAME="activityName";
    public static final String TABLE_ACTIVITY_COLUMN_ACTIVITYDATE="activityDate";
    public static final String TABLE_ACTIVITY_COLUMN_STARTTIME="startTime";
    public static final String TABLE_ACTIVITY_COLUMN_ENDTIME="endTime";
    public static final String TABLE_ACTIVITY_COLUMN_ISDONE="isDone";
    public static final String TABLE_ACTIVITY_COLUMN_USERNAME="userId";


    public SQLiteUtil(Context context, int version){
        super(context,DBNAME,null,version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql="create table if not exists "+TABLE_USER+" ("+TABLE_USER_COLUMN_USERNAME+" VARCHAR NOT NULL);";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql="DROP TABLE IF EXISTS "+TABLE_USER;
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }
}
