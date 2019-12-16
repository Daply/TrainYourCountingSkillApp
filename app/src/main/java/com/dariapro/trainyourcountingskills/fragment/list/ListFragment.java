package com.dariapro.trainyourcountingskills.fragment.list;

import com.dariapro.trainyourcountingskills.adapter.entity.EntityAdapter;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Pleshchankova Daria
 *
 */
public abstract class ListFragment extends Fragment {

    public String modeValue = null;
    public RecyclerView recyclerView = null;
    public EntityAdapter entityAdapter = null;

    public abstract void getExtras();

    public abstract void initData();

}
