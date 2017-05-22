package comlh.example.lenovo.myapplication.db.bean.TableItem;

import comlh.example.lenovo.myapplication.db.Annotation.DBColum;
import comlh.example.lenovo.myapplication.db.Annotation.DBTable;

/**
 * Created by Justinliu on 2017/5/19.
 */

@DBTable("user")
public class User {
    @DBColum("name")
    private String name;


    @DBColum("password")
    private String password;

    public User() {
    }

    public User(String name, String password) {

        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassWord() {
        return password;
    }

    public void setPassWord(String passWord) {
        this.password = passWord;
    }
}
