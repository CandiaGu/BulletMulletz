package gu.wen.bulletmullet;
import java.util.*;
/**
 * Created by angelwen on 6/18/17.
 */

public class DayEntry implements DayEntryInterface{
    private Date date;
    private Bullet bullet;

    public DayEntry(Date newDate){
        date = newDate;
        bullet = new Bullet(newDate);
    }

    /**called the addBullet method in Bullet**/
    public void addBullet(String bulletType, String text){
        bullet.addBullet(bulletType, text);
    }

    public void reorderBullet(int prev_pos, int new_pos){

    }
    public LinkedList<String> getEventsList(){
        return null;
    }
    public LinkedList<String> getTodoList(){
        return null;
    }
    public LinkedList<String> getNotesList(){
        return null;
    }
}
