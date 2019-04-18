package edu.indiana.akbranam.bucketlist;

/**BucketListCompletedFragment.java
 * used in BucketListMainActivity
 * Created by Anna Branam
 * Created on 2/21/2017.
 * Last Modified by: Anna Branam
 * Last Modified on: 3/1/2017
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class BucketListCompletedFragment extends Fragment implements OnClickListener{

    private ListView listViewItems;
    private ArrayList<BucketListItem> completedItemsList;
    private BucketListArrayAdapter blAdapter;

    public BucketListCompletedFragment() {
    }

    public static BucketListCompletedFragment newInstance() {
        BucketListCompletedFragment fragment = new BucketListCompletedFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_bucket_list_completed, container, false);

        BucketListDataBaseHandler handler = new BucketListDataBaseHandler(rootView.getContext());
        completedItemsList = handler.getList(1);
        //initial ListView setup
        blAdapter = new BucketListArrayAdapter(this.getContext(), completedItemsList);
        listViewItems = (ListView) rootView.findViewById(R.id.list_view_completed);
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

    // view item
    private AdapterView.OnItemClickListener lsvItemClickListener =
            new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    Intent i = new Intent(getContext(), BucketListViewItemActivity.class);
                    BucketListItem item = completedItemsList.get(position);
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
        completedItemsList  = handler.getList(1);
        blAdapter = new BucketListArrayAdapter(this.getContext(), completedItemsList);
        listViewItems.setAdapter(blAdapter);
        listViewItems.setOnItemClickListener(lsvItemClickListener);//select list items
        if ((completedItemsList.size()%2) == 0){//set list background color
            getActivity().findViewById(R.id.nsv_complete).setBackgroundResource(R.color.oddListBackground);
        }
        else{
            getActivity().findViewById(R.id.nsv_complete).setBackgroundResource(R.color.evenListBackground);
        }
    }
}