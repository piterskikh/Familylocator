package com.exubit.familylocator.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.exubit.familylocator.R;

public class SingleFragmentActivity extends AppCompatActivity {

    private static Fragment staticFragment;

    public static Intent getIntent(Context context, Fragment fragment) {
        staticFragment = fragment;
        return new Intent(context, SingleFragmentActivity.class);
    }

    @LayoutRes
    protected int getLayoutResId() {
        return R.layout.activity_fragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }

    protected Fragment createFragment(){
        Fragment fragment = staticFragment;
        staticFragment = null;
        return fragment;
    }
}
