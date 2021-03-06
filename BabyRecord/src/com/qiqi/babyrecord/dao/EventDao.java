package com.qiqi.babyrecord.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.qiqi.babyrecord.dao.Event;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table EVENT.
*/
public class EventDao extends AbstractDao<Event, Long> {

    public static final String TABLENAME = "EVENT";

    /**
     * Properties of entity Event.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
        public final static Property Quantity = new Property(2, Integer.class, "quantity", false, "QUANTITY");
        public final static Property Unit = new Property(3, String.class, "unit", false, "UNIT");
        public final static Property StartTime = new Property(4, Long.class, "startTime", false, "START_TIME");
        public final static Property EndTime = new Property(5, Long.class, "endTime", false, "END_TIME");
        public final static Property Remark = new Property(6, String.class, "remark", false, "REMARK");
        public final static Property BabyId = new Property(7, Long.class, "babyId", false, "BABY_ID");
    };


    public EventDao(DaoConfig config) {
        super(config);
    }
    
    public EventDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'EVENT' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'NAME' TEXT," + // 1: name
                "'QUANTITY' INTEGER," + // 2: quantity
                "'UNIT' TEXT," + // 3: unit
                "'START_TIME' INTEGER," + // 4: startTime
                "'END_TIME' INTEGER," + // 5: endTime
                "'REMARK' TEXT," + // 6: remark
                "'BABY_ID' INTEGER);"); // 7: babyId
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'EVENT'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Event entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
 
        Integer quantity = entity.getQuantity();
        if (quantity != null) {
            stmt.bindLong(3, quantity);
        }
 
        String unit = entity.getUnit();
        if (unit != null) {
            stmt.bindString(4, unit);
        }
 
        Long startTime = entity.getStartTime();
        if (startTime != null) {
            stmt.bindLong(5, startTime);
        }
 
        Long endTime = entity.getEndTime();
        if (endTime != null) {
            stmt.bindLong(6, endTime);
        }
 
        String remark = entity.getRemark();
        if (remark != null) {
            stmt.bindString(7, remark);
        }
 
        Long babyId = entity.getBabyId();
        if (babyId != null) {
            stmt.bindLong(8, babyId);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Event readEntity(Cursor cursor, int offset) {
        Event entity = new Event( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // name
            cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2), // quantity
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // unit
            cursor.isNull(offset + 4) ? null : cursor.getLong(offset + 4), // startTime
            cursor.isNull(offset + 5) ? null : cursor.getLong(offset + 5), // endTime
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // remark
            cursor.isNull(offset + 7) ? null : cursor.getLong(offset + 7) // babyId
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Event entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setQuantity(cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2));
        entity.setUnit(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setStartTime(cursor.isNull(offset + 4) ? null : cursor.getLong(offset + 4));
        entity.setEndTime(cursor.isNull(offset + 5) ? null : cursor.getLong(offset + 5));
        entity.setRemark(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setBabyId(cursor.isNull(offset + 7) ? null : cursor.getLong(offset + 7));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Event entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Event entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
