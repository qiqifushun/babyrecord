package com.qiqi.babyrecord;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 * @author qiqifushun@163.com
 * 
 *         2014-10-10 下午12:01:52
 * 
 */
public class BabyRecordGenerator {

    private static final int DB_VERSION = 1;

    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(DB_VERSION, "com.qiqi.babyrecord.dao");

        createBabyInfo(schema);
        createEvent(schema);

        new DaoGenerator().generateAll(schema, "../BabyRecord/src");
    }

    private static void createBabyInfo(Schema schema) {
        Entity event = schema.addEntity("BabyInfo");
        event.addIdProperty().primaryKey();
        event.addStringProperty("name");
        event.addStringProperty("nickName");
        event.addLongProperty("birthday");
        event.addStringProperty("birthplace");
        event.addIntProperty("weight");
        event.addStringProperty("remark");
        event.addStringProperty("imagePath");
    }

    private static void createEvent(Schema schema) {
        Entity event = schema.addEntity("Event");
        event.addIdProperty().primaryKey();
        event.addStringProperty("name");
        event.addIntProperty("quantity");
        event.addStringProperty("unit");
        event.addLongProperty("startTime");
        event.addLongProperty("endTime");
        event.addStringProperty("remark");
        event.addLongProperty("babyId");
    }
}
