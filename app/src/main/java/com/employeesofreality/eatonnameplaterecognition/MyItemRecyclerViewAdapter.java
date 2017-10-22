package com.employeesofreality.eatonnameplaterecognition;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.employeesofreality.eatonnameplaterecognition.shopping.Content;
import com.employeesofreality.eatonnameplaterecognition.shoppingFragment.OnListFragmentInteractionListener;
import com.employeesofreality.eatonnameplaterecognition.shopping.Content.Item;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Item} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final List<Item> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyItemRecyclerViewAdapter(List<Item> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shopping_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        //holder.mIdView.setText(mValues.get(position).id);
        /*
        if(!mValues.get(position).values.get("CatalogNumber").equals(""))
        {
            holder.mContentView.setText(mValues.get(position).values.get("CatalogNumber"));
        }
        else if(!mValues.get(position).values.get("OrderNumber").equals(""))
        {
            holder.mContentView.setText(mValues.get(position).values.get("OrderNumber"));
        }
        else if(!mValues.get(position).values.get("SerialNumber").equals(""))
        {
            holder.mContentView.setText(mValues.get(position).values.get("SerialNumber"));
        }
        else
        {

        }
        */
        if(Content.ITEMS.get(position).values.get("Name").isEmpty()) {
            Content.ITEMS.get(position).values.put("Name", "Part " + (int) (position + 1));
        }
        
        holder.mContentView.setText(Content.ITEMS.get(position).values.get("Name"));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public Item mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = null;//(TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
