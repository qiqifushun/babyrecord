package com.qiqi.babyrecord.fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.qiqi.babyrecord.DaoFactory;
import com.qiqi.babyrecord.R;
import com.qiqi.babyrecord.dao.Event;
import com.qiqi.babyrecord.dao.EventDao.Properties;
import com.qiqi.babyrecord.enumerator.EventType;
import com.qiqi.babyrecord.util.BabyInfoUtils;

public class EventListFragment extends Fragment {

    private long babyId;
    private long time;
    private List<Event> list;

    public static EventListFragment newInstance(long babyId, long time) {
        EventListFragment eventListFragment = new EventListFragment();
        Bundle bundle = new Bundle();
        bundle.putLong("babyId", babyId);
        bundle.putLong("time", time);
        eventListFragment.setArguments(bundle);
        return eventListFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            babyId = bundle.getLong("babyId");
            time = bundle.getLong("time");

            Calendar calendar = Calendar.getInstance(Locale.getDefault());
            calendar.setTimeInMillis(time);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            long start = calendar.getTimeInMillis();
            long end = start + BabyInfoUtils.DAY;
            list = DaoFactory
                    .getDaoSession(getActivity())
                    .getEventDao()
                    .queryBuilder()
                    .where(Properties.BabyId.eq(babyId),
                            Properties.StartTime.between(start, end)).list();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event, container, false);
        ListView listView = (ListView) view.findViewById(R.id.list_view);
        listView.setAdapter(new EventListAdapter(getActivity()));
        return view;
    }

    class EventListAdapter extends BaseAdapter {

        private Context context;

        public EventListAdapter(Context context) {
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
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(
                        R.layout.list_item_event, parent, false);
            }

            Event event = (Event) getItem(position);
            ImageView imageView = (ImageView) convertView
                    .findViewById(R.id.image_event);
            TextView textViewEvent = (TextView) convertView
                    .findViewById(R.id.text_event);
            TextView textViewTime = (TextView) convertView
                    .findViewById(R.id.text_time);

            imageView.setImageResource(EventType.getByName(event.getName())
                    .getResId());
            textViewEvent.setText(event.getName() + " " + event.getQuantity()
                    + " " + event.getUnit());
            textViewTime
                    .setText(new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale
                            .getDefault()).format(new Date(event.getStartTime())));
            return convertView;
        }
    }
}
