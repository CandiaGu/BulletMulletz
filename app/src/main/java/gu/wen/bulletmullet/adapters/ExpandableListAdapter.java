package gu.wen.bulletmullet.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import gu.wen.bulletmullet.R;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

	private Context _context;
	private List<String> _listDataHeader; // header titles
	// child data in format of header title, child title
	private HashMap<String, List<String>> _listDataChild;
    private int childTypeCount;

	public ExpandableListAdapter(Context context, List<String> listDataHeader,
                                 HashMap<String, List<String>> listChildData) {
		this._context = context;
		this._listDataHeader = listDataHeader;
		this._listDataChild = listChildData;
        childTypeCount = 2;
	}

	@Override
	public Object getChild(int groupPosition, int childPosititon) {
		return this._listDataChild.get(this._listDataHeader.get(groupPosition))
				.get(childPosititon);
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
	public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

		final String childText = (String) getChild(groupPosition, childPosition);

		if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            switch(getChildType(groupPosition, childPosition)){
                case 0:
                    convertView = infalInflater.inflate(R.layout.list_item, null);
                    break;
                case 1:
                    convertView = infalInflater.inflate(R.layout.list_item2, null);
                    break;
            }


		}

		TextView txtListChild = (TextView) convertView
				.findViewById(R.id.lblListItem);

		txtListChild.setText(childText);
		return convertView;
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
