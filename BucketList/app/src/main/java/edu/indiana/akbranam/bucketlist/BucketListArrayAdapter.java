package edu.indiana.akbranam.bucketlist;

/**BucketListArrayAdapter.java: adapter class for listViews in the app
 * Created by Anna Branam
 * Created on 3/1/2017.
 * Last Modified by: Anna Branam
 * Last Modified on: 3/3/2017
 */

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class BucketListArrayAdapter extends ArrayAdapter<BucketListItem> {

    Context context;

    public BucketListArrayAdapter(Context context,  ArrayList<BucketListItem> resource) {
        super(context, 0, resource);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        BucketListItem item = getItem(position);

        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View v = inflater.inflate(R.layout.content_bucket_list_item, parent, false);
        TextView textName = (TextView) v.findViewById(R.id.txt_list_item_name);
        String prefix = "";

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);//get settings
        boolean nums = prefs.getBoolean("numbers", true);// if numbers list setting is selected
        if (nums){
            prefix = Integer.toString(position+1) + ". ";// add number prefix to title
        }
        textName.setText(prefix+item.getItemName());//set background color of item
        if (position%2==0) {
            textName.setBackgroundResource(R.color.evenListItemBackground);
        }
        else{
            textName.setBackgroundResource(R.color.oddListItemBackground);
        }

        return v;
    }
}
