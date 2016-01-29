package com.android.shopping.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.android.shopping.utils.Logger;

public class MyDBHelper extends DBHelper {
    public static final String DB_NAME = "shop.db";
    public static int DB_VERSION = 1;
    public static final String TB_GOODS = "tb_goods";
    public static final String TB_CLAIM = "tb_claim";

    static MyDBHelper sHelper = null;

    public static MyDBHelper create(Context context) {
        if (sHelper == null) {
            sHelper = new MyDBHelper(context);
        }
        return sHelper;
    }

    public MyDBHelper(Context context) {
        super(context, DB_NAME, DB_VERSION);
    }

    @Override
    public int getDbCurrentVersion() {
        return DB_VERSION;
    }

    @Override
    public String getDbName() {
        return DB_NAME;
    }

    @Override
    public void onCreateTables(SQLiteDatabase db) {
        db.execSQL("CREATE table IF NOT EXISTS " + TB_GOODS
                + " (id TEXT PRIMARY KEY,code TEXT, name TEXT,category INTEGER DEFAULT 0," +
                "price INTEGER DEFAULT 0,desc TEXT,resid INTEGER DEFAULT 0,num INTEGER DEFAULT 0," +
                "score INTEGER DEFAULT 0,progress INTEGER DEFAULT 0,precent TEXT,hot INTEGER DEFAULT 0,point INTEGER DEFAULT 0," +
                "starttime Long DEFAULT 0, endtime Long DEFAULT 0,period Long DEFAULT 0, descurl TEXT,claimed INTEGER DEFAULT 0);");

        db.execSQL("CREATE table IF NOT EXISTS " + TB_CLAIM
                + " (id TEXT PRIMARY KEY,code TEXT, name TEXT,category INTEGER DEFAULT 0," +
                "price INTEGER DEFAULT 0,desc TEXT,resid INTEGER DEFAULT 0,num INTEGER DEFAULT 0," +
                "score INTEGER DEFAULT 0,progress INTEGER DEFAULT 0,precent TEXT,hot INTEGER DEFAULT 0,point INTEGER DEFAULT 0," +
                "starttime Long DEFAULT 0, endtime Long DEFAULT 0,period Long DEFAULT 0, descurl TEXT,claimed INTEGER DEFAULT 0);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != DB_VERSION) {
            db.execSQL("DROP TABLE IF EXISTS " + TB_GOODS);
            db.execSQL("DROP TABLE IF EXISTS " + TB_CLAIM);
            onCreate(db);
        }
    }
}
