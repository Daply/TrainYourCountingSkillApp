package com.dariapro.trainyourcountingskills.adapter.entity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dariapro.trainyourcountingskills.R;
import com.dariapro.trainyourcountingskills.entity.Record;
import com.dariapro.trainyourcountingskills.holder.EntityHolder;
import com.dariapro.trainyourcountingskills.holder.ScoreHolder;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

public class ScoreAdapter extends EntityAdapter<Record>{

    private List<Record> records;

    public ScoreAdapter(Context context, FragmentActivity currentActivity) {
        records = new ArrayList<>();
        this.context = context;
        this.currentActivity = currentActivity;
    }

    @Override
    @NonNull
    public ScoreHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(this.currentActivity);
        View view = inflater.inflate(R.layout.score_item_list, parent, false);
        return new ScoreHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EntityHolder holder, int position) {
        ScoreHolder scoreHolder = (ScoreHolder) holder;
        Record record = records.get(position);
        scoreHolder.bindEvent(record);
    }

    @Override
    public void setData(List<Record> records){
        if (records != null) {
            this.records = records;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return records.size();
    }

}
