package com.project.hw10.activities;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.hw10.Database;
import com.project.hw10.R;
import com.project.hw10.adapters.FinishedQuizResponseAdapter;
import com.project.hw10.adapters.QuizResponseAdapter;


public class HistoryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FinishedQuizResponseAdapter finishedQuizResponseAdapter = new FinishedQuizResponseAdapter(this, Database.getQuizResponses());
        recyclerView.setAdapter(finishedQuizResponseAdapter);
    }
}