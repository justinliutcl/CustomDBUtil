package comlh.example.lenovo.myapplication.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import comlh.example.lenovo.myapplication.db.bean.UserDao;

/**
 * Created by Justinliu on 2017/5/19.
 */

public class CustomSqlOpenHelper extends SQLiteOpenHelper {
    private static final int SQL_VERSION = 1;
    private static final String SQL_NAME = "custom.db";
    private static CustomSqlOpenHelper sHelper= null;

    private CustomSqlOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public static CustomSqlOpenHelper getSqlInstance(Context context){
        if(sHelper == null){
            sHelper = new CustomSqlOpenHelper(context,SQL_NAME,null,SQL_VERSION);
        }
        return sHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UserDao.creatTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
