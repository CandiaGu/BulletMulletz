package gu.wen.bulletmullet;


import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;

import android.content.Context;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.*;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

public class WeeklyActivity extends AppCompatActivity {
    //private EditText edittext;
    private static final String TAG = "WeeklyActivity";

    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public DayEntry[] dayEntries = new DayEntry[7];

    Button button;
    EditText add_task;
    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly);


        Log.d(TAG, "onCreate()");

        context = getApplicationContext();
        initiateDayEntries();
        setRecyclerView();
        add_task = (EditText) findViewById(R.id.add_task);
        add_task.setVisibility(View.GONE);

        addKeyListener();

        
    }
    public void setRecyclerView(){
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        DayEntry myDataset = dayEntries[0];

        // specify an adapter - used to update recyclerview
        mAdapter = new MyAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);

    }

    public void addKeyListener(){
        //add new bullet
        ///Log.d(TAG, "Task to add: " + task);
        final DayEntry d = dayEntries[0];

        add_task.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                // if keydown and "enter" is pressed
                if ((event.getAction() == KeyEvent.ACTION_DOWN)
                        && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    String task = String.valueOf(add_task.getText());
                    if(task!="")
                        mAdapter.add("todo", task);
                    // display a floating message
                    Toast.makeText(WeeklyActivity.this,
                            add_task.getText(), Toast.LENGTH_LONG).show();
                    //make edittext invisible and empty
                    add_task.setVisibility(View.GONE);
                    add_task.setText("");
                    return true;

                }
                return false;
            }

        });

    }
    //initiates the day entries for the current week, starting with monday of this week
    public void initiateDayEntries(){
        // get today and clear time of day
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the hour of day !
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);
        // get start of this week in milliseconds
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        for(int i = 0; i < 7; i++){
            cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek()+i);
            dayEntries[i] = new DayEntry(context, cal.getTime());
        }

    }
    public void addTask(View view){
        View parent = (View) view.getParent();
        button = (Button) findViewById(R.id.add_task_button);

        //make edittext visible
        add_task.setVisibility(View.VISIBLE);
        //create make edittext visible
    }
    public void deleteTask(View view){
        View parent = (View) view.getParent();
        TextView taskTextView = (TextView) parent.findViewById(R.id.task_delete);
        //mAdapter.delete


    }

}




