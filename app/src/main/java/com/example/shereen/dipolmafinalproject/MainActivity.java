package com.example.shereen.dipolmafinalproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    String sharedPrefName = "Login";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button register = (Button) findViewById(R.id.mainregister);
        Button login = (Button) findViewById(R.id.mainLogin);
        first_time_check();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SignUp.class);
                startActivity(intent);
            }
        });
    }

    private void first_time_check() {
        sharedPreferences = getSharedPreferences(sharedPrefName, MODE_PRIVATE);
        int check = sharedPreferences.getInt("check", 0);
        if((check == 1)){
            Intent i = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(i);
            MainActivity.this.finish();
        }
    }

}


