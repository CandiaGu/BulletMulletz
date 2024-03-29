package gu.wen.bulletmullet.interfaces;
import java.util.*;

import gu.wen.bulletmullet.data.BulletItem;

/**
 * Created by angelwen on 7/2/17.
 */

public interface DayEntryInterface {

    /**adds a bullet with the type bullet Type (event, to-do (without the dash), note) and the text in text
     * to the day**/
    public void addBullet(String bulletType, String text);

    /**deletes bullet with that specification **/
    public void deleteBullet(String bulletType, String text, int position);

    /**puts a bullet in the prev position into the new position**/
    public void reorderBullet(String bulletType, int prev_pos, int new_pos);

    /** returns a list of the events (things with times) to show**/
    public ArrayList<BulletItem> getEventsList();

    /** returns a list of todos to show**/
    public ArrayList<BulletItem> getTodoList();

    /** returns a list of notes to show**/
    public ArrayList<BulletItem> getNotesList();

    /** returns the date as a string**/
    public String getDateString();

    /** clears all of the bullets in the day **/
    public void clearAllBullets();

    public Date getDate();
}
