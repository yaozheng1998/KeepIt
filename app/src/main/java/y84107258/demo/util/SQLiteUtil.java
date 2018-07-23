package y84107258.demo.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteUtil extends SQLiteOpenHelper {
    private static final String DBNAME="keep.db";
    private static final String TABLE_USER="user";
    private static final String TABLE_USER_COLUMN_USERNAME="username";
    private static final String TABLE_ACTIVITY="activity";
    private static final String TABLE_ACTIVITY_COLUMN_ACTIVITYNAME="activityName";
    private static final String TABLE_ACTIVITY_COLUMN_ACTIVITYDATE="activityDate";
    private static final String TABLE_ACTIVITY_COLUMN_STARTTIME="startTime";
    private static final String TABLE_ACTIVITY_COLUMN_ENDTIME="endTime";
    private static final String TABLE_ACTIVITY_COLUMN_ISDONE="isDone";
    private static final String TABLE_ACTIVITY_COLUMN_USERNAME="userId";


    public SQLiteUtil(Context context, int version){
        super(context,DBNAME,null,version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
