package com.qiqi.babyrecord.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qiqi.babyrecord.DaoFactory;
import com.qiqi.babyrecord.R;
import com.qiqi.babyrecord.dao.Event;
import com.qiqi.babyrecord.enumerator.EventType;

public class NewEventActivity extends BaseActivity implements
        OnItemClickListener {

    public static final String KEY_BABY_ID = "babyId";
    public static final String KEY_SELECTED_DATE = "selectedDate";
    private long babyId;
    private long selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);

        GridView gridView = (GridView) findViewById(R.id.grid_view);
        gridView.setAdapter(new GridAdapter(getApplicationContext()));
        gridView.setOnItemClickListener(this);

        babyId = getIntent().getLongExtra(KEY_BABY_ID, -1L);
        selectedDate = getIntent().getLongExtra(KEY_SELECTED_DATE, -1L);
    }

    class GridAdapter extends BaseAdapter {

        private Context context;

        public GridAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return EventType.values().length;
        }

        @Override
        public Object getItem(int position) {
            return EventType.values()[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(
                        R.layout.grid_item_event, parent, false);
            }
            ImageView imageView = (ImageView) convertView
                    .findViewById(R.id.event_image);
            EventType eventType = (EventType) getItem(position);
            imageView.setImageResource(eventType.getResId());
            TextView textView = (TextView) convertView
                    .findViewById(R.id.event_name);
            textView.setText(eventType.getName());
            return convertView;
        }
    }

    @Override
    public void onItemClick(final AdapterView<?> parent, View view,
            final int position, long id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(
                NewEventActivity.this);

        View content = LayoutInflater.from(getApplicationContext()).inflate(
                R.layout.event_info, null);
        final Chronometer chronometer = (Chronometer) content
                .findViewById(R.id.chronometer_duration);
        final long startTime = selectedDate;
        chronometer.start();
        final EditText editText = (EditText) content
                .findViewById(R.id.edit_text);
        builder.setView(content);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String quantity = editText.getText().toString().trim();
                if (!TextUtils.isEmpty(quantity)
                        && TextUtils.isDigitsOnly(quantity)) {
                    chronometer.stop();
                    long endTime = selectedDate;
                    Event event = new Event();
                    EventType eventType = (EventType) parent.getAdapter()
                            .getItem(position);
                    event.setName(eventType.getName());
                    event.setQuantity(Integer.valueOf(quantity));
                    event.setStartTime(startTime);
                    event.setEndTime(endTime);
                    event.setBabyId(babyId);
                    DaoFactory.getDaoSession(getApplicationContext())
                            .getEventDao().insert(event);
                    NewEventActivity.this.finish();
                } else {
                    Toast.makeText(getApplicationContext(), "数据不合法！",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("取消", null);
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                chronometer.stop();
            }
        });
        builder.show();
    }
}
