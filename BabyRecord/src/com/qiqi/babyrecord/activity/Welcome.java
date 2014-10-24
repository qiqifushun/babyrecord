package com.qiqi.babyrecord.activity;

import java.util.List;

import com.qiqi.babyrecord.DaoFactory;
import com.qiqi.babyrecord.dao.BabyInfo;
import com.qiqi.babyrecord.dao.BabyInfoDao.Properties;
import com.qiqi.babyrecord.util.SharedPreferenceUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Welcome extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        long babyId = SharedPreferenceUtils
                .getCheckedBabyInfoId(getApplicationContext());
        List<BabyInfo> list = DaoFactory.getDaoSession(getApplicationContext())
                .getBabyInfoDao().queryBuilder()
                .where(Properties.Id.eq(babyId)).list();
        if (list != null && list.size() > 0) {
            startActivity(new Intent(Welcome.this, MainActivity.class));
        } else {
            startActivity(new Intent(Welcome.this, BabyInfoEditActivity.class));
        }
        Welcome.this.finish();
    }
}
