package gu.wen.bulletmullet;
import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by angelwen on 6/18/17.
 */


public class Bullet {
    private Date date;

    private static final String DATABASE_NAME = "bulletJournalDB";
    private static final String DATABASE_TABLE = "bullets"; //or whatever you need to access bs
    private static final int DATABASE_VERSION = 1;
    private static final String TAG = "DBAdapter";
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public Bullet(Date newDate){
        date = newDate;
        db = DBHelper.getWritableDatabase();
    }
    /** add whatever bullet to the table in the database **/
    public void addBullet(String bulletType, String text,Date date){//Task, Event, Note

        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        String dayStr = df.format(date);

        // we are using ContentValues to avoid sql format errors
        ContentValues contentValues = new ContentValues();
        contentValues.put("type",bulletType);
        contentValues.put("content","TEXT");
        contentValues.put("date",dayStr);
        db.insert(DATABASE_TABLE, null, contentValues);
    }
    public String getEventsList(Date day){
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        String dayStr = df.format(day);
        String[] columns = {DATABASE_TABLE,"event",dayStr};

        Cursor c = db.rawQuery("SELECT * FROM ? WHERE type = ? AND date = ?",columns);
        String str = c.getString(c.getColumnIndex("content"));
        System.out.println(str);
        return "";
    }


    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            // create database helper
            // primary key, bullet_type text, date, actual_text text
            String buildSQL = "CREATE TABLE " + DATABASE_TABLE+ "( id INTEGER PRIMARY KEY, type TEXT " +
                    "date DATE,  content  TEXT, order INTEGER)";
            db.execSQL(buildSQL);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion,
                              int newVersion)
        {
            Log.w(TAG, "Upgrading database from version " + oldVersion
                    + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE);
            onCreate(db);
        }
    }

}
