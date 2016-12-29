package com.xiachunle.reminder.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.xiachunle.reminder.adapter.DBAdapter;
import com.xiachunle.reminder.util.FileUtil;

import java.io.File;


/**
 * Created by xiachunle on 2016/12/6.
 */

public class DBHelper extends SQLiteOpenHelper {


    public DBHelper(Context context){
        super(context, FileUtil.getSDPath()+ File.separator+ DBAdapter.DB_NAME,null,DBAdapter.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
            db.execSQL(DBAdapter.CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+DBAdapter.TABLE_NAME);
        onCreate(db);
    }
}
