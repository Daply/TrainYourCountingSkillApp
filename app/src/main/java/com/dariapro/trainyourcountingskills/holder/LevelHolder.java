package com.dariapro.trainyourcountingskills.holder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.dariapro.trainyourcountingskills.R;
import com.dariapro.trainyourcountingskills.activity.LevelListActivity;
import com.dariapro.trainyourcountingskills.entity.Level;
import androidx.fragment.app.FragmentActivity;

public class LevelHolder extends EntityHolder<Level> {

    private Level level;
    private TextView titleTextView;
    private ImageView passedImageView;

    public LevelHolder(Context context, FragmentActivity currentActivity,
                        View itemView, String mode) {
        super(itemView);

        itemView.setOnClickListener(this);
        this.currentActivity = currentActivity;
        this.context = context;
        titleTextView = itemView.findViewById(R.id.list_item_level_title);
        passedImageView = itemView.findViewById(R.id.list_item_level_passed);
        passedImageView.setVisibility(View.GONE);
        this.modeValue = mode;
    }

    @Override
    public void bindEvent(Level level){
        this.level = level;
        titleTextView.setText(this.level.getTitle());
        if (this.level.isPassed()) {
            passedImageView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = LevelListActivity.newIntent(this.currentActivity, level.getLevelId());
        intent.putExtra(this.context.getString(R.string.MODE), this.modeValue);
        this.currentActivity.startActivityForResult(intent, REQUEST_EVENT);
    }
}
