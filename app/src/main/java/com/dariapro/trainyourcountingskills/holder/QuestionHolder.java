package com.dariapro.trainyourcountingskills.holder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dariapro.trainyourcountingskills.R;
import com.dariapro.trainyourcountingskills.activity.QuestionListActivity;
import com.dariapro.trainyourcountingskills.entity.Question;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

public class QuestionHolder extends EntityHolder<Question> {

    private Question question;
    private TextView titleTextView;
    private ImageView passedImageView;

    public QuestionHolder(Context context, FragmentActivity currentActivity,
                           View itemView, String mode) {
        super(itemView);

        itemView.setOnClickListener(this);
        this.currentActivity = currentActivity;
        this.context = context;
        titleTextView = itemView.findViewById(R.id.question_item_level_title);
        passedImageView = itemView.findViewById(R.id.list_item_question_passed);
        passedImageView.setVisibility(View.GONE);
        this.modeValue = mode;
    }

    @Override
    public void bindEvent(Question question){
        if (question == null) {
            return;
        }
        this.question = question;
        titleTextView.setText(this.question.getTitle());
        if (this.question.isPassed()) {
            passedImageView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = QuestionListActivity.newIntent(currentActivity, question.getQuestionId());
        intent.putExtra(context.getString(R.string.MODE), modeValue);
        currentActivity.startActivityForResult(intent, REQUEST_EVENT);
    }
}
