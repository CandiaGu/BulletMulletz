package gu.wen.bulletmullet;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.*;

public class WeeklyActivityFake extends AppCompatActivity {
    private EditText edittext;
    private static Context context;
    private static DayEntry d;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly);
        addKeyListener();
        context = getApplicationContext();
        d = new DayEntry(context,Calendar.getInstance().getTime());
    }
    public void addKeyListener() {
        // get edittext component
        edittext = (EditText) findViewById(R.id.editText);

        // add a keylistener to keep track user input
        edittext.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                // if keydown and "enter" is pressed
                if ((event.getAction() == KeyEvent.ACTION_DOWN)
                        && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    d.addBullet("event",edittext.getText().toString());
                    d.addBullet("todo",edittext.getText().toString());
                    d.addBullet("note",edittext.getText().toString());
                    // display a floating message
                    Toast.makeText(WeeklyActivity.this,
                            edittext.getText(), Toast.LENGTH_LONG).show();
                    return true;

                } else if ((event.getAction() == KeyEvent.ACTION_DOWN)
                        && (keyCode == KeyEvent.KEYCODE_9)) {
                    LinkedList<BulletItem> events = d.getNotesList();
                    Iterator itr = events.iterator();
                    String events_str = "";
                    BulletItem curr;
                    while (itr.hasNext()){
                        curr = (BulletItem)itr.next();
                        events_str = events_str +curr.toString()+curr.getPosition()+"\n";
                    }

                    // display a floating message
                    Toast.makeText(WeeklyActivity.this,
                            events_str, Toast.LENGTH_LONG).show();
                    return true;
                }else if ((event.getAction() == KeyEvent.ACTION_DOWN)
                        && (keyCode == KeyEvent.KEYCODE_8)) {
                    d.reorderBullet("note",0,3);
                    LinkedList<BulletItem> events = d.getNotesList();
                    Iterator itr = events.iterator();
                    String events_str = "";
                    BulletItem curr;
                    while (itr.hasNext()){
                        curr = (BulletItem)itr.next();
                        events_str = events_str +curr.toString()+curr.getPosition()+"\n";
                    }

                    // display a floating message
                    Toast.makeText(WeeklyActivity.this,
                            events_str, Toast.LENGTH_LONG).show();
                    return true;
                }else if ((event.getAction() == KeyEvent.ACTION_DOWN)
                        && (keyCode == KeyEvent.KEYCODE_7)) {
                    d.deleteBullet("note","thing");

                    return true;
                }

                return false;
            }
        });
    }
}

