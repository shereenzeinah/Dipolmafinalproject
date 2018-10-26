package com.example.shereen.dipolmafinalproject;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        FragmentManager fragmentmanager = getSupportFragmentManager();
        FragmentTransaction fragmenttranscation = fragmentmanager.beginTransaction();
        ProductsFragment fragment_home = new ProductsFragment();
        fragmenttranscation.replace(R.id.fragment,fragment_home);
        fragmenttranscation.commit();

    }
}
