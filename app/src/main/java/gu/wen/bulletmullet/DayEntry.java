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

    public String getEventsList(){
        LinkedList<String> events = bullet.getBulletList("event");
        Iterator itr = events.iterator();
        String events_str = "";
        while (itr.hasNext()){
            events_str = events_str +itr.next()+"\n";
        }
        return events_str;
    }
    public String getTodoList() {
        LinkedList<String> events = bullet.getBulletList("todo");
        Iterator itr = events.iterator();
        String events_str = "";
        while (itr.hasNext()){
            events_str = events_str +itr.next()+"\n";
        }
        return events_str;
    }
    public String getNotesList(){
        LinkedList<String> events = bullet.getBulletList("note");
        Iterator itr = events.iterator();
        String events_str = "";
        while (itr.hasNext()){
            events_str = events_str +itr.next()+"\n";
        }
        return events_str;
    }
}
