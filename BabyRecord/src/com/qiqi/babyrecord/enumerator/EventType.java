package com.qiqi.babyrecord.enumerator;

import com.qiqi.babyrecord.R;

/**
 * @author qiqifushun@163.com
 * 
 *         2014-10-10 下午3:50:12
 * 
 */
public enum EventType {

    BREAST_FEEDING("哺乳", R.drawable.ic_event_breast_feeding, ""), BOTTLE_BREAST_FEEDING(
            "瓶喂母乳", R.drawable.ic_event_bottle_breast_feeding, "ml"), MILK_FEEDING(
            "奶粉", R.drawable.ic_event_milk_feeding, "ml"), WATER_FEEDING("喂水",
            R.drawable.ic_event_water_feeding, "ml"), SLEEP("睡觉",
            R.drawable.ic_event_sleep, ""), TEMPERATURE("体温",
            R.drawable.ic_event_temperature, "℃"), DIAPER("换尿布",
            R.drawable.ic_event_diaper, ""), XUXU("尿尿",
            R.drawable.ic_event_xuxu, ""), BIANBIAN("便便",
            R.drawable.ic_event_bianbian, ""), GAME("游戏",
            R.drawable.ic_event_game, ""), TAKE_MEDICINE("吃药",
            R.drawable.ic_event_take_medicine, "颗,粒,勺,克,ml"), BATH("洗澡",
            R.drawable.ic_event_bath, ""), MILESTONE("成长事件",
            R.drawable.ic_event_milestone, ""), CUSTOMER("自定义",
            R.drawable.ic_event_customer, "");

    private String name;
    private int resId;
    private String unit;

    private EventType(String name, int resId, String unit) {
        this.name = name;
        this.resId = resId;
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public static EventType getByName(String name) {
        for (EventType eventType : EventType.values()) {
            if (eventType.getName().equals(name)) {
                return eventType;
            }
        }
        return null;
    }
}
