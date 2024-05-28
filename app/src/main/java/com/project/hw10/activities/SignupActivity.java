package com.project.hw10.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.project.hw10.Database;
import com.project.hw10.R;
import com.project.hw10.User;
import com.project.hw10.Util;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.IOException;

public class SignupActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private RoundedImageView imageAvatar;
    private String avatarString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        imageAvatar = findViewById(R.id.image_avatar);

        imageAvatar.setOnClickListener(v -> {
            // 启动系统图片选择器
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        });

        EditText editUsername = findViewById(R.id.edit_username);
        EditText editEmail = findViewById(R.id.edit_email);
        EditText editConfirmEmail = findViewById(R.id.edit_confirm_email);
        EditText editPassword = findViewById(R.id.edit_password);
        EditText editConfirmPassword = findViewById(R.id.edit_confirm_password);
        EditText editPhoneNumber = findViewById(R.id.edit_phone_number);
        Button btnCreateAccount = findViewById(R.id.btn_create_account);

        // 创建新账户按钮点击事件
        btnCreateAccount.setOnClickListener(v -> {
            // 获取用户输入
            String username = editUsername.getText().toString().trim();
            String email = editEmail.getText().toString().trim();
            String confirmEmail = editConfirmEmail.getText().toString().trim();
            String password = editPassword.getText().toString().trim();
            String confirmPassword = editConfirmPassword.getText().toString().trim();
            String phoneNumber = editPhoneNumber.getText().toString().trim();

            // 检查用户输入是否有效
            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(email) || TextUtils.isEmpty(confirmEmail) ||
                    TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword) || TextUtils.isEmpty(phoneNumber)) {
                Toast.makeText(SignupActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else if (!email.equals(confirmEmail)) {
                Toast.makeText(SignupActivity.this, "Emails do not match", Toast.LENGTH_SHORT).show();
            } else if (!password.equals(confirmPassword)) {
                Toast.makeText(SignupActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            } else if (!Database.canRegister(username)) {
                Toast.makeText(SignupActivity.this, "Username already exists", Toast.LENGTH_SHORT).show();
            } else {
                createUser(username, email, password, phoneNumber);
            }
        });
    }

    private void createUser(String username, String email, String password, String phoneNumber) {
        User user = new User(username, email, password, phoneNumber, new String[0], avatarString);
        Database.addUser(user);

        // 显示创建成功消息
        Toast.makeText(SignupActivity.this, "Account created successfully", Toast.LENGTH_SHORT).show();

        // 跳转到登录页面或其他页面
        Intent intent = new Intent(SignupActivity.this, SelectTopicActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // 获取选取的图片的 URI
            Uri uri = data.getData();

            try {

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                avatarString = Util.bitmapToBase64(bitmap);


                imageAvatar.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}