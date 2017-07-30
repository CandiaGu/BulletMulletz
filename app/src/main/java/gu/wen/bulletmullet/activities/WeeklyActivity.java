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
import gu.wen.bulletmullet.data.BulletItem;
public class WeeklyActivity extends AppCompatActivity {

    private static final String TAG = "WeeklyActivity";// for log printing purposes


    private RecyclerView curRecyclerView;// list of tasks
    private DayEntryAdapter curAdapter;// to make recyclerview responsive
    private RecyclerView.LayoutManager mLayoutManager;// to alter recyclerview layout

    private int numWeekDays = 7;
    private DayEntry[] dayEntries = new DayEntry[numWeekDays];// for each day of the week

    Button button;
    EditText add_task; // to add tasks to recyclerview
    private static Context context; // context of current state of the application/object

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d(TAG, "onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly);

        initiateVariables();
        initiateViews();
        initiateListeners();

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        boolean handleReturn = super.dispatchTouchEvent(ev);

        int x = (int) ev.getX();
        int y = (int) ev.getY();

        //retracts the keyboard if not pressing
        if (ev.getAction() == MotionEvent.ACTION_UP &&
                !getLocationOnScreen((add_task)).contains(x, y)) {

            InputMethodManager input = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            updateUI(false);
        }


        return handleReturn;
    }

    private void initiateVariables(){
        context = getApplicationContext();
        initiateDayEntries();
        add_task = (EditText) findViewById(R.id.add_task);
        add_task.setVisibility(View.GONE);
    }

    private void initiateViews(){
        setRecyclerView();
    }

    private void initiateListeners(){
        addKeyListener();
    }

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


    protected Rect getLocationOnScreen(EditText mEditText) {
        Rect mRect = new Rect();
        int[] location = new int[2];

        mEditText.getLocationOnScreen(location);

        mRect.left = location[0];
        mRect.top = location[1];
        mRect.right = location[0] + mEditText.getWidth();
        mRect.bottom = location[1] + mEditText.getHeight();

        return mRect;
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

    private void updateUI(boolean keyboardDisplayed){

        //adjusts recyclerview size
        ViewGroup.LayoutParams params= curRecyclerView.getLayoutParams();
        params.height= RecyclerView.LayoutParams.WRAP_CONTENT;
        curRecyclerView.setLayoutParams(params);

        if(!keyboardDisplayed){
            InputMethodManager input = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            if (getWindow().getCurrentFocus()
                    .getWindowToken()!=null) {
                input.hideSoftInputFromWindow(getWindow().getCurrentFocus()
                        .getWindowToken(), 0);
            }
        }
    }

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
    public void removeTask(View view){
        View parent = (View) view.getParent();
        TextView taskTextView = (TextView) parent.findViewById(R.id.task_content);
        String task = String.valueOf(taskTextView.getText());
        BulletItem bi = (BulletItem)(taskTextView.getTag());
        int position = bi.getPosition();
        curAdapter.delete("todo",task, position);
        updateUI(false);


    }

}




