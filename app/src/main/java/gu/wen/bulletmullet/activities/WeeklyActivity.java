package gu.wen.bulletmullet.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import gu.wen.bulletmullet.R;
import gu.wen.bulletmullet.adapters.ExpandableListAdapter;
import gu.wen.bulletmullet.data.DayEntry;

public class WeeklyActivity extends AppCompatActivity {


    private static final String TAG = "WeeklyActivity";// for log printing purposes

    private RecyclerView.LayoutManager mLayoutManager;// to alter recyclerview layout

    private String[] DAY_OF_WEEK = {"Monday", "Tuesday", "Wednesday", "Thursday",
                                                        "Friday", "Saturday", "Sunday"};
    private int NUM_WEEK_DAYS = 7;
    private DayEntry[] dayEntries = new DayEntry[NUM_WEEK_DAYS];// for each day of the week

    Button button;
    EditText add_task; // to add tasks to recyclerview
    private static Context context; // context of current state of the application/object



    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, DayEntry> listDataChild;


    /**
     * Creates the initial display
     * @param savedInstanceState stores information directly related to the current activity state
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d(TAG, "onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initiateVariables();
        initiateViews();



        //addKeyListener();

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
        if (ev.getAction() == MotionEvent.ACTION_UP) {

            InputMethodManager input = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);

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
        //add_task = (EditText) findViewById(R.id.add_task);
        //add_task.setVisibility(View.GONE);
    }

    private void initiateViews(){
        //setRecyclerView();
        setExpandableListView();
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

    private void setExpandableListView(){
        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
        for(int i = 0; i < listAdapter.getGroupCount(); i++)
            if(listAdapter.getChildrenCount(i) == 0)
                listAdapter.addChild(i,"addTask");
        // setting list adapter
        expListView.setAdapter(listAdapter);

        // Listview Group click listener
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();

            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();

            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
                Toast.makeText(
                        getApplicationContext(),
                        listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                listDataHeader.get(groupPosition)).getTodoList().get(
                                childPosition), Toast.LENGTH_SHORT)
                        .show();



                return false;
            }
        });


    }
    /*
 * Preparing the list data
 */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, DayEntry>();

        // Adding child data
        for(int i = 0; i < NUM_WEEK_DAYS; i++){
            listDataHeader.add(DAY_OF_WEEK[i]);

            System.out.print(dayEntries[i].getTodoList().size());
            //if(dayEntries[i].getTodoList().size() == 0)//for the edittext entry
            //    dayEntries[i].addBullet("todo", "addBullet");
            listDataChild.put(listDataHeader.get(i), dayEntries[i]);

        }

    }


/// Updates


    /**
     * Updates the UI
     * @param keyboardDisplayed True if keyboard needs to be displayed
     */
    private void updateUI(boolean keyboardDisplayed){

        if(!keyboardDisplayed){
            InputMethodManager input = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            View focus = getWindow().getCurrentFocus();

            if (focus!=null)
                input.hideSoftInputFromWindow(focus.getWindowToken(), 0);
            listAdapter.makeEditTextInvisible();

        }
    }




}
