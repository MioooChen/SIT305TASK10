package com.project.hw10.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.project.hw10.Database;
import com.project.hw10.adapters.InterestsAdapter;
import com.project.hw10.R;
import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SelectTopicActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private InterestsAdapter adapter;

    private List<String> selectedTopics = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_topic);

        FlexboxLayout flexboxLayout = findViewById(R.id.flexbox);

        for (String topic : getTopics()) {
            TextView textView = createTextView(topic);

            textView.setOnClickListener(v -> {
                toggleSelectedState(textView, topic);
            });
            flexboxLayout.addView(textView);
        }

        Button button = findViewById(R.id.btn_next);
        button.setOnClickListener(v -> {
            String username = getIntent().getStringExtra("username");
            Database.updateUserInterests(username, selectedTopics);
            // 回到主页
            finish();
            boolean toTask = getIntent().getBooleanExtra("toTask", false);
            if (toTask) {
                Intent intent = new Intent(SelectTopicActivity.this, TaskListActivity.class);
                startActivity(intent);
                Toast.makeText(SelectTopicActivity.this,
                        "Topics updated.",
                        Toast.LENGTH_SHORT).show();
            }

        });

    }

    private void toggleSelectedState(TextView textView, String topic) {
        if (!selectedTopics.contains(topic) && selectedTopics.size() >= 10) {
            Toast.makeText(SelectTopicActivity.this,
                    "you can't select more than 10 topics.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedTopics.contains(topic)) {
            // 如果已经选中，则取消选中
            textView.setBackgroundResource(R.drawable.textview_border);
            selectedTopics.remove(topic);
        } else {
            // 如果未选中，则选中
            textView.setBackgroundResource(R.drawable.selected_textview_border);
            selectedTopics.add(topic);
        }
    }

    private TextView createTextView(String s) {
        TextView textView = new TextView(this);
        textView.setText(s);
        textView.setPadding(16, 16, 16, 16);

        // 设置边框
        textView.setBackgroundResource(R.drawable.textview_border);

        // 设置字体颜色
        textView.setTextColor(Color.BLACK);
        textView.setTypeface(null, Typeface.BOLD);

        // 设置居中对齐
        textView.setGravity(Gravity.CENTER);

        // 设置布局参数
        FlexboxLayout.LayoutParams layoutParams = new FlexboxLayout.LayoutParams(
                FlexboxLayout.LayoutParams.WRAP_CONTENT,
                FlexboxLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8, 8, 8, 8);
        textView.setLayoutParams(layoutParams);

        return textView;
    }


    private List<String> getTopics() {
        String[] topics = {
                "Data Structures",
                "Algorithms",
                "Sorting Techniques",
                "Graph Theory",
                "Database Optimization",
                "Operating Systems",
                "Computer Networks",
                "Distributed Systems",
                "Cloud Computing",
                "Big Data Analysis",
                "Software Engineering",
                "Programming Languages",
                "Artificial Intelligence",
                "Machine Learning",
                "Deep Learning",
                "Natural Language Processing",
                "Computer Vision",
                "Reinforcement Learning",
                "Digital Signal Processing",
                "Embedded Systems",
                "Virtual Reality",
                "Blockchain Technology",
                "Web Development",
                "Backend Services",
                "Mobile App Development",
                "Data Visualization",
                "Web Scraping",
                "Information Retrieval",
                "Cybersecurity",
                "Quantum Computing",
        };
        return Arrays.asList(topics);
    }
}