package com.example.shereen.dipolmafinalproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    String sharedPrefName = "Location";
    double user_lat,user_lng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSharedPreference();
        Button register = (Button) findViewById(R.id.registerbutton);
        final EditText address = (EditText) findViewById(R.id.signlocation);
        address.setText(user_lat+","+user_lng);

        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this,location.class);
                startActivity(intent);

            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save_user();
            }
        });
    }
    public void save_user()
    {
        EditText name = (EditText) findViewById(R.id.signname);
        String user_name = name.getText().toString();

        EditText phone = (EditText) findViewById(R.id.signphone);
        int user_phone =Integer.parseInt(phone.getText().toString());

        EditText email = (EditText) findViewById(R.id.signemail);
        String user_email = email.getText().toString();

        EditText password = (EditText) findViewById(R.id.signpassword);
        String user_password = password.getText().toString();


        EditText address = (EditText) findViewById(R.id.signlocation);
        address.setText(user_lat+","+user_lng);


        User user = new User(user_name,user_phone ,user_lat,user_lng,user_email,user_password);

        sqlLiteHelper sqlLiteHelper = new sqlLiteHelper(SignUp.this);
        sqlLiteHelper.insert_user(user);

        Toast.makeText(SignUp.this,"Saved",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(SignUp.this , LoginActivity.class);
        startActivity(intent);

    }

    public void getSharedPreference()
    {
        sharedPreferences = getSharedPreferences(sharedPrefName,MODE_PRIVATE);
        String lat = sharedPreferences.getString("lat", "0.0");
        String lng = sharedPreferences.getString("lng" , "0.0");
        user_lat = Double.parseDouble(lat);
        user_lng = Double.parseDouble(lng);


    }

}
