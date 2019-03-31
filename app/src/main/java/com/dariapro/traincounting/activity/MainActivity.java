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
import com.dariapro.traincounting.fragment.MainFragment;

import java.util.List;
import java.util.UUID;

public class MainActivity extends SingleFragmentActivity {

    public static final String EXTRA_CHOICE = "com.dariapro.traincounting.choice";

    @Override
    protected Fragment createFragment() {
        return new MainFragment();
    }

    public static Intent newRandomExampleIntent(Context packegeContext){
        Intent intent = new Intent(packegeContext, RandomExampleActivity.class);
        //intent.putExtra(EXTRA_CATEGORY_ID, categoryId);
        return intent;
    }

    public static Intent newCategoryIntent(Context packegeContext){
        Intent intent = new Intent(packegeContext, CategoryActivity.class);
        //intent.putExtra(EXTRA_CATEGORY_ID, categoryId);
        return intent;
    }
}
