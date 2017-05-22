package comlh.example.lenovo.myapplication.db.dao;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import comlh.example.lenovo.myapplication.db.Base.BaseDao;
import comlh.example.lenovo.myapplication.db.CustomSqlOpenHelper;

/**
 * Created by Justinliu on 2017/5/19.
 */

public class BaseDaoFactory {
    private static List<Class> list = new ArrayList<>();
    private static Map<Class, BaseDao> map = new HashMap<>();

    public static <T extends BaseDao<M>, M> T getBaseDao(Class<T> baseDaoClass, Class<M> baseClass, Context context) {
        try {
            if (list.contains(baseDaoClass)) {
                return (T) map.get(baseDaoClass);
            }
            BaseDao<M> base = baseDaoClass.newInstance();
            base.init(baseClass, CustomSqlOpenHelper.getSqlInstance(context));
            list.add(baseDaoClass);
            map.put(baseDaoClass, base);
            return (T) base;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }


}
