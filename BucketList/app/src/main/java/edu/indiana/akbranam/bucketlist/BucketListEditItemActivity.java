package edu.indiana.akbranam.bucketlist;

/**
 * Created by: Anna Branam
 * Created on: 3/2/2017
 * Last modified by: Anna Branam
 * Last modified on: 3/3/2017
 */

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.Toast;


public class BucketListEditItemActivity extends Activity implements OnClickListener{

    private SeekBar diffMeter;
    private TextView diffText;
    private EditText itemNameText, itemDateText, itemLocalText;
    private int iID;
    private boolean iComp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_bucket_list_edit_item);

        //button setup
        FloatingActionButton fabYes = (FloatingActionButton)findViewById(R.id.fab_accept_edit_item);
        FloatingActionButton fabNo = (FloatingActionButton)findViewById(R.id.fab_cancel_edit_item);
        View dateButton = findViewById(R.id.btn_pick_date_edit_item);
        diffMeter = (SeekBar)findViewById(R.id.skbr_diff_meter_edit_item);
        diffText = (TextView) findViewById(R.id.txt_diff_edit_item);
        itemNameText = (EditText)findViewById(R.id.etxt_name_edit_item);
        itemDateText = (EditText)findViewById(R.id.etxt_date_edit_item);
        itemLocalText = (EditText)findViewById(R.id.etxt_local_edit_item);
        fabYes.setOnClickListener(this);
        fabNo.setOnClickListener(this);
        dateButton.setOnClickListener(this);
        diffMeter.setOnSeekBarChangeListener(diffSeekBarListener);

        //Item information
        String iName="";
        String iDate = "";
        String iLocal="";
        int iDiff =0;
        Bundle extras = getIntent().getExtras();
        if (extras!=null) {
            iID = extras.getInt("ViewItemID");
            iName = extras.getString("ViewItemName");
            iDate = extras.getString("ViewItemDate");
            iLocal = extras.getString("ViewItemLocal");
            iDiff = extras.getInt("ViewItemDiff");
            iComp = extras.getBoolean("ViewItemComp");
        }

        //display item information
        diffMeter.setProgress(iDiff);
        itemNameText.setText(iName);
        itemDateText.setText(iDate);
        itemLocalText.setText(iLocal);
        //if default date/location set to blank
        if (iDate.equals(getString(R.string.title_item_date_null))){itemDateText.setText("");}
        if (iLocal.equals(getString(R.string.title_item_local_null))){itemLocalText.setText("");}

    }

    @Override
    public void onClick(View view) {
        Intent i;
        switch (view.getId()){
            case R.id.fab_accept_edit_item://accept changes and update database
                diffMeter = (SeekBar)findViewById(R.id.skbr_diff_meter_edit_item);
                diffText = (TextView) findViewById(R.id.txt_diff_edit_item);
                itemDateText = (EditText)findViewById(R.id.etxt_date_edit_item);
                itemLocalText = (EditText)findViewById(R.id.etxt_local_edit_item);

                String iName = itemNameText.getText().toString();
                String iDate = itemDateText.getText().toString();
                String iLocal = itemLocalText.getText().toString();
                int iDiff = diffMeter.getProgress();

                if (iName.equals("")){// can't accept null title field
                    Toast.makeText(this, getString(R.string.popup_null_fields), Toast.LENGTH_SHORT).show();
                    break;
                }
                if (iDate.equals("")){iDate = getString(R.string.title_item_date_null);}
                if (iLocal.equals("")){iLocal = getString(R.string.title_item_local_null);;}

                BucketListItem item = new BucketListItem(iName, iDate, iDiff, iLocal, iComp);
                item.setID(iID);
                BucketListDataBaseHandler handler = new BucketListDataBaseHandler(this);
                handler.updateItem(item);
                Toast.makeText(this, getString(R.string.popup_changed), Toast.LENGTH_SHORT).show();
                i = new Intent();
                i.putExtra("itemname", iName);
                i.putExtra("itemdate", iDate);
                i.putExtra("itemlocal", iLocal);
                i.putExtra("itemdiff", iDiff);
                setResult(RESULT_OK, i);

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);//get settings
                if (prefs.getBoolean("sound", true)){
                    MediaPlayer mp = MediaPlayer.create(this, R.raw.change_item_sound);//play sound
                    mp.start();
                }

                finish();
                break;
            case R.id.fab_cancel_edit_item:
                finish();
                break;
            case R.id.btn_pick_date_edit_item://pick date
                i = new Intent(this, BucketListPickDateActivity.class);
                startActivityForResult(i, 1);
                break;

        }

    }
    //update difficulty text based on meter
    private SeekBar.OnSeekBarChangeListener diffSeekBarListener =
            new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    switch(i){
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
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {}

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {}
            };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
                String tmpDate =data.getStringExtra("itemDate");
                itemDateText.setText(tmpDate);
            }
        }
    }
}
