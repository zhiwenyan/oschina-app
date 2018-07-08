package com.greenfarm.client.base_library.db;

import android.database.sqlite.SQLiteDatabase;

import com.greenfarm.client.base_library.db.curd.QuerySupport;

import java.util.List;


public interface IDaoSupport<T> {
    //初始化下数据库
    void init(SQLiteDatabase sqLiteDatabase, Class<T> clazz);

    // 插入数据
    long insert(T t);

    // 批量插入  检测性能
    void insert(List<T> datas);

    // 获取专门查询的支持类
    QuerySupport<T> querySupport();

    // 按照语句查询


    int delete(String whereClause, String... whereArgs);

    int update(T obj, String whereClause, String... whereArgs);
}
