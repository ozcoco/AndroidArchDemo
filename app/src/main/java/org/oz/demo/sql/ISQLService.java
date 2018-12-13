package org.oz.demo.sql;

import androidx.annotation.NonNull;

import org.oz.demo.sql.dao.UserDao;

public interface ISQLService {

    @NonNull
    UserDao getUserDao();

}
