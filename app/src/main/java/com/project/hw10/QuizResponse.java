package com.project.hw10;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.io.Serializable;
import java.util.UUID;

public class QuizResponse implements Serializable {
    @SerializedName("id")
    private UUID id = UUID.randomUUID();

    @SerializedName("quiz")
    private List<Quiz> quizList;

    @SerializedName("finished")
    private boolean finished = false;
    public List<Quiz> getQuizList() {
        return quizList;
    }

    public void setQuizList(List<Quiz> quizList) {
        this.quizList = quizList;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < quizList.size(); i++) {
            Quiz quiz = quizList.get(i);
            stringBuilder.append("Question: ").append(quiz.getQuestion()).append("\n");
            stringBuilder.append("Options: ").append(quiz.getOptions()).append("\n");
            stringBuilder.append("Correct Answer: ").append(quiz.getCorrectAnswer()).append("\n\n");
        }
        return stringBuilder.toString();
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public UUID getId() {
        return id;
    }
}


