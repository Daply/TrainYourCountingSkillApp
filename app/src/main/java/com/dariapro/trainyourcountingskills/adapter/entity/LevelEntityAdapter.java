package com.dariapro.trainyourcountingskills.adapter.entity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.dariapro.trainyourcountingskills.R;
import com.dariapro.trainyourcountingskills.entity.Level;
import com.dariapro.trainyourcountingskills.holder.EntityHolder;
import com.dariapro.trainyourcountingskills.holder.LevelHolder;
import java.util.ArrayList;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

/**
 * @author Pleshchankova Daria
 *
 */
public class LevelEntityAdapter extends EntityAdapter<Level> {

    private List<Level> levels;

    public LevelEntityAdapter(Context context, FragmentActivity currentActivity, String mode) {
        this.currentActivity = currentActivity;
        this.context = context;
        this.levels = new ArrayList<>();
        this.modeValue = mode;
    }

    @NonNull
    @Override
    public LevelHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(currentActivity);
        View view = inflater.inflate(R.layout.level_item_list, parent, false);
        return new LevelHolder(context, currentActivity, view, this.modeValue);
    }

    @Override
    public void onBindViewHolder(@NonNull EntityHolder holder, int position) {
        LevelHolder levelHolder = (LevelHolder) holder;
        Level level = levels.get(position);
        levelHolder.bindEvent(level);
    }

    @Override
    public void setData (List<Level> levels){
        if (levels == null) {
            this.levels = new ArrayList<>();
        }
        else {
            this.levels = levels;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return levels.size();
    }

}
