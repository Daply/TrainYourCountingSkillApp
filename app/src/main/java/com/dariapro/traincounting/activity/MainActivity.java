package com.dariapro.traincounting.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.dariapro.traincounting.R;
import com.dariapro.traincounting.fragment.CategoryFragment;
import com.dariapro.traincounting.fragment.CategoryListFragment;

import java.util.List;
import java.util.UUID;

public class MainActivity extends SingleFragmentActivity {

    public static final String EXTRA_CATEGORY_ID = "com.dariapro.traincounting.category_id";

    @Override
    protected Fragment createFragment() {
        return new CategoryListFragment();
    }

    public static Intent newIntent(Context packegeContext, UUID categoryId){
        Intent intent = new Intent(packegeContext, LevelListActivity.class);
        intent.putExtra(EXTRA_CATEGORY_ID, categoryId);
        return intent;
    }
}
