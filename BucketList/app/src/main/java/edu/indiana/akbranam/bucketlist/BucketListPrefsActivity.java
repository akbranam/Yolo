package edu.indiana.akbranam.bucketlist;

/** BucketListPrefsActivity.java: java file for preference activity of app
 * Created by Anna Branam
 * Created on 2/21/2017.
 * Last Modified by: Anna Branam
 * Last Modified on: 2/28/2017
 */

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceActivity;

public class BucketListPrefsActivity extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //take settings features from resource file
        addPreferencesFromResource(R.xml.settings);
    }

}
