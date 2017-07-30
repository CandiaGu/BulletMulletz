package gu.wen.bulletmullet.adapters;

import android.provider.Settings;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import gu.wen.bulletmullet.R;
import gu.wen.bulletmullet.data.DayEntry;

/**
 * Created by candiagu on 7/6/17.
 */

public class DayEntryAdapter extends RecyclerView.Adapter<DayEntryAdapter.ViewHolder> {
    private DayEntry dayEntry;


    // Provide a suitable constructor (depends on the kind of dataset)
    public DayEntryAdapter(DayEntry myDataset) {
        dayEntry = myDataset;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            mTextView = (TextView) v.findViewById(R.id.task_content);
        }
    }

    public void add(String bulletType, String text) {
        dayEntry.addBullet(bulletType, text);
        System.out.print("adding text:" + text);
        Log.d("todo", dayEntry.getTodoList().toString());
        System.out.println("todo"+ dayEntry.getTodoList().toString());
        //last position
        int pos = getItemCount();
        notifyItemInserted(pos-1);
    }

    public void delete(String bullettype, String text, int position){
        System.out.println("position is "+position+" and string is "+text);
        dayEntry.deleteBullet(bullettype,text,position);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public DayEntryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_task, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        System.out.println("the on bind view holder thing has this: "+ dayEntry.getTodoList().get(position).toString());
        holder.mTextView.setText(dayEntry.getTodoList().get(position).toString());
        holder.mTextView.setTag(dayEntry.getTodoList().get(position));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return dayEntry.getTodoList().size();
    }
}




