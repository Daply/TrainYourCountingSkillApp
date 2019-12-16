package com.dariapro.trainyourcountingskills.holder;


import android.view.View;
import android.widget.TextView;

import com.dariapro.trainyourcountingskills.R;
import com.dariapro.trainyourcountingskills.entity.Record;

public class ScoreHolder extends EntityHolder<Record> {

    private Record record;
    private TextView levelTextView;
    private TextView timeTextView;
    private TextView questionsNumberTextView;

    public ScoreHolder(View itemView) {
        super(itemView);

        levelTextView = itemView.findViewById(R.id.score_item_level);
        timeTextView = itemView.findViewById(R.id.score_item_time);
        questionsNumberTextView = itemView.findViewById(R.id.score_item_questions_number);
    }

    @Override
    public void bindEvent(Record record){
        if (record == null) {
            return;
        }
        this.record = record;
        levelTextView.setText(String.valueOf(this.record.getLevel()));
        timeTextView.setText(String.valueOf(this.record.getTime()));
        questionsNumberTextView.setText(String.valueOf(this.record.getNumberOfQuestions()));
    }

    @Override
    public void onClick(View v) {}
}
