package comlh.example.lenovo.myapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import comlh.example.lenovo.myapplication.db.bean.TableItem.User;
import comlh.example.lenovo.myapplication.db.dao.BaseDaoFactory;
import comlh.example.lenovo.myapplication.db.bean.UserDao;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    int i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
         i=12;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Log.i("asd", "nav_camera: ");
        } else if (id == R.id.nav_gallery) {
            Log.i("asd", "nav_gallery: ");
        } else if (id == R.id.nav_slideshow) {
            Log.i("asd", "nav_slideshow: ");
        } else if (id == R.id.nav_manage) {
            Log.i("asd", "nav_manage: ");
        } else if (id == R.id.nav_share) {
            Log.i("asd", "nav_share: ");
        } else if (id == R.id.nav_send) {
            Log.i("asd", "nav_send: ");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void show(String t){
        Toast.makeText(this,t,Toast.LENGTH_SHORT).show();
    }

    public void show1(View view){
        UserDao userDao = BaseDaoFactory.getBaseDao(UserDao.class,User.class,this);
        List<User>list;
        list = userDao.query();
        for(User user: list){
//                show(user.getName()+user.getPassWord());
            Log.i("asd", "onNavigationItemSelected: "+user.getName()+user.getPassWord());
        }
    }
    public void add(View view){
        UserDao userDao = BaseDaoFactory.getBaseDao(UserDao.class,User.class,this);
        User user =new User();
        user.setName("aaa");
        user.setPassWord("1234567");
        show(userDao.insert(user)+"");
        
    }

    public void delete(View view){
        UserDao userDao = BaseDaoFactory.getBaseDao(UserDao.class,User.class,this);
        User user =new User();
        user.setName("aaa");
        user.setPassWord("1234567");
        show(userDao.delet(user)+"");
    }

    public void update(View view){
        UserDao userDao = BaseDaoFactory.getBaseDao(UserDao.class,User.class,this);
        User user =new User();
        user.setName("aaa");
        user.setPassWord("1234567");
        User user2 =new User();
        user2.setName("aaa2222");
        user2.setPassWord("12345672");
        show(userDao.update(user,user2)+"");
        i++;
    }
}
