package edu.indiana.akbranam.bucketlist;

/**BucketListHomeFragment.java: java file for home screen fragment;
 * used in BucketListMainActivity
 * Created by Anna Branam
 * Created on 2/21/2017.
 * Last Modified by: Anna Branam
 * Last Modified on: 3/3/2017
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class BucketListHomeFragment extends Fragment implements OnClickListener{

    private ArrayList<BucketListItem> tryList;

    public BucketListHomeFragment() {
    }

    public static BucketListHomeFragment newInstance() {
        BucketListHomeFragment fragment = new BucketListHomeFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_bucket_list_main, container, false);
        View newB = rootView.findViewById(R.id.btn_new_home);
        newB.setOnClickListener(this);
        View tryB = rootView.findViewById(R.id.btn_try_home);
        tryB.setOnClickListener(this);
        View aboutB = rootView.findViewById(R.id.btn_about_home);
        aboutB.setOnClickListener(this);
        View exitB = rootView.findViewById(R.id.btn_exit_home);
        exitB.setOnClickListener(this);


        return rootView;
    }

    public void onClick(View v){
        Intent i;
        switch(v.getId()){
            case R.id.btn_new_home:
                i = new Intent(getActivity(), BucketListNewItemActivity.class);
                startActivity(i);
                break;
            case R.id.btn_try_home:
                //randomly view activity

                    Random rn = new Random();
                    BucketListDataBaseHandler handler = new BucketListDataBaseHandler(getContext());
                    tryList = handler.getList(0);
                if (tryList.size()>0) {
                    int position = rn.nextInt(tryList.size());
                    BucketListItem item = tryList.get(position);
                    i = new Intent(getContext(), BucketListViewItemActivity.class);
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
                else{
                    Toast.makeText(getContext(), R.string.toast_add, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_about_home:
                i = new Intent(getActivity(), BucketListAboutActivity.class);
                startActivity(i);
                break;
            case R.id.btn_exit_home:
                getActivity().finish();//make sure this exits app
                break;
        }
    }

}
