package edu.indiana.akbranam.bucketlist;

/**BucketListToDoFragment.java: java file for the To-do list screen of the app
 * used in BucketListMainActivity
 * Created by Anna Branam
 * Created on 2/21/2017.
 * Last Modified by: Anna Branam
 * Last Modified on: 3/3/2017
 */

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import java.util.ArrayList;


public class BucketListToDoFragment extends Fragment implements OnClickListener{

    private ListView listViewItems;
    private ArrayList<BucketListItem> itemsList;
    private BucketListArrayAdapter blAdapter;

    public BucketListToDoFragment() {
    }

    public static BucketListToDoFragment newInstance() {
        BucketListToDoFragment fragment = new BucketListToDoFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_bucket_list_to_do, container, false);

        //get uncompleted items from database
        BucketListDataBaseHandler handler = new BucketListDataBaseHandler(getActivity());
        itemsList = handler.getList(0);
        listViewItems = (ListView) rootView.findViewById(R.id.list_view_to_do);
        //set ListView adapter
        blAdapter = new BucketListArrayAdapter(this.getContext(), itemsList);
        listViewItems.setAdapter(blAdapter);
        listViewItems.setOnItemClickListener(lsvItemClickListener);//select list items
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateList();//update list
    }

    public void onClick(View v){
    }

    //click item in list to view
    private AdapterView.OnItemClickListener lsvItemClickListener =
            new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    Intent i = new Intent(getContext(), BucketListViewItemActivity.class);
                    BucketListItem item = itemsList.get(position);
                    int iID = item.getID();
                    String iName = item.getItemName();
                    String iDate = item.getItemDate();
                    String iLocal = item.getLocal();
                    int iDiff = item.getDiff();
                    i.putExtra("ViewItemID", iID);
                    i.putExtra("ViewItemName", iName);
                    i.putExtra("ViewItemDate", iDate);
                    i.putExtra("ViewItemLocal", iLocal);
                    i.putExtra("ViewItemDiff", iDiff);
                    i.putExtra("ViewItemComp", item.isComplete());
                    i.putExtra("ViewItemPos", position);
                    startActivity(i);
                }
            };

    public void updateList(){
        BucketListDataBaseHandler handler = new BucketListDataBaseHandler(getContext());
        itemsList  = handler.getList(0);
        blAdapter = new BucketListArrayAdapter(this.getContext(), itemsList);
        listViewItems.setAdapter(blAdapter);
        listViewItems.setOnItemClickListener(lsvItemClickListener);//select list items
        if ((itemsList.size()%2) == 0){
            getActivity().findViewById(R.id.nsv_to_do).setBackgroundResource(R.color.oddListBackground);
        }
        else{
            getActivity().findViewById(R.id.nsv_to_do).setBackgroundResource(R.color.evenListBackground);
        }

    }
}