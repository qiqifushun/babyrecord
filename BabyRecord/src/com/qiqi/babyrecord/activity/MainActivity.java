package com.qiqi.babyrecord.activity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.OnNavigationListener;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qiqi.babyrecord.DaoFactory;
import com.qiqi.babyrecord.R;
import com.qiqi.babyrecord.dao.BabyInfo;
import com.qiqi.babyrecord.fragment.EventListFragment;
import com.qiqi.babyrecord.util.BabyInfoUtils;
import com.qiqi.babyrecord.util.SharedPreferenceUtils;

public class MainActivity extends ActionBarActivity implements
        OnNavigationListener, OnPageChangeListener {

    private final int MIDDLE = 12;

    private List<BabyInfo> list;
    private long babyId;

    private long selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        selectedDate = System.currentTimeMillis();
        initActionBar();
    }

    private void initActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
    }

    @Override
    protected void onStart() {
        super.onStart();
        refreshActionbar();

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(new EventFragmentPagerAdapter(
                getSupportFragmentManager()));
        viewPager.setOnPageChangeListener(this);
        viewPager.setCurrentItem(getFragmentIndex(selectedDate));
    }

    public void refreshActionbar() {
        ActionBar actionBar = getSupportActionBar();

        list = DaoFactory.getDaoSession(getApplicationContext())
                .getBabyInfoDao().queryBuilder().list();

        if (list != null && list.size() > 0) {
            actionBar.setListNavigationCallbacks(new BabyAdapter(
                    getApplicationContext()), this);

            babyId = SharedPreferenceUtils
                    .getCheckedBabyInfoId(getApplicationContext());
            int index = getPositionFromId(babyId);
            actionBar.setSelectedNavigationItem(index);
        } else {
            MainActivity.this.finish();
        }

    }

    public int getPositionFromId(long id) {
        for (int i = 0, size = list.size(); i < size; i++) {
            BabyInfo babyInfo = list.get(i);
            if (babyInfo.getId() == id) {
                return i;
            }
        }
        return 0;
    }

    private long getFragmentTime(int index) {
        return System.currentTimeMillis() - (MIDDLE - index)
                * BabyInfoUtils.DAY;
    }

    private int getFragmentIndex(long selectedDate) {
        return MIDDLE
                - (int) ((System.currentTimeMillis() - selectedDate) / BabyInfoUtils.DAY);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

        case R.id.action_new:
            Intent intentNewEvent = new Intent(MainActivity.this,
                    NewEventActivity.class);
            intentNewEvent.putExtra(NewEventActivity.KEY_BABY_ID, babyId);
            intentNewEvent.putExtra(NewEventActivity.KEY_SELECTED_DATE,
                    selectedDate);
            startActivity(intentNewEvent);
            break;

        case R.id.action_menu_info:
            Intent intentBabyInfo = new Intent(MainActivity.this,
                    BabyInfoActivity.class);
            intentBabyInfo.putExtra(BabyInfoActivity.KEY_BABY_ID, babyId);
            startActivity(intentBabyInfo);
            break;

        case R.id.action_menu_chart:
            // TODO
            break;

        case R.id.action_menu_about:
            // TODO
            break;

        default:
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(int positon, long id) {
        babyId = id;
        SharedPreferenceUtils.setCheckedBabyInfoId(getApplicationContext(),
                babyId);
        return false;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset,
            int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        selectedDate = getFragmentTime(position);
        refreshActionbar();
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    class BabyAdapter extends BaseAdapter {

        private Context context;

        public BabyAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return ((BabyInfo) getItem(position)).getId();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                Holder holder = new Holder();
                convertView = LayoutInflater.from(context).inflate(
                        R.layout.list_item_baby_info, parent, false);
                holder.name = (TextView) convertView
                        .findViewById(R.id.baby_name);
                holder.age = (TextView) convertView.findViewById(R.id.baby_age);
                convertView.setTag(holder);
            }

            bindView(convertView, position);

            return convertView;
        }

        private void bindView(View convertView, int position) {
            Holder holder = (Holder) convertView.getTag();
            BabyInfo babyInfo = (BabyInfo) getItem(position);

            StringBuffer stringBuffer = new StringBuffer();
            if (!TextUtils.isEmpty(babyInfo.getName())) {
                stringBuffer.append(babyInfo.getName());
                if (!TextUtils.isEmpty(babyInfo.getNickName())) {
                    stringBuffer.append("(");
                    stringBuffer.append(babyInfo.getNickName());
                    stringBuffer.append(")");
                }
            } else {
                if (!TextUtils.isEmpty(babyInfo.getNickName())) {
                    stringBuffer.append(babyInfo.getNickName());
                }
            }
            holder.name.setText(stringBuffer.toString());

            String date = new SimpleDateFormat("yyyy.MM.dd",
                    Locale.getDefault()).format(new Date(selectedDate));
            String age = "("
                    + BabyInfoUtils
                            .getAge(selectedDate, babyInfo.getBirthday()) + ")";
            holder.age.setText(date + age);
        }

        class Holder {
            TextView name;
            TextView age;
        }
    }

    class EventFragmentPagerAdapter extends FragmentStatePagerAdapter {

        public EventFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return EventListFragment.newInstance(babyId,
                    getFragmentTime(position));
        }

        @Override
        public int getCount() {
            return MIDDLE * 2;
        }
    }

}
