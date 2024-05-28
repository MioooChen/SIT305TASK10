package com.project.hw10;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JsonHelper {
    private static final String FILE_NAME =  "users.json";
    private static String filePrefix = "";

    public static void init(Context context) {
        filePrefix = context.getFilesDir().getPath().toString() + "/";
    }

    // 保存用户数据到文件
    public static void saveUsers(List<User> userList) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (Writer writer = new FileWriter(filePrefix + FILE_NAME)) {
            gson.toJson(userList, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("save users successfully");
    }

    // 从文件中读取用户数据
    public static List<User> readUsers() {
        Gson gson = new Gson();
        List<User> userList = new ArrayList<>();
        try (Reader reader = new FileReader(filePrefix + FILE_NAME)) {
            Type type = new TypeToken<List<User>>(){}.getType();
            userList = gson.fromJson(reader, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userList;
    }

    // 根据用户名更新用户信息
    public static void updateUser(User updatedUser) {
        List<User> userList = readUsers();
        for (User user : userList) {
            if (user.getUsername().equals(updatedUser.getUsername())) {
                user.setEmail(updatedUser.getEmail());
                user.setPassword(updatedUser.getPassword());
                user.setPhoneNumber(updatedUser.getPhoneNumber());
                user.setInterests(updatedUser.getInterests());
                break;
            }
        }
        saveUsers(userList);
    }
}
