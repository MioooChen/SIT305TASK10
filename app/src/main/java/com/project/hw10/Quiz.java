package com.project.hw10;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Quiz implements Serializable {
    @SerializedName("show")
    private boolean show = false;
    @SerializedName("correct_answer")
    private String correctAnswer;

    @SerializedName("options")
    private List<String> options;

    @SerializedName("question")
    private String question;

    @SerializedName("my_answer")
    private int myAnswer = -1;

    public String getCorrectAnswer() {
        char correctAnswerChar = correctAnswer.charAt(0);
        char[] optionChars = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
                'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};


        for (int i = 0; i < options.size(); i++) {
            if (optionChars[i] == correctAnswerChar) {
                return options.get(i);
            }
        }
        return correctAnswer;
    }

    public List<String> getOptions() {
        return options;
    }

    public String getQuestion() {
        return question;
    }

    public String getMyAnswerString() {
        if (myAnswer == -1) {
            return "Not Answered";
        }
        return options.get(myAnswer);
    }
    public int getMyAnswer() {return this.myAnswer;}

    public void setMyAnswer(int myAnswer) {
        this.myAnswer = myAnswer;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }
}