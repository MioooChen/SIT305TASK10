package com.project.hw10.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.project.hw10.Quiz;
import com.project.hw10.QuizResponse;
import com.project.hw10.R;
import com.project.hw10.adapters.ResultAdapter;

import java.util.List;

public class ResultActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ResultAdapter resultAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        QuizResponse quizResponse = (QuizResponse) intent.getSerializableExtra("quizResponse");

        List<Quiz> quizList = quizResponse.getQuizList();
        for (int i = 0; i < quizList.size(); i++) {
            quizList.get(i).setShow(i == 0);
        }

        resultAdapter = new ResultAdapter(quizList);
        recyclerView.setAdapter(resultAdapter);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(v -> {
            finish();
        });



    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}