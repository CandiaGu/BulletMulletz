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

public class WeeklyActivity extends AppCompatActivity {
    private EditText edittext;
    private static Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly);
        addKeyListener();
        context = getApplicationContext();
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

                    // display a floating message
                    Toast.makeText(WeeklyActivity.this,
                            edittext.getText(), Toast.LENGTH_LONG).show();
                    return true;

                } else if ((event.getAction() == KeyEvent.ACTION_DOWN)
                        && (keyCode == KeyEvent.KEYCODE_9)) {
                    DayEntry d = new DayEntry(context,Calendar.getInstance().getTime());
                    d.addBullet("event","hello this is an event");
                    d.addBullet("todo","todododododo");
                    d.addBullet("note","hello do this NOTE");
                    LinkedList<String> events = d.getNotesList();
                    Iterator itr = events.iterator();
                    String events_str = "";
                    while (itr.hasNext()){
                        events_str = events_str +itr.next()+"\n";
                    }
                    // display a floating message
                    Toast.makeText(WeeklyActivity.this,
                            events_str, Toast.LENGTH_LONG).show();
                    return true;
                }

                return false;
            }
        });
    }
}

