package com.example.shereen.dipolmafinalproject;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    String sharedPrefName = "Login";
    String user_details = "user_Details";
    static String TAG="TEST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button signin = (Button) findViewById(R.id.loginbutton);
        getSharedPreference();
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: here");
                EditText editText_email = (EditText) findViewById(R.id.loginemail);
                EditText editText_password = (EditText) findViewById(R.id.loginpassword);
                String email = editText_email.getText().toString();
                String password = editText_password.getText().toString();
                login(email,password);
            }
        });
    }

    public void login(String email, String password) {
        sqlLiteHelper sqlLiteHelper = new sqlLiteHelper(LoginActivity.this);
        ArrayList<User> users = sqlLiteHelper.get_Users_Data();
        for (int i = 0; i < users.size(); i++)
        {
            Log.d(TAG, "login: 1 " +users.get(i).password);
            if (users.get(i).email.equals(email)) {
                if (users.get(i).password.equals(password)) {
                    Toast.makeText(LoginActivity.this, "Signed in", Toast.LENGTH_LONG).show();
                    sharedPreferences = getSharedPreferences(sharedPrefName, MODE_PRIVATE);
                    int check = sharedPreferences.getInt("check", 0);
                    if (check == 0) {
                        save_username_password();
                    } else {
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                        this.finish();
                    }
                }
            }
        }
    }
    //We need to save his email anyway for later use.
    public void add_user_Details()
    {
        EditText name = (EditText) findViewById(R.id.loginemail);
        String email = name.getText().toString();
        Log.d(TAG, "add_user_Details: " + email);
        sharedPreferences = getSharedPreferences(user_details, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", email);
        editor.commit();
    }
    public void save_username_password() {
        final Dialog dialog = new Dialog(LoginActivity.this);
        dialog.setContentView(R.layout.activity_dialoug);
        TextView dialoug_title = (TextView) dialog.findViewById(R.id.title1);
        dialoug_title.setText("Save");
        TextView content = (TextView) dialog.findViewById(R.id.content);
        content.setText("Do you want to save your username and password?");
        Button dialogButton = (Button) dialog.findViewById(R.id.positive_button);
        Button no = (Button) dialog.findViewById(R.id.negative_button);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_user_Details();
               
                Toast.makeText(LoginActivity.this, "Password and username not saved", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "Password and username saved", Toast.LENGTH_SHORT).show();
                addSharedPreference();
                dialog.dismiss();
                sharedPreferences = getSharedPreferences(sharedPrefName, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("check", 1);
                editor.commit();

                add_user_Details();

                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
            }

        });
        dialog.show();
    }

    public void getSharedPreference()
    {
        sharedPreferences = getSharedPreferences(sharedPrefName,MODE_PRIVATE);
        String name = sharedPreferences.getString("name", "");
        String password = sharedPreferences.getString("password" , "");

        EditText editText_name = (EditText) findViewById(R.id.loginemail);
        EditText editText_password = (EditText) findViewById(R.id.loginpassword);
        editText_name.setText(name);
        editText_password.setText(password);
        // Intent intent = new Intent(LoginActivity.this,home_page.class);
        // startActivity(intent);
        login(name,password);
    }

    public void addSharedPreference()
    {
        EditText name = (EditText) findViewById(R.id.loginemail);
        String user_name = name.getText().toString();
        EditText password = (EditText) findViewById(R.id.loginpassword);
        String user_password = password.getText().toString();
        sharedPreferences = getSharedPreferences(sharedPrefName,MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();

        editor.putString("name" , user_name);
        editor.putString("password" , user_password);

        editor.commit();


    }
}