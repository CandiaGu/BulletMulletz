package gu.wen.bulletmullet;


import android.database.sqlite.SQLiteDatabase;

//import android.content.Context;

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
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public DayEntry[] dayEntries;

    Button button;
    EditText addTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly);
        setRecyclerView();

        Log.d(TAG, "onCreate()");

        addTask = (EditText) findViewById(R.id.add_task);
        addTask.setVisibility(View.GONE);

        
    }
    public void setRecyclerView(){
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        initiateDayEntries();

        //LinkedList<String> toDoList = dayEntries[0].getTodoList();
        //String[] myDataset =toDoList.toArray(new String[toDoList.size()]);
        // specify an adapter (see also next example)
        //mAdapter = new MyAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);

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
            //dayEntries[i] = new DayEntry(cal.getTime());
        }

    }
    public void addTask(View view){
        View parent = (View) view.getParent();
        button = (Button) findViewById(R.id.add_task_button);
        addTask.setVisibility(View.VISIBLE);
        //create make edittext visible
        //add new bullet


    }

}




