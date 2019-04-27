package com.example.shereen.dipolmafinalproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static String Tag="Check product";
    public  static ArrayList<Product> products_lists;
    public static Toolbar toolbar;
    SharedPreferences sharedPreferences;
    String sharedPrefName = "Login";
    String user_details = "user_Details";
    public static LinearLayout searchbar;
    public static TextView title;
    public static String user_Email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        searchbar = (LinearLayout) findViewById(R.id.searchbar);
        title= (TextView) findViewById(R.id.title);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);

        sharedPreferences = getSharedPreferences(user_details,MODE_PRIVATE);
        user_Email = sharedPreferences.getString("email", "");
        TextView username_profile = (TextView) headerView.findViewById(R.id.usernameaccount);
        sqlLiteHelper sqlLiteHelper = new sqlLiteHelper(HomeActivity.this);
        User user = sqlLiteHelper.get_User(user_Email);
        String username = user.name;
        username_profile.setText(username);

        // set name of user
        username_profile.setText(username);
        // open user account
        username_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentmanager = getSupportFragmentManager();
                FragmentTransaction fragmenttranscation = fragmentmanager.beginTransaction().addToBackStack(null);
                UserAccountFragment fragment_user = new UserAccountFragment();
                fragmenttranscation.replace(R.id.fragment,fragment_user,"UserAccountFragment");
                fragmenttranscation.commit();
                // set title of actionbar

                searchbar.setVisibility(View.INVISIBLE);
                title.setVisibility(View.VISIBLE);
                title.setText("Profile");



                // close side menue
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });

       // get all products from data base
        products_lists = new ArrayList<>();

        sqlLiteHelper sql = new sqlLiteHelper(HomeActivity.this);
        products_lists=sql.get_Products_Data();
        Log.d(Tag, "here ");

        for(int i=0;i<products_lists.size();i++)
        {

            Log.d(Tag, "onRequestPermissionsResult: " + products_lists.get(i).getName());

        }


// open productss fragment by default
        FragmentManager fragmentmanager = getSupportFragmentManager();
        FragmentTransaction fragmenttranscation = fragmentmanager.beginTransaction();
        ProductsFragment fragment_home = new ProductsFragment(products_lists);
        fragmenttranscation.replace(R.id.fragment,fragment_home);
        fragmenttranscation.commit();
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
        getMenuInflater().inflate(R.menu.home, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            FragmentManager fragmentmanager = getSupportFragmentManager();
            FragmentTransaction fragmenttranscation = fragmentmanager.beginTransaction();
            ProductsFragment fragment_home = new ProductsFragment(products_lists);
            fragmenttranscation.replace(R.id.fragment,fragment_home);
            fragmenttranscation.commit();
           // set title of actionbar
            searchbar.setVisibility(View.VISIBLE);
            title.setVisibility(View.INVISIBLE);


        } else if (id == R.id.nav_settings) {
            FragmentManager fragmentmanager = getSupportFragmentManager();
            FragmentTransaction fragmenttranscation = fragmentmanager.beginTransaction();
            AccountSettingFragment fragment_setting = new AccountSettingFragment();
            fragmenttranscation.replace(R.id.fragment,fragment_setting);
            fragmenttranscation.commit();
            // set title of actionbar
            searchbar.setVisibility(View.INVISIBLE);
            title.setVisibility(View.VISIBLE);
            title.setText("Settings");

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_logout) {

            sharedPreferences = getSharedPreferences(sharedPrefName,MODE_PRIVATE);
            SharedPreferences.Editor editor= sharedPreferences.edit();
            editor.putString("name" , "");
            editor.putString("password" , "");
            editor.putInt("check", 0);
            editor.commit();
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
