package com.dariapro.traincounting.activity;

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

public class MainActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new CategoryListFragment();
    }

}
