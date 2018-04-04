package com.elang.wcdb;

import android.content.Context;
import android.util.Log;

import com.elang.wcdb.statement.StatementResult;
import com.mvp.rxandroid.app.MvpApp;
import com.tencent.wcdb.Cursor;
import com.tencent.wcdb.database.SQLiteDatabase;
import com.tencent.wcdb.database.SQLiteOpenHelper;
import com.tencent.wcdb.database.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhuMH on 18/3/27.
 */

public class SqlStatementManager {

    static Helper helper;

    public static void testWCDB(){
        ISqlStatement iSqlStatement = getSqlStatement();
        StatementResult result = null;
        try {
            result = iSqlStatement.createTab(UserInfo.class.newInstance());
            SQLiteStatement statement = getDb().compileStatement(result.getSqlString());
            statement.execute();

            result = iSqlStatement.insertData(new UserInfo("100","张100","100"));
            statement = getDb().compileStatement(result.getSqlString());
            statement.bindAllArgsAsStrings(result.getAllArgs());
            statement.executeInsert();
            statement.clearBindings();

            result = iSqlStatement.queryData(UserInfo.class.newInstance());
            statement = getDb().compileStatement(result.getSqlString());
            statement.bindAllArgsAsStrings(result.getAllArgs());
            String sql = statement.simpleQueryForString();
            Cursor cursor = getDb().rawQuery(sql,null);

            List<UserInfo> userInfos = iSqlStatement.gsonCursor(UserInfo.class.newInstance(),cursor);
            Log.e("elang","userInfos : " + userInfos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ISqlStatement getSqlStatement(){
        return SQLStatementImpl.initSqlStatement();
    }

    public static List<UserInfo> getUsers(){
        List<UserInfo> userInfos = new ArrayList<>();
        userInfos.add(new UserInfo("1","张一","10"));
        userInfos.add(new UserInfo("2","张二","10"));
        userInfos.add(new UserInfo("3","张三","10"));
        userInfos.add(new UserInfo("4","张四","10"));
        userInfos.add(new UserInfo("5","张五","10"));
        userInfos.add(new UserInfo("6","张六","10"));
        return userInfos;
    }

    public static SQLiteDatabase getDb(){
        if (helper == null){
            helper = new Helper(MvpApp.mvpApp,"test.db",null,1);
        }
        SQLiteDatabase db = helper.getWritableDatabase();
        return db;
    }

    static class Helper extends SQLiteOpenHelper {

        public Helper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

}
