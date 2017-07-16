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


public class BulletWorker {
    private Date date;
    private String dayStr;
    private static final String DATABASE_NAME = "bulletJournalDB5";
    private static final String DATABASE_TABLE = "bullets_table"; //or whatever you need to access bs
    private static final int DATABASE_VERSION = 1;
    private static final String TAG = "DBAdapter";
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public BulletWorker(Context ctx, Date newDate){
        date = newDate;
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        dayStr = df.format(date);
        DBHelper = new DatabaseHelper(ctx);
        db = DBHelper.getWritableDatabase();
    }

    /** add whatever bullet to the table in the database **/
    public void addBullet(String type, String text){//Task, Event, Note
        Cursor c = db.rawQuery("SELECT * FROM "+DATABASE_TABLE+" WHERE type = '"+type+"' AND day = '"+dayStr+"'", null);
        int position = c.getCount();

        ContentValues contentValues = new ContentValues();
        contentValues.put("type",type);
        contentValues.put("content",text);
        contentValues.put("day",dayStr);
        contentValues.put("pos",position);
        db.insert(DATABASE_TABLE, null, contentValues);
    }

    public void reorderBullet(String type, int prev_pos, int new_pos){
        if (new_pos>prev_pos) {
            db.execSQL("UPDATE " + DATABASE_TABLE + " SET pos = -5 " +
                    "WHERE type = '" + type + "' AND day = '" + dayStr + "' " +
                    "AND pos = " + prev_pos);
            db.execSQL("UPDATE " + DATABASE_TABLE + " SET pos = pos - 1 " +
                    "WHERE type = '" + type + "' AND day = '" + dayStr + "' " +
                    "AND pos > " + prev_pos + " AND pos <= " + new_pos + "");
            // actually move the first thing to the new pos
            db.execSQL("UPDATE " + DATABASE_TABLE + " SET pos = " +new_pos+
                    " WHERE type = '" + type + "' AND day = '" + dayStr + "' " +
                    "AND pos = -5");
        }
        else if (new_pos<prev_pos) {
            db.execSQL("UPDATE " + DATABASE_TABLE + " SET pos = -5 " +
                    "WHERE type = '" + type + "' AND day = '" + dayStr + "' " +
                    "AND pos = " + prev_pos);
            db.execSQL("UPDATE " + DATABASE_TABLE + " SET pos = pos + 1 " +
                    "WHERE type = '" + type + "' AND day = '" + dayStr + "' " +
                    "AND pos <= " + new_pos + " AND pos >= " + prev_pos + "");
            db.execSQL("UPDATE " + DATABASE_TABLE + " SET pos = " +new_pos+
                    " WHERE type = '" + type + "' AND day = '" + dayStr + "' " +
                    "AND pos = -5");
        }
    }

    public void deleteBullet(String type, String text){
        db.delete(DATABASE_TABLE, "type" + "=? content "+"=?", new String[]{type,text});
    }

    public LinkedList<BulletItem> getBulletList(String type){
        Cursor c = db.rawQuery("SELECT * FROM "+DATABASE_TABLE+" WHERE type = '"+type+"' AND day = '"+dayStr+"'", null);

        OrderedList<BulletItem> l = new OrderedList<BulletItem>();
        if (c.moveToFirst()){
            do {
                String str = c.getString(c.getColumnIndex("content"));
                int position = c.getInt(c.getColumnIndex("pos"));
                BulletItem bi = new BulletItem(str,position);
                l.orderedAdd(bi);
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
