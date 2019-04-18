package edu.indiana.akbranam.bucketlist;

/** BucketListPickDateActivity.java: java file for pisck date screen
 * Created by Anna Branam
 * Created on: 3/3/2017
 * Last modified by: Anna Branam
 * Last modified on: 3/3/2017
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;


public class BucketListPickDateActivity extends Activity implements OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_bucket_list_new_item_date);
        View okButton = findViewById(R.id.btn_yes_date);//setup done button
        okButton.setOnClickListener(this);
    }

    public void onClick(View v){//get date from picker and send back
        DatePicker dp = (DatePicker)findViewById(R.id.dtpk_item_new);
        String date = ""+(dp.getMonth()+1)+"/"+dp.getDayOfMonth()+"/"+ dp.getYear();
        Intent i = new Intent();
        i.putExtra("itemDate", date);
        setResult(RESULT_OK, i);
        finish();
    }

}
