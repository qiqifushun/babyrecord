package com.qiqi.babyrecord.dao;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import com.qiqi.babyrecord.dao.BabyInfo;
import com.qiqi.babyrecord.dao.Event;

import com.qiqi.babyrecord.dao.BabyInfoDao;
import com.qiqi.babyrecord.dao.EventDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig babyInfoDaoConfig;
    private final DaoConfig eventDaoConfig;

    private final BabyInfoDao babyInfoDao;
    private final EventDao eventDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        babyInfoDaoConfig = daoConfigMap.get(BabyInfoDao.class).clone();
        babyInfoDaoConfig.initIdentityScope(type);

        eventDaoConfig = daoConfigMap.get(EventDao.class).clone();
        eventDaoConfig.initIdentityScope(type);

        babyInfoDao = new BabyInfoDao(babyInfoDaoConfig, this);
        eventDao = new EventDao(eventDaoConfig, this);

        registerDao(BabyInfo.class, babyInfoDao);
        registerDao(Event.class, eventDao);
    }
    
    public void clear() {
        babyInfoDaoConfig.getIdentityScope().clear();
        eventDaoConfig.getIdentityScope().clear();
    }

    public BabyInfoDao getBabyInfoDao() {
        return babyInfoDao;
    }

    public EventDao getEventDao() {
        return eventDao;
    }

}
