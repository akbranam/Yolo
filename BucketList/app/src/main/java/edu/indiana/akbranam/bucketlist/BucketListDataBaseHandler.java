package edu.indiana.akbranam.bucketlist;

/**BucketListDataBaseHandler.java: java class to access app's SQL database
 * Created by Anna Branam
 * Created on 2/21/2017.
 * Last Modified by: Anna Branam
 * Last Modified on: 3/3/2017
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ListView;

import java.util.ArrayList;

public class BucketListDataBaseHandler extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "bucket_list_db_1";//"BucketListDataBase1";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_ITEMS = "bucket_list_items_table_1";
    private static final String COLUMN_ITEM_ID = "item_id";
    private static final String COLUMN_ITEM_NAME = "item_name";
    private static final String COLUMN_ITEM_DATE = "item_date";
    private static final String COLUMN_ITEM_DIFFICULTY = "item_diff";
    private static final String COLUMN_ITEM_LOCATION = "item_local";
    private static final String COLUMN_ITEM_COMPLETED = "item_completed";


    //TODO fix constructor
    public BucketListDataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String Create_Contacts_DB = "CREATE TABLE " +
                TABLE_ITEMS + " ("+
                COLUMN_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                COLUMN_ITEM_NAME+" TEXT, "+
                COLUMN_ITEM_DATE + " TEXT, "+
                COLUMN_ITEM_DIFFICULTY+" INTEGER, "+
                COLUMN_ITEM_LOCATION+" TEXT, "+
                COLUMN_ITEM_COMPLETED + " INTEGER)";
        db.execSQL(Create_Contacts_DB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_ITEMS);
        onCreate(db);
    }

    public void addItem(BucketListItem item){
        ContentValues values = new ContentValues();
        values.put(COLUMN_ITEM_NAME, item.getItemName());
        values.put(COLUMN_ITEM_DATE, item.getItemDate());
        values.put(COLUMN_ITEM_DIFFICULTY, item.getDiff());
        values.put(COLUMN_ITEM_LOCATION, item.getLocal());
        values.put(COLUMN_ITEM_COMPLETED, 0);
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_ITEMS, null, values);
        db.close();
    }


    public void updateItem(BucketListItem item){//update item in database
        ContentValues values = new ContentValues();
        values.put(COLUMN_ITEM_NAME, item.getItemName());
        values.put(COLUMN_ITEM_DATE, item.getItemDate());
        values.put(COLUMN_ITEM_DIFFICULTY, item.getDiff());
        values.put(COLUMN_ITEM_LOCATION, item.getLocal());
        if(item.isComplete()){values.put(COLUMN_ITEM_COMPLETED, 1);}
        else{values.put(COLUMN_ITEM_COMPLETED, 0);}
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_ITEMS, values, COLUMN_ITEM_ID+" = "+item.getID(), null);
        db.close();
    }

    public boolean deleteItem(int id){//delete item based on id
        //TODO finish method
        boolean result = false;
        String sqlQ = "SELECT * FROM "+ TABLE_ITEMS +
                " WHERE " + COLUMN_ITEM_ID + " = "+
                id;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor myCur = db.rawQuery(sqlQ, null);
        if (myCur.moveToFirst()){
            int tmpId = myCur.getInt(0);
            db.delete(TABLE_ITEMS, COLUMN_ITEM_ID +" = ?", new String[]{Integer.toString(tmpId)});
            myCur.close();
            result = true;
        }
        db.close();
        return result;
    }

    public ArrayList<BucketListItem> getList(int completed){//takes completed values returns list of items with that value
        SQLiteDatabase db = this.getWritableDatabase();
        String sqlQ = "SELECT * FROM "+TABLE_ITEMS+
                " WHERE "+COLUMN_ITEM_COMPLETED + " = \""+ completed+"\"";
        Cursor myCur = db.rawQuery(sqlQ, null);
        ArrayList<BucketListItem> res = new ArrayList<BucketListItem>();
        BucketListItem item;
        for (int i = 0; i < myCur.getCount(); i++) {//iterate through items in database
            if (myCur.moveToPosition(i)) {
                int tmpId = myCur.getInt(0);
                String tmpName = myCur.getString(1);
                String tmpDate = myCur.getString(2);
                int tmpDiff = myCur.getInt(3);
                String tmpLocal = myCur.getString(4);
                int tmpComp = myCur.getInt(5);
                item = new BucketListItem(tmpName, tmpDate, tmpDiff, tmpLocal, tmpComp);
                item.setID(tmpId);
                res.add(item);
            }
        }
        myCur.close();
        db.close();
        return res;
    }
}
