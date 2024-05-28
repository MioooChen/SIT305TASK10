package com.project.hw10;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class NetClient {
    private static final String BASE_URL = "http://10.0.2.2:5000";

    public static QuizResponse fetchFake() {
        String responseData = "{\n" +
                "    \"quiz\": [\n" +
                "        {\n" +
                "            \"correct_answer\": \"C\",\n" +
                "            \"options\": [\n" +
                "                \"Android apps are designed for entertainment purposes only, while Android games are designed for both entertainment and productivity.\",\n" +
                "                \"Android apps are developed using Java, while Android games are developed using C++.\",\n" +
                "                \"Android apps are installed on the device's internal storage, while Android games are installed on the device's external storage.\",\n" +
                "                \"Android apps are designed for use on a single device, while Android games are designed for use on multiple devices.\"\n" +
                "            ],\n" +
                "            \"question\": \"What is the main difference between a Android app and a Android game?\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"correct_answer\": \"A\",\n" +
                "            \"options\": [\n" +
                "                \"A toolkit for developing Android apps that includes a set of pre-written code and tools for debugging and testing.\",\n" +
                "                \"A platform for developing Android games that includes a set of pre-written code and tools for debugging and testing.\",\n" +
                "                \"A tool for creating Android apps that are optimized for use on a single device.\",\n" +
                "                \"A tool for creating Android games that are optimized for use on a single device.\"\n" +
                "            ],\n" +
                "            \"question\": \"What is the Android Software Development Kit (SDK)?\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"correct_answer\": \"B\",\n" +
                "            \"options\": [\n" +
                "                \"An activity is a user interface that represents a single task or function, while a service is a background process that performs a specific task.\",\n" +
                "                \"An activity is a background process that performs a specific task, while a service is a user interface that represents a single task or function.\",\n" +
                "                \"An activity is a single instance of an app that runs on a single device, while a service is a single instance of an app that runs on multiple devices.\",\n" +
                "                \"An activity is a single instance of an app that runs on multiple devices, while a service is a single instance of an app that runs on a single device.\"\n" +
                "            ],\n" +
                "            \"question\": \"What is the difference between a Android activity and a Android service?\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        Gson gson = new Gson();
        QuizResponse quizResponse = gson.fromJson(responseData, QuizResponse.class);
        return quizResponse;
    }
    public static QuizResponse fetchQuiz(String topic) {
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.MINUTES)
                .writeTimeout(10, TimeUnit.MINUTES)
                .build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(BASE_URL + "/getQuiz").newBuilder();
        urlBuilder.addQueryParameter("topic", topic);
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                Gson gson = new Gson();
                String responseData = response.body().string();
                QuizResponse quizResponse = gson.fromJson(responseData, QuizResponse.class);
                return quizResponse;
            } else {
                Log.e("QuizClient", "Failed to fetch quiz: " + response.code());
            }
        } catch (IOException e) {
            Log.e("QuizClient", "Error fetching quiz: " + e.getMessage());
        }
        return null;
    }
}
