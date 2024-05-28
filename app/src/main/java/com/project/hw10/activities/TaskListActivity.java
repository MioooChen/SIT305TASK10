package com.project.hw10.activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.hw10.Database;
import com.project.hw10.NetClient;
import com.project.hw10.QuizResponse;
import com.project.hw10.Util;
import com.project.hw10.adapters.QuizResponseAdapter;
import com.project.hw10.R;
import com.project.hw10.User;

import java.util.ArrayList;
import java.util.List;

public class TaskListActivity extends AppCompatActivity {
    User user;
    QuizResponse quizResponse;
    private RecyclerView recyclerView;
    private QuizResponseAdapter responseAdapter;
    private boolean canRefreshTasks = false;
    TextView taskCountTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        // get username
        user = Database.getCurrentUser();
        TextView usernameText = findViewById(R.id.username);
        usernameText.setText(user.getUsername());

        // set avatar
        ImageView avatar = findViewById(R.id.image_avatar);
        // image base64 to bitmap
        if (!user.getImg().isEmpty()) {
            avatar.setImageBitmap(Util.base64ToBitmap(user.getImg()));
        }
        avatar.setOnClickListener(v -> {
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
        });


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        taskCountTextView = findViewById(R.id.text_task_count);
        taskCountTextView.setOnClickListener(v ->{
            if (canRefreshTasks) {
                canRefreshTasks = false;
                fetchTasks();
            }
        });


        responseAdapter = new QuizResponseAdapter(this, Database.getQuizResponses());
        recyclerView.setAdapter(responseAdapter);
        taskCountTextView.setText("You have " + Database.getUnfinishedResponses().size() + " tasks due");

        // get quiz
        // create thread
        fetchTasks();
    }

    private void fetchTasks() {
        Toast.makeText(this, "Fetching tasks...", Toast.LENGTH_SHORT).show();
        new Thread(() -> {
            quizResponse = NetClient.fetchQuiz(String.join("&", user.getInterests()));

//            quizResponse = NetClient.fetchFake();
            System.out.println(quizResponse);
            runOnUiThread(() -> {
                if (quizResponse == null) {
                    canRefreshTasks = true;
                    Toast.makeText(this, "Failed to fetch quiz", Toast.LENGTH_SHORT).show();
                    return;
                }
                Database.addQuizResponse(quizResponse);

                // update adapter
//                responseAdapter.
                responseAdapter.notifyDataSetChanged();
                taskCountTextView.setText("You have " + Database.getUnfinishedResponses().size() + " tasks due");

                Toast.makeText(this, "Tasks fetched", Toast.LENGTH_SHORT).show();
            });
        }).start();

    }

    @Override
    protected void onResume() {
        responseAdapter.notifyDataSetChanged();
        taskCountTextView.setText("You have " + Database.getUnfinishedResponses().size() + " tasks due");
        super.onResume();
    }
}