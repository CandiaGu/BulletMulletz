package gu.wen.bulletmullet.activities;

import android.content.Context;
import android.graphics.Rect;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Calendar;
import gu.wen.bulletmullet.adapters.DayEntryAdapter;
import gu.wen.bulletmullet.R;
import gu.wen.bulletmullet.data.DayEntry;

public class WeeklyActivity extends AppCompatActivity {


    private static final String TAG = "WeeklyActivity";// for log printing purposes


    private RecyclerView curRecyclerView;// list of tasks
    private DayEntryAdapter curAdapter;// to make recyclerview responsive
    private RecyclerView.LayoutManager mLayoutManager;// to alter recyclerview layout

    private int NUM_WEEK_DAYS = 7;
    private DayEntry[] dayEntries = new DayEntry[NUM_WEEK_DAYS];// for each day of the week

    Button button;
    EditText add_task; // to add tasks to recyclerview
    private static Context context; // context of current state of the application/object

    /**
     * Creates the initial display
     * @param savedInstanceState stores information directly related to the current activity state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d(TAG, "onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly);

        initiateVariables();


        addKeyListener();

    }
/// Initiation
    /**
     * A controller which decides how to route the touch events
     * @param ev occuring touch event
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        boolean handleReturn = super.dispatchTouchEvent(ev);

        int x = (int) ev.getX();
        int y = (int) ev.getY();

        //retracts the keyboard if not pressing
        if (ev.getAction() == MotionEvent.ACTION_UP &&
                !getLocationOnScreen((add_task)).contains(x, y)) {

            updateUI(false);
        }


        return handleReturn;
    }
    /**
     * Initiates global variables
     */
    private void initiateVariables(){
        context = getApplicationContext();
        initiateDayEntries();
        add_task = (EditText) findViewById(R.id.add_task);
        add_task.setVisibility(View.GONE);

        setRecyclerView();
    }

    /**
     * Initiates the day entries for the current week, starting with Monday of this week
     */
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

    /**
     * Sets the layout manager and the adapter for the RecyclerView bullet list
     */
    private void setRecyclerView(){
        curRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        curRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        curRecyclerView.setLayoutManager(mLayoutManager);


        DayEntry myDataset = dayEntries[0];

        // specify an adapter - used to update recyclerview
        curAdapter = new DayEntryAdapter(myDataset);
        curRecyclerView.setAdapter(curAdapter);

    }

    /**
     * Adds key listeners to views that require actions when interacted with
     *
     * Listeners:
     *
     * add_task - once enter is  pressed, adds text to database and makes the edittext invisible
     */
    public void addKeyListener(){
        //add new bullet
        ///Log.d(TAG, "Task to add: " + task);
        final DayEntry d = dayEntries[0];

        add_task.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                // if keydown and "enter" is pressed
                if ((event.getAction() == KeyEvent.ACTION_DOWN)
                        && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    String task = String.valueOf(add_task.getText()).trim();
                    if(task!= null && !task.isEmpty()) {
                        curAdapter.add("todo", task);
                        // display a floating message
                        Toast.makeText(WeeklyActivity.this,
                                task, Toast.LENGTH_LONG).show();
                    }
                    //make edittext invisible and empty
                    add_task.setVisibility(View.GONE);
                    add_task.setText("");

                    updateUI(false);
                    return true;

                }
                return false;
            }

        });

    }

/// Updates

    /**
     * Updates the UI
     * @param keyboardDisplayed True if keyboard needs to be displayed
     */
    private void updateUI(boolean keyboardDisplayed){

        //adjusts recyclerview size
        ViewGroup.LayoutParams params= curRecyclerView.getLayoutParams();
        params.height= RecyclerView.LayoutParams.WRAP_CONTENT;
        curRecyclerView.setLayoutParams(params);

        if(!keyboardDisplayed){
            InputMethodManager input = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            View focus = getWindow().getCurrentFocus();
            if (focus!=null)
                input.hideSoftInputFromWindow(focus.getWindowToken(), 0);
        }
    }

//// Button Functions

    /**
     * Function called when add_task_button is pressed
     * Makes edittext visible and pressed on it programmatically
     * @param view
     */
    public void addTask(View view){
        View parent = (View) view.getParent();
        button = (Button) findViewById(R.id.add_task_button);

        //make edittext visible
        add_task.setVisibility(View.VISIBLE);
        //create make edittext visible

        // programmatically presses add_task
        add_task.requestFocus();
        add_task.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(),
                SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN , 0, 0, 0));
        add_task.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(),
                SystemClock.uptimeMillis(), MotionEvent.ACTION_UP , 0, 0, 0));
    }

    /**
     * Function called to remove bullets
     * @param view
     */
    public void removeTask(View view){
        View parent = (View) view.getParent();
        TextView taskTextView = (TextView) parent.findViewById(R.id.task_content);
        String task = String.valueOf(taskTextView.getText());

        curAdapter.delete("todo",task);
        updateUI(false);


    }

//// Helper Functions
    /**
     * Gets a Rect of the View box
     * @param view the view to get the location of
     * @return the border Rect of the view
     */
    protected Rect getLocationOnScreen(View view) {
        Rect mRect = new Rect();
        int[] location = new int[2];

        view.getLocationOnScreen(location);

        mRect.left = location[0];
        mRect.top = location[1];
        mRect.right = location[0] + view.getWidth();
        mRect.bottom = location[1] + view.getHeight();

        return mRect;
    }

}




