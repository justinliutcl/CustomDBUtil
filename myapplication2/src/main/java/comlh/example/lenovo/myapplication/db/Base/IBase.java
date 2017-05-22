package comlh.example.lenovo.myapplication.db.Base;

import java.util.List;

/**
 * Created by Justinliu on 2017/5/19.
 */

public interface IBase<T> {
    long insert(T t);

    int delet(T t);

    int update(T t,T value);

    List<T> query();

    List<T> query(String sql);
}
