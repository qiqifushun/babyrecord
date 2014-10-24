package com.qiqi.babyrecord;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.qiqi.babyrecord.dao.DaoMaster;
import com.qiqi.babyrecord.dao.DaoMaster.DevOpenHelper;
import com.qiqi.babyrecord.dao.DaoSession;

public class DaoFactory {

    private static DaoSession mDaoSession;

    public synchronized static DaoSession getDaoSession(Context context) {
        if (mDaoSession == null) {
            DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(context,
                    "babyrecord.db", null);
            SQLiteDatabase db = devOpenHelper.getWritableDatabase();
            mDaoSession = new DaoMaster(db).newSession();
        }
        return mDaoSession;
    }
}
