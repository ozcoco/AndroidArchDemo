package org.oz.demo.sql;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.RxRoom;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import org.oz.demo.sql.dao.UserDao;

import java.util.Objects;

/**
 * @Name SQLService
 * @package com.rfid.sql
 * @Author oz
 * @Email 857527916@qq.com
 * @Time 2018/12/10 15:03
 * @Description Room数据库操作实例
 */
public enum SQLService implements ISQLService {

    INSTANCE {
        @NonNull
        @Override
        public UserDao getUserDao() {

            if (appDatabase == null && context != null)
                _init(context);

            return Objects.requireNonNull(appDatabase).userDao();
        }

        private AppDatabase appDatabase;

        private Context context;

        @Override
        public void _init(@NonNull Context context) {

            this.context = context;

            if (appDatabase == null)
                appDatabase = Room.databaseBuilder(context,
                        AppDatabase.class, "AppDB").build();

        }

        @Override
        public void _destroy() {

            if (appDatabase != null)
                appDatabase.close();

            appDatabase = null;

            context = null;

        }


    };

    public static SQLService getInstance() {
        return INSTANCE;
    }

    public static void init(@NonNull Context context) {

        INSTANCE._init(context);
    }

    public static void destroy() {

        INSTANCE._destroy();

    }

    public abstract void _init(@NonNull Context context);

    public abstract void _destroy();


}
