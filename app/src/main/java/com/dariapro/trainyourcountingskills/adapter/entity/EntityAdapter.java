package com.dariapro.trainyourcountingskills.adapter.entity;

import android.content.Context;

import com.dariapro.trainyourcountingskills.holder.EntityHolder;

import java.util.List;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Pleshchankova Daria
 *
 */
public abstract class EntityAdapter<Entity> extends RecyclerView.Adapter<EntityHolder> {

    public FragmentActivity currentActivity;
    public Context context;
    public String modeValue;

    @Override
    public abstract void onBindViewHolder(EntityHolder holder, int position);

    public abstract void setData (List<Entity> entityList);

}
