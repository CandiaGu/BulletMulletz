package gu.wen.bulletmullet;
import java.util.*;
import android.database.sqlite.*;

/**
 * Created by angelwen on 6/18/17.
 */

public class Bullet {
    private Date date;

    private String dbName = "bulletJournalDB";
    private String tableName = "bullets"; //or whatever you need to access bs
    private static final String DATABASE_CREATE =
            "create table productdet ("
                    +"_id integer primary key autoincrement, "
                    + "proname varchar ,"
                    + "procost varchar);";
    private SQLiteDatabase db;

    public Bullet(Date newDate){
        date = newDate;
    }

    /** add whatever bullet to the table in the database **/
    public void addBullet(String bulletType, String text){//Task, Event, Note

    }




    public Date getDate(){
        return date;
    }

}
