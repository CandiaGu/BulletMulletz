package gu.wen.bulletmullet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.Toast;
import java.util.*;


public class WeeklyActivity extends AppCompatActivity {
    private EditText edittext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly);
        addDaysoftheWeek();
        //addKeyListener();
    }

    public void addDaysoftheWeek(){
        DayEntry day1 = new DayEntry(new Date());

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

                    // display a floating message
                    Toast.makeText(WeeklyActivity.this,
                            "Number 9 is pressed!", Toast.LENGTH_LONG).show();
                    return true;
                }

                return false;
            }
        });
    }
}

