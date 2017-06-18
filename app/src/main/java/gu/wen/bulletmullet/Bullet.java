package gu.wen.bulletmullet;
import java.util.*;
/**
 * Created by angelwen on 6/18/17.
 */

public class Bullet {
    private String dbName = "bulletDb"; //or whatever you need to access bs
    private String bulletType; //Task, Event, Note
    private String text;
    private Date date;

    public Bullet(String newBulletType, Date newDate, String newText){
        //do stuff
        bulletType = newBulletType;
        date = newDate;
        text = newText;
    }

    private String getDbName(){
        return "hi";
    }
    private void addBullet(){

    }
    private Date getDate(){
        Date date = new GregorianCalendar(2000,1 ,1).getTime();
        return date;
    }

}
