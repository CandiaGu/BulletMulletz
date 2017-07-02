package gu.wen.bulletmullet;
import java.util.*;
/**
 * Created by angelwen on 7/2/17.
 */

public interface DayEntryInterface {

    /**adds a bullet with the type bullet Type (event, to-do (without the dash), note) and the text in text
     * to the day**/
    public void addBullet(String bulletType, String text);

    /**puts a bullet in the prev position into the new position**/
    public void reorderBullet(int prev_pos, int new_pos);

    /** returns a linked list of the events (things with times) to show**/
    public LinkedList<String> getEventsList();

    /** returns a linked list of todos to show**/
    public LinkedList<String> getTodoList();

    /** returns a linked list of notes to show**/
    public LinkedList<String> getNotesList();
}
