package y84107258.demo.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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

    private String CREATE_USER="CREATE TABLE [user](\n" +
            "  [username] VARCHAR NOT NULL, \n" +
            "  [description] VARCHAR);\n";
    private String CREATE_ACTIVITY="CREATE TABLE [activity](\n" +
            "  [activityName] VARCHAR, \n" +
            "  [activityDate] DATE, \n" +
            "  [startTime], \n" +
            "  [endTime], \n" +
            "  [isDone] TINYINT, \n" +
            "  [userId] VARCHAR);\n";

    public SQLiteUtil(Context context, int version){
        super(context,DBNAME,null,version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_USER);
        sqLiteDatabase.execSQL(CREATE_ACTIVITY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.v("SQLiteUtil","Database is upgraded");
    }
}
