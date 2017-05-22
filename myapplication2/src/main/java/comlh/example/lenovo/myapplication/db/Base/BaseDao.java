package comlh.example.lenovo.myapplication.db.Base;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import comlh.example.lenovo.myapplication.db.Annotation.DBColum;
import comlh.example.lenovo.myapplication.db.Annotation.DBTable;

/**
 * Created by Justinliu on 2017/5/19.
 */

public class BaseDao<T> implements IBase<T> {
    private String mTableName;
    private SQLiteDatabase mReadSqLiteDatabase;
    private SQLiteDatabase mWriteSqLiteDatabase;
    private boolean mIsInit = false;
    private Map<String, Field> mEntryCache;
    private Class<T> mEntryClass;

    public void init(Class<T> base, SQLiteOpenHelper database) {
        if (!mIsInit) {
            mIsInit = true;
            mReadSqLiteDatabase = database.getReadableDatabase();
            mWriteSqLiteDatabase = database.getWritableDatabase();
            mEntryClass = base;
            mEntryCache = new HashMap<>();
            mTableName = getTableName();
            initEntryCache();
        }


    }

    private void initEntryCache() {
        String sql = "select * from " + mTableName + " limit 1,0";
        Cursor cursor = null;
        try {
            cursor = mReadSqLiteDatabase.rawQuery(sql, null);
            String columnName[] = cursor.getColumnNames();
            Field[] fields = mEntryClass.getFields();

            for (Field field : fields) {
                field.setAccessible(true);
            }

            for (int i = 0; i < columnName.length; i++) {
                String column = "";
                Field field = null;
                for (int j = 0; j < fields.length; j++) {
                    if (fields[j].getAnnotation(DBColum.class) == null) {
                        column = fields[j].getName();
                    } else {
                        column = fields[j].getAnnotation(DBColum.class).value();
                    }

                    if (columnName[i].equals(column)) {
                        field = fields[j];
                        break;
                    }
                }
                if (field != null)
                    mEntryCache.put(column, field);
            }
        } catch (Exception e) {

        } finally {
            if (cursor != null)
                cursor.close();
        }


    }


    @Override
    public long insert(T t) {
        Map<String, String> map = getValue(t);
        ContentValues content = getContentValue(map);
        return mWriteSqLiteDatabase.insert(mTableName, null, content);
    }

    @Override
    public int delet(T t) {
        Content content = new Content(getValue(t));
        return mWriteSqLiteDatabase.delete(mTableName, content.getWhere(), content.getWhereArg());
    }

    @Override
    public int update(T tWhere, T value) {
        Map<String, String> map = getValue(tWhere);
        Map<String, String> mapV = getValue(value);
        Content content = new Content(map);
        ContentValues contentValues = getContentValue(mapV);
        return mWriteSqLiteDatabase.update(mTableName, contentValues, content.getWhere(), content.getWhereArg());
    }

    @Override
    public List<T> query() {
        Cursor c = mReadSqLiteDatabase.query(mTableName, null, null, null, null, null, null);
        if (c != null) {
            return getValueList(c);
        }
        return null;
    }

    @Override
    public List<T> query(String sql) {
        return null;
    }

    private List<T> getValueList(Cursor cursor) {
        Log.i("asd", "getValueList");
        List<T> list = new ArrayList<>();
        if (cursor.isClosed()) {
            return list;
        }
        try {
            while (cursor.moveToNext()) {
                String[] columsName = cursor.getColumnNames();
                T item = null;

                item = mEntryClass.newInstance();

                for (String name : columsName) {
                    Field field = mEntryCache.get(name);
                    if (field == null) {
                        continue;
                    }
                    Class type = field.getType();
                    if (type == String.class) {
                        field.set(item, cursor.getString(cursor.getColumnIndex(name)));
                    } else if (type == Integer.class) {
                        field.set(item, cursor.getInt(cursor.getColumnIndex(name)));
                    } else if (type == Double.class) {
                        field.set(item, cursor.getDouble(cursor.getColumnIndex(name)));
                    } else if (type == Long.class) {
                        field.set(item, cursor.getLong(cursor.getColumnIndex(name)));
                    } else if (type == byte[].class) {
                        field.set(item, cursor.getBlob(cursor.getColumnIndex(name)));
                    }
                }
                if (item != null) {
                    list.add(item);
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }
        return list;
    }

    private Map<String, String> getValue(T t) {
        Map<String, String> map = new HashMap<>();
        Field[] fields = t.getClass().getFields();
        Set<Map.Entry<String, Field>> set = mEntryCache.entrySet();
        try {
            for (Map.Entry<String, Field> entry : set) {
                String column = "";
                String value = "";
                for (Field field : fields) {
//                    if (field.getAnnotation(DBColum.class) == null) {
//                        column = field.getName();
//                    } else {
//                        column = field.getAnnotation(DBColum.class).value();
//                    }

                    if (entry.getValue().get(t) == null) {
                        continue;
                    }
                    column = entry.getKey();
                    value = entry.getValue().get(t).toString();
                }
                map.put(column, value);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return map;
    }

    private ContentValues getContentValue(Map<String, String> map) {
        ContentValues values = new ContentValues();
        Set<Map.Entry<String, String>> set = map.entrySet();
        for (Map.Entry<String, String> entry : set) {
            values.put(entry.getKey(), entry.getValue());
        }
        return values;
    }

    public String getTableName() {
        if (mEntryClass.getAnnotation(DBTable.class) == null) {
            return mEntryClass.getSimpleName();
        }
        return mEntryClass.getAnnotation(DBTable.class).value();
    }

    private class Content {
        Map<String, String> map;
        String where;
        String[] whereArg;

        public Content(Map<String, String> map) {
            this.map = map;
            StringBuilder builder = new StringBuilder();
            builder.append(" 1=1");
            Set<Map.Entry<String, String>> entrySet = map.entrySet();
            whereArg = new String[entrySet.size()];
            int i = 0;
            for (Map.Entry<String, String> entry : entrySet) {
                builder.append(" and " + entry.getKey() + "=?");
                whereArg[i] = entry.getValue();
                i++;
            }
            where = builder.toString();
        }

        public String getWhere() {
            return where;
        }

        public String[] getWhereArg() {
            return whereArg;
        }
    }
}
