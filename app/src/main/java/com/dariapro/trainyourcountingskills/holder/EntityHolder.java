package com.dariapro.trainyourcountingskills.holder;

import android.content.Context;
import android.view.View;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

public abstract class EntityHolder<Entity> extends RecyclerView.ViewHolder
                                           implements View.OnClickListener {

    public int REQUEST_EVENT = 1;

    public FragmentActivity currentActivity;
    public Context context;
    public String modeValue;

    public EntityHolder(View itemView) {
        super(itemView);
    }

    public abstract void bindEvent(Entity entity);
}
