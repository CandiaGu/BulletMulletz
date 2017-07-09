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
        bullet = new Bullet(ctx,newDate);
    }

    /**called the addBullet method in Bullet**/
    public void addBullet(String bulletType, String text){
        //System.out.println("hello");
        bullet.addBullet(bulletType, text);
    }
    public void deleteBullet(String bulletType, String text){
        System.out.println("should delete bullet in the future");
    }

    public void reorderBullet(int prev_pos, int new_pos){

    }

    public LinkedList<String> getEventsList(){
        return bullet.getBulletList("event");
    }
    public LinkedList<String> getTodoList() {
        return bullet.getBulletList("todo");
    }
    public LinkedList<String> getNotesList(){
        return bullet.getBulletList("note");
    }
}
