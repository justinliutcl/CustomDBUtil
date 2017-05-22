package comlh.example.lenovo.myapplication.db.bean;

import comlh.example.lenovo.myapplication.db.Base.BaseDao;
import comlh.example.lenovo.myapplication.db.bean.TableItem.User;

/**
 * Created by Justinliu on 2017/5/19.
 */

public class UserDao extends BaseDao<User> {

    public static String creatTable() {
        return "create table if not exists user (id integer primary key autoincrement,name text,password text)";
    }
}
