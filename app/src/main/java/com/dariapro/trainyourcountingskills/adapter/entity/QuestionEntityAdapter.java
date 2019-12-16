package com.dariapro.trainyourcountingskills.adapter.entity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dariapro.trainyourcountingskills.R;
import com.dariapro.trainyourcountingskills.entity.Question;
import com.dariapro.trainyourcountingskills.holder.EntityHolder;
import com.dariapro.trainyourcountingskills.holder.QuestionHolder;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

/**
 * @author Pleshchankova Daria
 *
 */
public class QuestionEntityAdapter extends EntityAdapter<Question> {

    private List<Question> questions;

    public QuestionEntityAdapter(Context context, FragmentActivity currentActivity, String mode) {
        this.currentActivity = currentActivity;
        this.context = context;
        questions = new ArrayList<>();
        this.modeValue = mode;
    }

    @Override
    @NonNull
    public QuestionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(currentActivity);
        View view = inflater.inflate(R.layout.question_item_list, parent, false);
        return new QuestionHolder(context, currentActivity, view, this.modeValue);
    }

    @Override
    public void onBindViewHolder(@NonNull EntityHolder holder, int position) {
        QuestionHolder questionHolder = (QuestionHolder) holder;
        Question question = questions.get(position);
        questionHolder.bindEvent(question);
    }

    public void setData (List<Question> questions){
        if (questions == null) {
            this.questions = new ArrayList<>();
        }
        else {
            this.questions = questions;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }
}
