package gu.wen.bulletmullet;
import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.SynchronousQueue;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.app.Service;

/**
 * Created by angelwen on 6/18/17.
 */


public class Bullet {
    private Date date;

    private static final String DATABASE_NAME = "bulletJournalDB3";
    private static final String DATABASE_TABLE = "bullets_table"; //or whatever you need to access bs
    private static final int DATABASE_VERSION = 1;
    private static final String TAG = "DBAdapter";
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public Bullet(Context ctx, Date newDate){
        date = newDate;
        DBHelper = new DatabaseHelper(ctx);
        db = DBHelper.getWritableDatabase();
    }
    /** add whatever bullet to the table in the database **/
    public void addBullet(String bulletType, String text){//Task, Event, Note

        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        String dayStr = df.format(date);
        System.out.println("hello?");
        // we are using ContentValues to avoid sql format errors
        ContentValues contentValues = new ContentValues();
        contentValues.put("type",bulletType);
        contentValues.put("content",text);
        contentValues.put("day",dayStr);
        db.insert(DATABASE_TABLE, null, contentValues);
        System.out.println("HEYYY");
    }
    public LinkedList<String> getBulletList(String type){
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        String dayStr = df.format(date);
        //System.out.println(dayStr);
        //String[] columns = {DATABASE_TABLE,dayStr};

        Cursor c = db.rawQuery("SELECT * FROM bullets_table WHERE type = '"+type+"' AND day = '"+dayStr+"'", null);

        LinkedList<String> l = new LinkedList<String>();
        if (c.moveToFirst()){
            do {
                String str = c.getString(c.getColumnIndex("content"));
                System.out.println(str);
                l.add(str);
            }while(c.moveToNext());
        }
        return l;
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
            System.out.println("OOPDASDASKJDAK WHY IS THERE NO DAY");
            String buildSQL = "CREATE TABLE " + DATABASE_TABLE+ "( id INTEGER PRIMARY KEY, day TEXT, type TEXT, content  TEXT, pos INTEGER)";
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
