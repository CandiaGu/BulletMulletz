package gu.wen.bulletmullet.data;
import java.util.*;
import android.content.Context;

import gu.wen.bulletmullet.interfaces.DayEntryInterface;

/**
 * Created by angelwen on 6/18/17.
 */

public class DayEntry implements DayEntryInterface {
    private Date date;
    private BulletWorker bullet;

    public DayEntry(Context ctx, Date newDate){
        date = newDate;
        bullet = new BulletWorker(ctx,newDate);
    }

    /**called the addBullet method in Bullet**/
    public void addBullet(String bulletType, String text){
        //System.out.println("hello");
        bullet.addBullet(bulletType, text);
    }
    public void deleteBullet(String bulletType, String text){
        System.out.println("should delete bullet in the future");
    }

    public void reorderBullet(String bulletType, int prev_pos, int new_pos){
        bullet.reorderBullet(bulletType, prev_pos, new_pos);
    }

    public LinkedList<BulletItem> getEventsList(){
        return bullet.getBulletList("event");
    }
    public LinkedList<BulletItem> getTodoList() {
        return bullet.getBulletList("todo");

    }
    public LinkedList<BulletItem> getNotesList(){
        return bullet.getBulletList("note");
    }
}
