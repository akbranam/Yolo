package edu.indiana.akbranam.bucketlist;

/** BucketListItem.java: java class for an item in the App's lists
 * Created by Anna Branam
 * Created on 2/21/2017.
 * Last Modified by: Anna Branam
 * Last Modified on: 2/28/2017
 */

public class BucketListItem {
    //TODO add instance vars (Date)
    private String mItemName;
    private int mID;
    private String mItemDate;
    private int mDiff;
    private String mLocal;
    private boolean mComplete;

    public BucketListItem(String name, String date, int diff, String local, int comp){
        //mID = ID;
        mItemName = name;
        mItemDate = date;
        mDiff = diff;
        mLocal = local;
        if (comp==0){mComplete = false;}
        else{mComplete = true;}
    }

    public BucketListItem(String name, String date, int diff, String local, boolean comp){
        //mID = ID;
        mItemName = name;
        mItemDate = date;
        mDiff = diff;
        mLocal = local;
        mComplete = comp;
    }

    public String getItemName() {
        return mItemName;
    }

    public void setItemName(String name) {
        mItemName = name;
    }

    public int getID() {
        return mID;
    }

    public void setID(int ID) {
        mID = ID;
    }

    public int getDiff() {
        return mDiff;
    }

    public void setDiff(int diff) {
        mDiff = diff;
    }

    public String getLocal() {
        return mLocal;
    }

    public void setLocal(String local) {
        mLocal = local;
    }

    public boolean isComplete() { return mComplete; }

    public void setComplete(boolean complete) { mComplete = complete; }

    public String getItemDate() {
        return mItemDate;
    }

    public void setItemDate(String itemDate) {
        mItemDate = itemDate;
    }
}
