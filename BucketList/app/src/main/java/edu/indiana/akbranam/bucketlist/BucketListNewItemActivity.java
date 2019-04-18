package edu.indiana.akbranam.bucketlist;

/**
 * Created by Anna Branam
 * Created on 2/21/2017.
 * Last Modified by: Anna Branam
 * Last Modified on: 3/3/2017
 */


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class BucketListNewItemActivity extends Activity implements OnClickListener{

    //TODO Override onCreate
    private SeekBar diffMeter;
    private TextView diffText;
    private EditText itemNameText, itemDateText, itemLocalText;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.content_bucket_list_new_item);//set layout

       //button setup
       FloatingActionButton fabYes = (FloatingActionButton)findViewById(R.id.fab_accept_new_item);
       FloatingActionButton fabNo = (FloatingActionButton)findViewById(R.id.fab_cancel_new_item);
       View dateButton = findViewById(R.id.btn_pick_date_new_item);
       fabYes.setOnClickListener(this);
       fabNo.setOnClickListener(this);
       dateButton.setOnClickListener(this);

       //information setup
       diffMeter = (SeekBar)findViewById(R.id.skbr_diff_meter_new_item);
       diffText = (TextView) findViewById(R.id.txt_diff_new_item);
       itemNameText = (EditText)findViewById(R.id.etxt_name_new_item);
       itemDateText = (EditText)findViewById(R.id.etxt_date_new_item);
       itemLocalText = (EditText)findViewById(R.id.etxt_local_new_item);
       diffMeter.setOnSeekBarChangeListener(diffSeekBarListener);



   }

   public void onClick(View v){
      //TODO add switch case
      switch (v.getId()){
         case R.id.fab_accept_new_item:
             BucketListDataBaseHandler handler = new BucketListDataBaseHandler(this);
             String iName = itemNameText.getText().toString();
             String iDate = itemDateText.getText().toString();
             String iLocal = itemLocalText.getText().toString();
             int iDiff = diffMeter.getProgress();
             if (iName.equals("")){
                 Toast.makeText(this, getString(R.string.popup_null_fields), Toast.LENGTH_SHORT).show();
                 break;
             }
             if (iDate.equals("")){iDate = getString(R.string.title_item_date_null);}
             if (iLocal.equals("")){iLocal = getString(R.string.title_item_local_null);}
             BucketListItem item = new BucketListItem(iName, iDate, iDiff, iLocal, 0);
             handler.addItem(item);
             Toast.makeText(this, getString(R.string.popup_added), Toast.LENGTH_SHORT).show();

             SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);//get settings
             if (prefs.getBoolean("sound", true)){
                 MediaPlayer mp = MediaPlayer.create(this, R.raw.add_item_sound);//play sound
                 mp.start();
         }
             finish();
             break;
         case R.id.fab_cancel_new_item:
             finish();
             break;
          case R.id.btn_pick_date_new_item:
              Intent i = new Intent(this, BucketListPickDateActivity.class);
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