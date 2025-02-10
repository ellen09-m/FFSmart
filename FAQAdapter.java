package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FAQAdapter extends RecyclerView.Adapter<FAQAdapter.FAQViewHolder> {
    private List<FAQ> faqList;

    public FAQAdapter(List<FAQ> faqList) {
        this.faqList = faqList;
    }

    @Override
    public FAQViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the item layout for each FAQ
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_faq, parent, false);
        return new FAQViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FAQViewHolder holder, int position) {
        FAQ faq = faqList.get(position);
        holder.questionTextView.setText(faq.getQuestion());
        holder.answerTextView.setText(faq.getAnswer());

        // Efficiently handle visibility based on answer state
        if (faq.isAnswerVisible()) {
            holder.answerTextView.setVisibility(View.VISIBLE);
        } else {
            holder.answerTextView.setVisibility(View.GONE);
        }

        // Toggle the answer visibility when the question is clicked
        holder.itemView.setOnClickListener(v -> {
            // Toggle visibility of the answer and notify adapter of the change
            faq.setAnswerVisible(!faq.isAnswerVisible());
            notifyItemChanged(position);
        });
    }

    @Override
    public int getItemCount() {
        return faqList.size(); // Return the size of the FAQ list
    }

    // ViewHolder class to hold references to the question and answer TextViews
    public static class FAQViewHolder extends RecyclerView.ViewHolder {
        TextView questionTextView;
        TextView answerTextView;

        public FAQViewHolder(View itemView) {
            super(itemView);
            // Find the views inside each item layout
            questionTextView = itemView.findViewById(R.id.faq_question);
            answerTextView = itemView.findViewById(R.id.faq_answer);
        }
    }
}
