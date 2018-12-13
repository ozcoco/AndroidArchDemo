package org.oz.demo.sql;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import org.oz.demo.po.User;
import org.oz.demo.sql.converter.Converters;
import org.oz.demo.sql.dao.UserDao;


/**
 * @author oz
 * @version 1.0
 * @name AppDatabase
 * @package
 * @time
 * @email 857527916@qq.com
 * @describe TODO
 */


@Database(entities = {User.class}, version = 1/*, exportSchema = false*/)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    /**
     * @Name userDao
     * @Params []
     * @Return com.rfid.sql.dao.UserDao
     * @Author oz
     * @Email 857527916@qq.com
     * @Time 2018/12/10 12:22
     * @Description todo
     */
    public abstract UserDao userDao();


}
