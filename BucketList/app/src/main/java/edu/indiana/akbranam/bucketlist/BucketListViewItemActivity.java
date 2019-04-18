package edu.indiana.akbranam.bucketlist;

/** BucketListViewItemActivity.java: java file for view item screen
 Created by Anna Branam
 * Created on 2/21/2017.
 * Last Modified by: Anna Branam
 * Last Modified on: 3/3/2017
 */

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class BucketListViewItemActivity extends Activity implements OnClickListener{

    //variables
    private int iID;
    private boolean iComp;
    private String iName="";
    private String iDate = "";
    private String iLocal="";
    private int iDiff =0;
    private int iPos = 0;
    private TextView nameText, localText, dateText, diffText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_bucket_list_view_item);
        Bundle extras = getIntent().getExtras();
        if (extras!=null) {//get item information
            iID = extras.getInt("ViewItemID");
            iName = extras.getString("ViewItemName");
            iDate = extras.getString("ViewItemDate");
            iLocal = extras.getString("ViewItemLocal");
            iDiff = extras.getInt("ViewItemDiff");
            iComp = extras.getBoolean("ViewItemComp");
            iPos = extras.getInt("ViewItemPos");
        }
        if ((iPos%2) == 0){//set background color
            findViewById(R.id.ll_view_item).setBackgroundResource(R.color.evenListBackground);
        }
        else{
            findViewById(R.id.ll_view_item).setBackgroundResource(R.color.oddListBackground);
        }
        //setup TextViews
        nameText = (TextView)findViewById(R.id.txt_name_view_item);
        localText = (TextView)findViewById(R.id.txt_local_view_item);
        dateText = (TextView)findViewById(R.id.txt_date_view_item);
        diffText = (TextView)findViewById(R.id.txt_diff_view_item);
        //Set information
        switch(iDiff){//set difficulty text
            case 0:
                diffText.setText(getString(R.string.diff_easy));
                break;
            case 1:
                diffText.setText(getString(R.string.diff_medium));
                break;
            case 2:
                diffText.setText(getString(R.string.diff_hard));
                break;
        }
        nameText.setText(iName);
        dateText.setText(getString(R.string.label_view_item_date)+" "+iDate);
        localText.setText(getString(R.string.label_view_item_local)+" "+iLocal);
        //setup buttons
        Button tryButton = (Button)findViewById(R.id.btn_try_view_item);
        Button laterButton = (Button)findViewById(R.id.btn_later_view_item);
        FloatingActionButton fabDelete = (FloatingActionButton) findViewById(R.id.fab_delete_view_item);
        FloatingActionButton fabEdit = (FloatingActionButton) findViewById(R.id.fab_edit_view_item);
        fabDelete.setOnClickListener(this);
        fabEdit.setOnClickListener(this);
        tryButton.setOnClickListener(this);
        laterButton.setOnClickListener(this);

        //set button text based on item completion status
        if (iComp){
            tryButton.setText(getString(R.string.button_view_item_try_again));
            laterButton.setText(getString(R.string.button_view_item_back));
        }
        else{
            tryButton.setText(getString(R.string.button_view_item_complete));
            laterButton.setText(getString(R.string.button_view_item_later));
        }
    }

    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.btn_try_view_item:
                //TODO move to completed list (update completed field)
                if (iComp) {

                } else {
                    iComp = true;
                    BucketListItem item = new BucketListItem(iName, iDate, iDiff, iLocal, iComp);
                    item.setID(iID);
                    BucketListDataBaseHandler handler = new BucketListDataBaseHandler(this);
                    handler.updateItem(item);
                }
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);//get settings
                if (prefs.getBoolean("sound", true)){
                    MediaPlayer mp = MediaPlayer.create(this, R.raw.complete_item_sound);//play sound
                    mp.start();
                }
                finish();
                break;
            case R.id.btn_later_view_item://go back
                finish();
                break;
            case R.id.fab_delete_view_item:
                //launch delete action confirmation
                confirm();
                break;
            case R.id.fab_edit_view_item:
                i = new Intent(this, BucketListEditItemActivity.class);
                //send item info to edit screen
                i.putExtra("ViewItemID", iID);
                i.putExtra("ViewItemName", iName);
                i.putExtra("ViewItemDate", iDate);
                i.putExtra("ViewItemLocal", iLocal);
                i.putExtra("ViewItemDiff", iDiff);
                i.putExtra("ViewItemComp", iComp);
                startActivityForResult(i, 1);
                break;
        }
    }

    private void confirmedDelete(){
        BucketListDataBaseHandler handler = new BucketListDataBaseHandler(this);
        handler.deleteItem(iID);//delete item from database
        Toast.makeText(this, getString(R.string.popup_deleted), Toast.LENGTH_SHORT).show();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);//get settings
        if (prefs.getBoolean("sound", true)){
            MediaPlayer mp = MediaPlayer.create(this, R.raw.delete_item_sound);//play sound
            mp.start();
        }
        finish();
    }

    private void confirm(){
        new AlertDialog.Builder(this)
                .setMessage(getString(R.string.popup_confirm))
                .setPositiveButton(R.string.button_yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int bID) {
                        confirmedDelete();
                    }})
                .setNegativeButton(R.string.button_no, null).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {//update item information after edit
            if(resultCode == RESULT_OK){

                iName = data.getStringExtra("itemname");
                iDate = data.getStringExtra("itemdate");
                iLocal = data.getStringExtra("itemlocal");
                iDiff = data.getIntExtra("itemdiff", iDiff);

                nameText.setText(iName);
                dateText.setText(getString(R.string.label_view_item_date)+" "+iDate);
                localText.setText(getString(R.string.label_view_item_local)+" "+iLocal);
                switch(iDiff){//set difficulty text
                    case 0:
                        diffText.setText(getString(R.string.diff_easy));
                        break;
                    case 1:
                        diffText.setText(getString(R.string.diff_medium));
                        break;
                    case 2:
                        diffText.setText(getString(R.string.diff_hard));
                        break;
                }

            }
        }
    }



}
