package com.qiqi.babyrecord.activity;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

import com.qiqi.babyrecord.DaoFactory;
import com.qiqi.babyrecord.R;
import com.qiqi.babyrecord.dao.BabyInfo;
import com.qiqi.babyrecord.dao.BabyInfoDao.Properties;
import com.qiqi.babyrecord.util.SharedPreferenceUtils;

public class BabyInfoEditActivity extends BaseActivity implements
        OnDateChangedListener, OnTimeChangedListener {

    public static final String KEY_BABY_ID = "babyId";

    private long babyId;
    private long birthday;

    private EditText editTextName;
    private EditText editTextNickname;
    private EditText editTextBirthPlace;
    private EditText editTextWeight;
    private EditText editTextRemark;
    private DatePicker datePicker;
    private TimePicker timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_babyinfo_edit);

        editTextName = (EditText) findViewById(R.id.edit_name);
        editTextNickname = (EditText) findViewById(R.id.edit_nickname);
        editTextBirthPlace = (EditText) findViewById(R.id.edit_birthplace);
        editTextWeight = (EditText) findViewById(R.id.edit_weight);
        editTextRemark = (EditText) findViewById(R.id.edit_remark);
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        timePicker = (TimePicker) findViewById(R.id.timePicker);

        babyId = getIntent().getLongExtra(KEY_BABY_ID, -1L);

        initView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_babyinfo_edit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.action_done:
            long rowId = saveBabyInfo();
            if (SharedPreferenceUtils
                    .getCheckedBabyInfoId(getApplicationContext()) == -1) {
                // 第一次使用，新增宝宝信息的情况
                SharedPreferenceUtils.setCheckedBabyInfoId(
                        getApplicationContext(), rowId);
                startActivity(new Intent(BabyInfoEditActivity.this,
                        MainActivity.class));
            } else if (babyId == -1) {
                // 新增宝宝信息的情况
                SharedPreferenceUtils.setCheckedBabyInfoId(
                        getApplicationContext(), rowId);
                Intent intent = new Intent(BabyInfoEditActivity.this,
                        BabyInfoActivity.class);
                intent.putExtra(BabyInfoActivity.KEY_BABY_ID, rowId);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
            finish();
            break;

        default:
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView() {

        Calendar calendar = Calendar.getInstance(Locale.getDefault());

        if (babyId != -1) {
            List<BabyInfo> list = DaoFactory
                    .getDaoSession(getApplicationContext()).getBabyInfoDao()
                    .queryBuilder().where(Properties.Id.eq(babyId)).list();
            if (list != null && list.size() > 0) {
                BabyInfo babyInfo = list.get(0);
                editTextName.setText(babyInfo.getName());
                editTextNickname.setText(babyInfo.getNickName());
                editTextBirthPlace.setText(babyInfo.getBirthplace());
                editTextWeight.setText(String.valueOf(babyInfo.getWeight()));
                editTextRemark.setText(babyInfo.getRemark());

                birthday = babyInfo.getBirthday();
            } else {
                birthday = System.currentTimeMillis();
            }
        } else {
            birthday = System.currentTimeMillis();
        }

        calendar.setTimeInMillis(birthday);
        datePicker.init(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), this);
        timePicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
        timePicker.setCurrentMinute(calendar.get(Calendar.MINUTE));
        timePicker.setIs24HourView(true);
        timePicker.setOnTimeChangedListener(this);
    }

    private long saveBabyInfo() {
        BabyInfo babyInfo = new BabyInfo();
        if (babyId != -1) {
            babyInfo.setId(babyId);
        }
        babyInfo.setName(editTextName.getText().toString().trim());
        babyInfo.setNickName(editTextNickname.getText().toString().trim());
        babyInfo.setBirthplace(editTextBirthPlace.getText().toString().trim());
        babyInfo.setWeight(Integer.valueOf(editTextWeight.getText().toString()
                .trim()));
        babyInfo.setRemark(editTextRemark.getText().toString().trim());
        babyInfo.setBirthday(birthday);

        long rowId = DaoFactory.getDaoSession(getApplicationContext())
                .getBabyInfoDao().insertOrReplace(babyInfo);
        return rowId;
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear,
            int dayOfMonth) {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTimeInMillis(birthday);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, monthOfYear);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        birthday = calendar.getTimeInMillis();
    }

    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTimeInMillis(birthday);
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        birthday = calendar.getTimeInMillis();
    }
}
