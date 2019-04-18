package edu.indiana.akbranam.bucketlist;

/**BucketListMainActivity.java: java file for the app's main activity; uses 3 fragments
 * Created by Anna Branam
 * Created on 2/21/2017.
 * Last Modified by: Anna Branam
 * Last Modified on: 3/3/2017
 */

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BucketListMainActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    private BucketListCompletedFragment frag2;
    private BucketListToDoFragment frag1;
    private BucketListHomeFragment frag0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bucket_list_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabMain = (TabLayout) findViewById(R.id.sliding_tabs_main);
        tabMain.setupWithViewPager(mViewPager);
        tabMain.getTabAt(0).setText(getString(R.string.title_home_main));
        tabMain.getTabAt(1).setText(getString(R.string.title_todo_main));
        tabMain.getTabAt(2).setText(getString(R.string.title_complete_main));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bucket_list_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch ((item.getItemId())){
            case R.id.settings_menu:
                startActivity(new Intent(this, BucketListPrefsActivity.class));
                return true;
        }
        return false;
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position){
                case 2://completed list
                    frag2 = BucketListCompletedFragment.newInstance();
                    int wait = 0;
                    return frag2;
                case 1://to-do list screen
                    frag1 = BucketListToDoFragment.newInstance();
                    return frag1;
                case 0:
                default://home screen
                    frag0 = BucketListHomeFragment.newInstance();
                    return frag0;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }
}
