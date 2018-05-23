package com.exubit.familylocator.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.exubit.familylocator.R;
import com.exubit.familylocator.view.fragment.StartSettingsFragment;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Intent intent = SingleFragmentActivity.getIntent(this, new StartSettingsFragment());
        startActivity(intent);
    }
}
