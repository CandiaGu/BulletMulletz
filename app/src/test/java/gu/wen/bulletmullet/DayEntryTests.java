package gu.wen.bulletmullet;
import java.util.*;
import org.junit.Test;
import java.util.regex.Pattern;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by angelwen on 7/2/17.
 */


public class DayEntryTests {
    @Test
    public void addEventsToDayEntry() {
        DayEntry d = new DayEntry(new Date());
        d.addBullet("event","9 am HELLO");
        d.getEventsList();
    }
}
