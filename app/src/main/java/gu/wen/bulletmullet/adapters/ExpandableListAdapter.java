package gu.wen.bulletmullet.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.os.SystemClock;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.jar.Pack200;

import gu.wen.bulletmullet.R;
import gu.wen.bulletmullet.activities.WeeklyActivity;
import gu.wen.bulletmullet.data.BulletItem;
import gu.wen.bulletmullet.data.DayEntry;

import static android.content.ContentValues.TAG;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

	private Context _context;
	private List<String> _listDataHeader; // header titles
	// child data in format of header title, child title
	private HashMap<String, DayEntry> _listDataChild;
    private int childTypeCount;

	public ExpandableListAdapter(Context context, List<String> listDataHeader,
                                 HashMap<String, DayEntry> listChildData) {
		this._context = context;
		this._listDataHeader = listDataHeader;
		this._listDataChild = listChildData;
        childTypeCount = 2;
	}

	@Override
	public String getChild(int groupPosition, int childPosition) {
		return this._listDataChild.get(this._listDataHeader.get(groupPosition))
				.get(childPosition).toString();
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

    @Override
    public int getChildType(int groupPosition, int childPosition) {

        // Return a number here, 1 to whatever you return in getChildTypeCount.
        // Each number should correspond to a particular layout, using group
        // and child position to determine which layout to produce.
        //0 -> bulletitem
        //1 -> edittext
        if (childPosition < getChildrenCount(groupPosition)-1)
            return 0;
        else
            return 1;
        //return super.getChildType(groupPosition, childPosition);
    }

    @Override
    public int getChildTypeCount() {

        // Return the number of distinct layouts you expect to create
        return childTypeCount;
        //return super.getChildTypeCount();
    }

	@Override
	public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

		final String childText = getChild(groupPosition, childPosition);

		if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            switch(getChildType(groupPosition, childPosition)){
                case 0:
                    convertView = infalInflater.inflate(R.layout.list_item, null);
                    TextView txtListChild = (TextView) convertView
                            .findViewById(R.id.lblListItem);
                    txtListChild.setText(childText);
                    break;
                case 1:
                    convertView = infalInflater.inflate(R.layout.list_item2, null);
                    final EditText et = (EditText)convertView.findViewById(R.id.lblListItem);
                    //et.setEnabled(false);
                    et.setClickable(false);
                    et.setVisibility(View.GONE);
                    final Button add_task=(Button)convertView.findViewById(R.id.add_task_butt);
                    add_task.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            et.setVisibility(View.VISIBLE);
                            et.requestFocus();
                            et.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(),
                                    SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN , 0, 0, 0));
                            et.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(),
                                    SystemClock.uptimeMillis(), MotionEvent.ACTION_UP , 0, 0, 0));
                            et.setOnKeyListener(new View.OnKeyListener() {
                                public boolean onKey(View v, int keyCode, KeyEvent event) {

                                    // if keydown and "enter" is pressed
                                    if ((event.getAction() == KeyEvent.ACTION_DOWN)
                                            && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                                        String task = String.valueOf(et.getText()).trim();
                                        if(task!= null && !task.isEmpty()) {
                                            addChild(groupPosition, task);
                                            // display a floating message
                                            Toast.makeText(_context,
                                                    task, Toast.LENGTH_LONG).show();
                                        }
                                        //make edittext invisible and empty
                                        et.setVisibility(View.GONE);
                                        et.setText("");

                                        //updateUI(false);
                                        notifyDataSetChanged();
                                        return true;

                                    }
                                    return false;
                                }

                            });
                        }
                    });

                    break;
            }


		}



		return convertView;
	}
    public void addChild(int groupPosition, String task){
        Log.d(TAG, "addChild");
        List list = this._listDataChild.get(this._listDataHeader.get(groupPosition));
        addBullet()

    }


	@Override
	public int getChildrenCount(int groupPosition) {
		return this._listDataChild.get(this._listDataHeader.get(groupPosition))
				.size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return this._listDataHeader.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return this._listDataHeader.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
		String headerTitle = (String) getGroup(groupPosition);
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this._context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.list_group, null);
		}

		TextView lblListHeader = (TextView) convertView
				.findViewById(R.id.lblListHeader);
		lblListHeader.setTypeface(null, Typeface.BOLD);
		lblListHeader.setText(headerTitle);

		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}



}
