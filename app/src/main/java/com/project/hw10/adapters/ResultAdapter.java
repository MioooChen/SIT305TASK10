package com.project.hw10.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.project.hw10.Quiz;
import com.project.hw10.R;

import java.util.List;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ResultItemViewHolder> {
    private List<Quiz> quizList;

    public ResultAdapter(List<Quiz> quizList) {
        this.quizList = quizList;
    }

    @Override
    public ResultItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_result, parent, false);
        return new ResultItemViewHolder(view, parent.getContext());
    }

    @Override
    public void onBindViewHolder(ResultItemViewHolder holder, int position) {
        Quiz quiz = quizList.get(position);
        holder.bind(quiz, position);
    }

    @Override
    public int getItemCount() {
        return quizList.size();
    }


    public class ResultItemViewHolder extends RecyclerView.ViewHolder {
        private Context context;
        private TextView titleTextView;
        private ImageButton imageButton;
        private RadioGroup radioGroup;

        public ResultItemViewHolder(View itemView, Context context) {
            super(itemView);
            this.context = context;
            titleTextView = itemView.findViewById(R.id.title_text_view);
            radioGroup = itemView.findViewById(R.id.options);
            imageButton = itemView.findViewById(R.id.image_button);
        }

        public void bind(Quiz resultItem, int postion) {
            titleTextView.setText((postion + 1) + ". " + resultItem.getQuestion());
            imageButton.setOnClickListener(v -> {
                resultItem.setShow(true);
                notifyItemChanged(postion);
            });

            if (resultItem.isShow()) {
                List<String> options = resultItem.getOptions();
                for (int i = 0; i < options.size(); i++) {
                    RadioButton radioButton = new RadioButton(context);
                    radioButton.setText(options.get(i));

                    radioButton.setTextColor(ContextCompat.getColor(context, R.color.white));
                    radioButton.setButtonTintList(ContextCompat.getColorStateList(context, R.color.white));
                    if (i == resultItem.getMyAnswer()) {
                        radioButton.setChecked(true);
                        radioButton.setTextColor(ContextCompat.getColor(context, R.color.red));
                        radioButton.setButtonTintList(ContextCompat.getColorStateList(context, R.color.red));
                    }
                    if (options.get(i).equals(resultItem.getCorrectAnswer())) {
                        radioButton.setChecked(true);
                        radioButton.setTextColor(ContextCompat.getColor(context, R.color.green));
                        radioButton.setButtonTintList(ContextCompat.getColorStateList(context, R.color.green));
                    }

                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    layoutParams.setMargins(0, 10, 0, 10);
                    radioButton.setLayoutParams(layoutParams);

                    radioButton.setEnabled(false);

                    radioGroup.addView(radioButton);
                }
                imageButton.setVisibility(View.GONE);
            } else {
                radioGroup.setVisibility(View.GONE);
                imageButton.setVisibility(View.VISIBLE);
            }

        }
    }
}





