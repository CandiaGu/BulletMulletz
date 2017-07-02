package gu.wen.bulletmullet;
import java.util.*;
import android.content.Context;
/**
 * Created by angelwen on 6/18/17.
 */

public class DayEntry implements DayEntryInterface{
    private Date date;
    private Bullet bullet;

    public DayEntry(Context ctx, Date newDate){
        date = newDate;
        bullet = new Bullet(ctx, newDate);
    }

    /**called the addBullet method in Bullet**/
    public void addBullet(String bulletType, String text){
        System.out.println("hello");
        //bullet.addBullet(bulletType, text,date);
    }

    public void reorderBullet(int prev_pos, int new_pos){

    }

    public LinkedList<String> getEventsList(){
        bullet.getEventsList();
        return null;
    }
    public LinkedList<String> getTodoList(){
        return null;
    }
    public LinkedList<String> getNotesList(){
        return null;
    }
}
