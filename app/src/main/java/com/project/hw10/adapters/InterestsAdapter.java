package com.project.hw10.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.hw10.R;

import java.util.List;

public class InterestsAdapter extends RecyclerView.Adapter<InterestsAdapter.ViewHolder> {
    private List<String> interests;
    private RecyclerViewClickListener listener;

    public InterestsAdapter(List<String> interests, RecyclerViewClickListener listener) {
        this.interests = interests;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // 创建列表项视图
        View interestView = inflater.inflate(R.layout.item_interest, parent, false);

        // 返回视图持有者
        return new ViewHolder(interestView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // 获取当前兴趣
        String interest = interests.get(position);

        // 设置兴趣文本
        holder.textInterest.setText(interest);

        // 设置项点击监听器
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 调用接口方法以通知监听器项被点击
                listener.onItemClick(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return interests.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textInterest;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // 获取兴趣文本视图
            textInterest = itemView.findViewById(R.id.text_interest);
        }
    }

    public String getItem(int position) {
        return interests.get(position);
    }
}

interface RecyclerViewClickListener {
    void onItemClick(View view, int position);
}
