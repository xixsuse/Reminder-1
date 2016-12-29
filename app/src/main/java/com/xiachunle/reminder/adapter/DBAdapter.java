package com.xiachunle.reminder.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.xiachunle.reminder.bean.MemoReminders;
import com.xiachunle.reminder.db.DBHelper;

import java.util.ArrayList;



/**
 * Created by xiachunle on 2016/12/6.
 */

public class DBAdapter {
    public final static String DB_NAME = "memo.db";
    public static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "table_reminders";
    public static final String ID = "_id";
    public static final String RealID="RealID";
    public static final String DETAILS = "details";
    public static final String IMPORTANT = "important";

    public static final String TIME = "createTime";
    public static final String DATAS="imageDatas";

    public static final String CREATE_TABLE = "create table if not exists " + TABLE_NAME + " ( " + ID
            + " integer primary key autoincrement, "+RealID +" integer,"+ DETAILS + " text, " + IMPORTANT + " Integer, "
            + TIME + " text, " + DATAS+" blob"+ ");";


    private DBHelper dbHelper;
    private Context mContext;
    private SQLiteDatabase db;

    public DBAdapter(Context mContext) {

        this.mContext = mContext;
    }

    public void open() {
        dbHelper = new DBHelper(mContext);
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        if (dbHelper != null) {
            dbHelper.close();
        }
    }

    //根据数据库字段增加数据库内容

    public void addReminder(String msg, byte[] datas , boolean important, String time) {
        ContentValues values = new ContentValues();
        values.put(DETAILS, msg);
        values.put(IMPORTANT, important ? 1 : 0);
        values.put(TIME, time);
        values.put(DATAS, datas);

        int realId=getRealId();
        values.put(RealID,realId);
        db.insert(TABLE_NAME, null, values);

    }

    public void addReminder(MemoReminders reminders) {
        ContentValues values = new ContentValues();
        values.put(DETAILS, reminders.getmContent());
        values.put(IMPORTANT, reminders.getmFlag());
        values.put(TIME, reminders.getCreateTime());
        values.put(DATAS, reminders.getImageDatas());

        int realId=getRealId();
        values.put(RealID,realId);
        db.insert(TABLE_NAME, null, values);

    }




    //根据RealID查询
    public MemoReminders fetchReminderById(int id) {

        Cursor cursor =db.query(TABLE_NAME, new String[]{
                        ID,DETAILS, IMPORTANT, TIME, DATAS},
                    RealID+ "=?",new String[]{String.valueOf(id)},null,null,null,null);
        MemoReminders reminders = null;
        if (cursor != null) {
            cursor.moveToFirst();
        }

        reminders = new MemoReminders(cursor.getInt(0),
                cursor.getString(1), cursor.getInt(2), cursor.getString(3),
                cursor.getBlob(4));
        cursor.close();
        return reminders;
    }

    public ArrayList<MemoReminders> fetchAllReminders() {
        ArrayList<MemoReminders> lists = new ArrayList<>();
        MemoReminders reminders;
        Cursor cursor = db.query(TABLE_NAME, new String[]{
                        ID, DETAILS, IMPORTANT, TIME, DATAS},
                null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                reminders = new MemoReminders(cursor.getInt(0),
                        cursor.getString(1), cursor.getInt(2), cursor.getString(3),
                        cursor.getBlob(4));
                lists.add(reminders);

            }
        }
        cursor.close();

        return lists;
    }

    public void updatReminder(MemoReminders reminders) {

        ContentValues values = new ContentValues();
        values.put(ID, reminders.getmId());
        values.put(DETAILS, reminders.getmContent());
        values.put(IMPORTANT, reminders.getmFlag());
        values.put(TIME, reminders.getCreateTime());
        values.put(DATAS, reminders.getImageDatas());
        db.update(TABLE_NAME, values, ID + "=?", new String[]{String.valueOf(reminders.getmId())});

    }

    public void deleteReminderById(int id) {
        db.delete(TABLE_NAME, ID + "=?", new String[]{String.valueOf(id)});

    }

    public void deleteAllReminder() {
        db.delete(TABLE_NAME, null, null);

    }
    public void deleteReminderByRealId(int id) {
        db.delete(TABLE_NAME, RealID + "=?", new String[]{String.valueOf(id)});

    }

    private int getRealId() {
        Cursor cursor=select();
        int realId=1;
        if(!cursor.moveToFirst()){
            return realId;
        }else {
            while (true){
                if(realId!=cursor.getInt(1)){
                    return realId;
                }
                else
                {
                    realId++;
                    if(!cursor.moveToNext())
                        return realId;
                }
            }
        }
    }


    /**
     * 设置真正的ID并升序排序
     * @return
     */
    public Cursor select(){

        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, RealID +" ASC");
        return cursor;

    }

    public void updataRealId(){
        ArrayList<MemoReminders> list=fetchAllReminders();
        for(int i=0;i<list.size();i++){
            Log.e("All","Alll:"+i);
            db.execSQL("update "+TABLE_NAME+" set "+RealID+" = "+(i+1)+
            " where "+ID+" = "+list.get(i).getmId());
        }
    }
}
