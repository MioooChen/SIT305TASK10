package com.project.hw10.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.project.hw10.Database;
import com.project.hw10.Quiz;
import com.project.hw10.QuizResponse;
import com.project.hw10.R;

import java.util.List;

public class TaskActivity extends AppCompatActivity {
    private QuizResponse quizResponse;
    private TextView title1;
    private TextView title2;
    private TextView description1;
    private RadioGroup radioGroup;
    private ImageButton nextButton;
    private int currentQuestion = 0;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        Intent intent = getIntent();
        quizResponse = (QuizResponse) intent.getSerializableExtra("quizResponse");

        title1 = findViewById(R.id.title1);
        title2 = findViewById(R.id.title2);
        description1 = findViewById(R.id.desc1);

        radioGroup = findViewById(R.id.options);

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId != -1) {
                int index = radioGroup.indexOfChild(findViewById(checkedId));
                quizResponse.getQuizList().get(currentQuestion).setMyAnswer(index);
            }
        });

        title1.setText("1. " + quizResponse.getQuizList().get(0).getQuestion());
        title2.setText("2. " + quizResponse.getQuizList().get(1).getQuestion());
        description1.setText(quizResponse.getQuizList().get(2).getQuestion());

        List<String> options = quizResponse.getQuizList().get(0).getOptions();
        for (int i = 0; i < options.size(); i++) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(options.get(i));
            radioButton.setTextColor(ContextCompat.getColor(this, R.color.white));
            radioButton.setButtonTintList(ContextCompat.getColorStateList(this, R.color.white));

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            layoutParams.setMargins(0, 10, 0, 10);
            radioButton.setLayoutParams(layoutParams);
            radioGroup.addView(radioButton);
        }

        nextButton = findViewById(R.id.button_arrow_right);
        nextButton.setOnClickListener(v -> {
            nextQuestion();
        });

        submitButton = findViewById(R.id.submit_button);
        submitButton.setOnClickListener(v -> {
            // answered all questions
            for (Quiz quiz : quizResponse.getQuizList()) {
                if (quiz.getMyAnswer() == -1) {
                    Toast.makeText(this, "Please answer all questions", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            Database.setQuizResponseFinished(quizResponse);
            Intent intent1 = new Intent(TaskActivity.this, ResultActivity.class);
            intent1.putExtra("quizResponse", quizResponse);
            startActivity(intent1);
            finish();
        });
    }

    private void nextQuestion() {
        // check if selected
        if (radioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show();
            return;
        }

        if (currentQuestion < quizResponse.getQuizList().size() - 2) {
            currentQuestion++;
            title1.setText((currentQuestion+1) + ". " + quizResponse.getQuizList().get(currentQuestion).getQuestion());
            title2.setText((currentQuestion+2) + ". " + quizResponse.getQuizList().get(currentQuestion + 1).getQuestion());
            description1.setText(quizResponse.getQuizList().get(currentQuestion).getQuestion());
            radioGroup.clearCheck();
            radioGroup.removeAllViews();

            List<String> options = quizResponse.getQuizList().get(currentQuestion).getOptions();
            for (int i = 0; i < options.size(); i++) {
                RadioButton radioButton = new RadioButton(this);
                radioButton.setText(options.get(i));
                radioButton.setTextColor(ContextCompat.getColor(this, R.color.white));
                radioButton.setButtonTintList(ContextCompat.getColorStateList(this, R.color.white));

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                layoutParams.setMargins(0, 10, 0, 10);
                radioButton.setLayoutParams(layoutParams);
                radioGroup.addView(radioButton);
            }
        } else if (currentQuestion == quizResponse.getQuizList().size() - 2) {
            currentQuestion++;
            title1.setText((currentQuestion+1) + ". " + quizResponse.getQuizList().get(currentQuestion).getQuestion());
            description1.setText(quizResponse.getQuizList().get(currentQuestion).getQuestion());

            radioGroup.clearCheck();
            radioGroup.removeAllViews();
            List<String> options = quizResponse.getQuizList().get(currentQuestion).getOptions();
            for (int i = 0; i < options.size(); i++) {
                RadioButton radioButton = new RadioButton(this);
                radioButton.setText(options.get(i));
                radioButton.setTextColor(ContextCompat.getColor(this, R.color.white));
                radioButton.setButtonTintList(ContextCompat.getColorStateList(this, R.color.white));

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                layoutParams.setMargins(0, 10, 0, 10);
                radioButton.setLayoutParams(layoutParams);
                radioGroup.addView(radioButton);
            }

            LinearLayout layout = findViewById(R.id.line2);
            layout.setVisibility(LinearLayout.GONE);
        }
    }
}