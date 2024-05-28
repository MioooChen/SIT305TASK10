package com.project.hw10.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.hw10.QuizResponse;
import com.project.hw10.R;
import com.project.hw10.activities.TaskActivity;
import com.project.hw10.activities.TaskListActivity;

import java.util.List;

public class QuizResponseAdapter extends RecyclerView.Adapter<QuizResponseAdapter.QuizResponseViewHolder> {

    private List<QuizResponse> responseList;
    private TaskListActivity context;

    public QuizResponseAdapter(TaskListActivity context, List<QuizResponse> responseList) {
        this.context = context;
        this.responseList = responseList;
    }

    @NonNull
    @Override
    public QuizResponseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quiz_response, parent, false);
        return new QuizResponseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuizResponseViewHolder holder, int position) {
        QuizResponse response = responseList.get(position);
        if (response.isFinished()) {
            holder.itemView.setVisibility(View.GONE);
            return;
        }

        holder.titleTextView.setText("Generated Task " + (position+1));
        holder.contentTextView.setText("Small Description for the generated Task");
        holder.button.setOnClickListener(v -> {
            Intent intent = new Intent(context, TaskActivity.class);
            intent.putExtra("quizResponse", responseList.get(position));

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        int count = 0;
        for (QuizResponse response : responseList) {
            if (!response.isFinished()) {
                count++;
            }
        }
        return count;
    }

    public class QuizResponseViewHolder extends RecyclerView.ViewHolder {

        ImageView starImageView;
        TextView titleTextView;
        TextView contentTextView;
        ImageButton button;

        public QuizResponseViewHolder(@NonNull View itemView) {
            super(itemView);
            starImageView = itemView.findViewById(R.id.image_star);
            titleTextView = itemView.findViewById(R.id.text_title);
            contentTextView = itemView.findViewById(R.id.text_content);
            button = itemView.findViewById(R.id.button_arrow_right);
        }
    }
}