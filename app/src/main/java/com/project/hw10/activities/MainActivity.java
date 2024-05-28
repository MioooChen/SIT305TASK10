package com.project.hw10.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.project.hw10.Database;
import com.project.hw10.JsonHelper;
import com.project.hw10.R;

public class MainActivity extends AppCompatActivity {

    private void init() {
        // 初始化 JsonHelper 和 Database
        JsonHelper.init(this);
        Database.initialize();
    }

    public static synchronized String getPackageName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.packageName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("package", getPackageName(this));
        init();



        TextView signup = findViewById(R.id.text_need_account);
        signup.setOnClickListener(v -> {
            Intent intent = new Intent(this, SignupActivity.class);
            startActivity(intent);
        });

        EditText editUsername = findViewById(R.id.edit_username);
        EditText editPassword = findViewById(R.id.edit_password);

        Button loginButton = findViewById(R.id.btn_login);
        loginButton.setOnClickListener(view -> {
            String username = editUsername.getText().toString();
            String password = editPassword.getText().toString();

            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                Toast.makeText(MainActivity.this, "Please enter username and password", Toast.LENGTH_SHORT).show();
            } else {
                boolean isValidUser = Database.checkUser(username, password);
                if (isValidUser) {
                    if (Database.getCurrentUser().getInterests().length == 0) {
                        Intent intent = new Intent(MainActivity.this, SelectTopicActivity.class);
                        intent.putExtra("username", username);
                        intent.putExtra("toTask", true);
                        startActivity(intent);
                        Toast.makeText(MainActivity.this, "Please select your interests", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(MainActivity.this, TaskListActivity.class);
                        startActivity(intent);
                        Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Incorrect username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }




}