package com.android.returncandidate.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.android.returncandidate.R;
import com.android.returncandidate.common.constants.Constants;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * <h1>List View Adapter for List Publisher Screen</h1>
 * <p>
 * Adapter for list view Publisher in filter dialog
 *
 * @author cong-pv
 * @since 2018-07-09.
 */
public class ListViewPublisherAdapter extends BaseAdapter {

    /**
     * List item
     */
    public ArrayList<HashMap<String, String>> list;

    /**
     * Activity
     */
    private Activity activity;

    /**
     * Constructor List View Adapter
     *
     * @param activity Main Activity call this Adapter
     * @param list     list data to set adapter
     */
    public ListViewPublisherAdapter(Activity activity, ArrayList<HashMap<String, String>> list) {

        super();
        this.activity = activity;
        this.list = list;
    }

    /**
     * Get count item
     *
     * @return list size is int
     */
    @Override
    public int getCount() {
        return list.size();
    }

    /**
     * Get item with position
     *
     * @param position position selected
     * @return object in list by position selected
     */
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    /**
     * Get Item Id with position
     *
     * @param position position selected
     * @return id for default
     */
    @Override
    public long getItemId(int position) {
        return 0;
    }

    /**
     * Init View Holder
     */
    private class ViewHolder {
        TextView txv_id;
        TextView txv_name;
        CheckBox checkBox;
    }

    /**
     * Set custom layout for list view
     *
     * @param position    in list data
     * @param convertView set layout from custom layout
     * @param parent      view group
     * @return view from layout custom
     */
    @SuppressLint("InflateParams")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        LayoutInflater inflater = activity.getLayoutInflater();

        if (convertView == null) {

            // Init custom layout list genre
            convertView = inflater.inflate(R.layout.list_publisher, null);
            holder = new ViewHolder();


            // Init column in list view
            holder.txv_id = (TextView) convertView.findViewById(R.id.txv_id);
            holder.txv_name = (TextView) convertView.findViewById(R.id.txv_name_header);
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.rowCheckBox);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        // Set data in list to list view


        HashMap<String, String> map = list.get(position);
        //holder.txv_id.setText(map.get(Constants.COLUMN_PUBLISHER_CD));
        holder.txv_id.setText(map.get(Constants.COLUMN_PUBLISHER_NAME));
        holder.txv_name.setText(map.get(Constants.COLUMN_PUBLISHER_NAME));
        if (Constants.VALUE_STR_CHECK.equals(list.get(position).get(Constants.FLAG_SELECT))) {
            holder.checkBox.setChecked(true);
        } else {
            holder.checkBox.setChecked(false);
        }

        holder.checkBox.setOnClickListener(onStateChangedListener(holder.checkBox, position));
        return convertView;
    }

    private View.OnClickListener onStateChangedListener(final CheckBox checkBoxs, final int positions) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //If check all
                if (positions == 0) {
                    //when select all is check
                    if (!checkBoxs.isChecked()) {
                        for (int i = 0; i < list.size(); i++) {
                            list.get(i).put(Constants.FLAG_SELECT, Constants.VALUE_STR_NO_CHECK);
                            list.set(i, list.get(i));
                        }
                    } else { // when select all is not check
                        for (int i = 0; i < list.size(); i++) {
                            list.get(i).put(Constants.FLAG_SELECT, Constants.VALUE_STR_CHECK);
                            list.set(i, list.get(i));
                        }
                    }
                } else {
                    //uncheck  select all
                    list.get(0).put(Constants.FLAG_SELECT, Constants.VALUE_STR_NO_CHECK);
                    list.set(0, list.get(0));
                    //uncheck or check other
                    if (!checkBoxs.isChecked()) {
                        list.get(positions).put(Constants.FLAG_SELECT, Constants.VALUE_STR_NO_CHECK);
                        list.set(positions, list.get(positions));
                    } else { // Item not check
                        list.get(positions).put(Constants.FLAG_SELECT, Constants.VALUE_STR_CHECK);
                        list.set(positions, list.get(positions));
                    }
                }

                //Check all or not all
                int intCheckAll = 0;
                for (int i = 0; i < list.size(); i++) {
                    if (Constants.VALUE_STR_CHECK.equals(list.get(i).get(Constants.FLAG_SELECT))) {
                        intCheckAll++;
                    }
                }
                if (intCheckAll == list.size() - 1) {
                    list.set(0, list.get(0));
                    list.get(0).put(Constants.FLAG_SELECT, Constants.VALUE_STR_CHECK);
                }
                notifyDataSetChanged();

            }
        };
    }
}
