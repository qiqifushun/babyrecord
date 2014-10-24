package com.qiqi.babyrecord.activity;

import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.qiqi.babyrecord.DaoFactory;
import com.qiqi.babyrecord.R;
import com.qiqi.babyrecord.dao.BabyInfo;
import com.qiqi.babyrecord.dao.BabyInfoDao.Properties;
import com.qiqi.babyrecord.dao.DaoSession;
import com.qiqi.babyrecord.dao.Event;
import com.qiqi.babyrecord.util.BabyInfoUtils;
import com.qiqi.babyrecord.util.SharedPreferenceUtils;

public class BabyInfoActivity extends BaseActivity {

    public static final String KEY_BABY_ID = "babyId";
    private long babyId;

    private TextView textViewName;
    private TextView textViewNickname;
    private TextView textViewBirthday;
    private TextView textViewBirthPlace;
    private TextView textViewWeight;
    private TextView textViewRemark;
    private ImageView imageViewBaby;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_babyinfo);

        textViewName = (TextView) findViewById(R.id.text_name);
        textViewNickname = (TextView) findViewById(R.id.text_nickname);
        textViewBirthday = (TextView) findViewById(R.id.text_birthday);
        textViewBirthPlace = (TextView) findViewById(R.id.text_birthplace);
        textViewWeight = (TextView) findViewById(R.id.text_weight);
        textViewRemark = (TextView) findViewById(R.id.text_remark);
        imageViewBaby = (ImageView) findViewById(R.id.img_baby);

    }

    @Override
    protected void onStart() {
        super.onStart();

        babyId = getIntent().getLongExtra(KEY_BABY_ID, -1);

        refreshView();
    }

    private void refreshView() {
        List<BabyInfo> list = DaoFactory.getDaoSession(getApplicationContext())
                .getBabyInfoDao().queryBuilder()
                .where(Properties.Id.eq(babyId)).list();
        if (list != null && list.size() > 0) {
            BabyInfo babyInfo = list.get(0);
            textViewName.setText("姓名：" + babyInfo.getName());
            textViewNickname.setText("昵称：" + babyInfo.getNickName());
            textViewBirthday.setText("生日："
                    + BabyInfoUtils.getBirthday(babyInfo.getBirthday()));
            textViewBirthPlace.setText("出生地：" + babyInfo.getBirthplace());
            textViewWeight.setText("体重："
                    + BabyInfoUtils.getWeight(babyInfo.getWeight()));
            textViewRemark.setText("备注：" + babyInfo.getRemark());
            imageViewBaby.setImageBitmap(BabyInfoUtils.getBabyImage(
                    getApplicationContext(), babyInfo.getImagePath()));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_babyinfo, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

        case R.id.action_menu_new:
            Intent intentNew = new Intent(BabyInfoActivity.this,
                    BabyInfoEditActivity.class);
            startActivity(intentNew);
            break;

        case R.id.action_menu_delete:
            new AlertDialog.Builder(BabyInfoActivity.this)
                    .setTitle("删除")
                    .setMessage("确认删除吗？")
                    .setPositiveButton("确认",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                        int which) {
                                    deleteBabyInfo();
                                }
                            }).setNegativeButton("取消", null).create().show();
            break;

        case R.id.action_menu_edit:
            Intent intentEdit = new Intent(BabyInfoActivity.this,
                    BabyInfoEditActivity.class);
            intentEdit.putExtra(BabyInfoEditActivity.KEY_BABY_ID, babyId);
            startActivity(intentEdit);
            break;

        default:
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteBabyInfo() {

        // 删除宝宝信息
        DaoSession daoSession = DaoFactory
                .getDaoSession(getApplicationContext());
        daoSession.getBabyInfoDao().deleteByKey(babyId);
        // 删除宝宝相关的事件信息
        List<Event> events = daoSession
                .getEventDao()
                .queryBuilder()
                .where(com.qiqi.babyrecord.dao.EventDao.Properties.BabyId
                        .eq(babyId)).list();
        daoSession.getEventDao().deleteInTx(events);
        // 选择一个宝宝信息作为默认信息
        List<BabyInfo> list = daoSession.getBabyInfoDao().queryBuilder().list();
        if (list != null && list.size() > 0) {
            babyId = list.get(0).getId();
            SharedPreferenceUtils.setCheckedBabyInfoId(getApplicationContext(),
                    babyId);
        } else {
            startActivity(new Intent(BabyInfoActivity.this,
                    BabyInfoEditActivity.class));
            SharedPreferenceUtils.setCheckedBabyInfoId(getApplicationContext(),
                    -1);
        }
        BabyInfoActivity.this.finish();
    }
}
