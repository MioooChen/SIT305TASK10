package com.project.hw10.activities;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.project.hw10.Database;
import com.project.hw10.Quiz;
import com.project.hw10.QuizResponse;
import com.project.hw10.R;
import com.project.hw10.User;
import com.project.hw10.Util;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.makeramen.roundedimageview.RoundedImageView;

public class ProfileActivity extends AppCompatActivity {

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        this.user = Database.getCurrentUser();

        TextView usernameText = findViewById(R.id.usernameTextView);
        usernameText.setText(user.getUsername());

        TextView emailText = findViewById(R.id.emailTextView);
        emailText.setText(user.getEmail());

        RoundedImageView avatar = findViewById(R.id.image_avatar);
        if (!user.getImg().isEmpty()) {
            avatar.setImageBitmap(Util.base64ToBitmap(user.getImg()));
        }

        TextView totalQuestionsTextView = findViewById(R.id.totalQuestionsTextView);
        totalQuestionsTextView.setText(String.valueOf(getTotalQuestions()));

        TextView correctQuestionsTextView = findViewById(R.id.correctTextView);
        correctQuestionsTextView.setText(String.valueOf(getCorrectQuestions()));

        TextView incorrectQuestionsTextView = findViewById(R.id.incorrectTextView);
        incorrectQuestionsTextView.setText(String.valueOf(getIncorrectQuestions()));


        LinearLayout shareButton = findViewById(R.id.shareLine);
        shareButton.setOnClickListener(v -> showQRCode());

        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        LinearLayout infoLine = findViewById(R.id.infoLine);
        infoLine.setOnClickListener(v -> {
            Intent intent = new Intent(this, UpgradeActivity.class);
            startActivity(intent);
        });

        LinearLayout totalQuestionLayout = findViewById(R.id.totalQuestionLayout);
        totalQuestionLayout.setOnClickListener(v -> {
            Intent intent = new Intent(this, HistoryActivity.class);
            startActivity(intent);
        });
    }

    private void showQRCode() {
        Toast.makeText(this, "Generating QR Code", Toast.LENGTH_SHORT).show();
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(ProfileActivity.this);
        View bottomSheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.bottom_sheet_layout, findViewById(R.id.bottomSheetContainer));

        ImageView qrCodeImageView = bottomSheetView.findViewById(R.id.qrCodeImageView);

        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("name", user.getUsername());
        jsonObject.addProperty("email", user.getEmail());
        jsonObject.addProperty("question_count", getTotalQuestions());
        jsonObject.addProperty("question_correct_count", getCorrectQuestions());
        jsonObject.addProperty("question_incorrect_count", getIncorrectQuestions());

        Gson gson = new Gson();
        String textToEncode = gson.toJson(jsonObject);
        int width = 500;
        int height = 500;

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = qrCodeWriter.encode(textToEncode, BarcodeFormat.QR_CODE, width, height);
            Bitmap bitmap = bitMatrixToBitmap(bitMatrix);
            qrCodeImageView.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
    }

    private Bitmap bitMatrixToBitmap(BitMatrix bitMatrix) {
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                bitmap.setPixel(x, y, bitMatrix.get(x, y) ? ContextCompat.getColor(this, R.color.black) : ContextCompat.getColor(this, R.color.white));
            }
        }

        return bitmap;
    }

    private int getTotalQuestions() {
        int i = 0;
        for (QuizResponse quizResponse : Database.getQuizResponses()) {
            i += quizResponse.getQuizList().size();
        }
        return i;
    }

    private int getCorrectQuestions() {
        int cnt = 0;
        for (QuizResponse quizResponse : Database.getQuizResponses()) {
            if (quizResponse.isFinished()) {
                for (Quiz quiz : quizResponse.getQuizList()) {
                    if (quiz.getMyAnswerString().equals(quiz.getCorrectAnswer())) {
                        cnt++;
                    }
                }
            }

        }
        return cnt;
    }

    private int getIncorrectQuestions() {
        int cnt = 0;
        for (QuizResponse quizResponse : Database.getQuizResponses()) {
            if (quizResponse.isFinished()) {
                for (Quiz quiz : quizResponse.getQuizList()) {
                    if (!quiz.getMyAnswerString().equals(quiz.getCorrectAnswer())) {
                        cnt++;
                    }
                }
            }

        }
        return cnt;
    }
}