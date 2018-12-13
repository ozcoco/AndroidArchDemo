package org.oz.demo.sql.converter;


import androidx.room.TypeConverter;

import java.util.Date;

/**
 * @author oz
 * @version 1.0
 * @name Converters
 * @package com.donkeylive.qw.sql.converter
 * @time 23/04/18 23:40
 * @email 857527916@qq.com
 * @describe flied converter
 */

public class Converters {

    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

}
