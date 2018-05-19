package com.exubit.familylocator.view.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.exubit.familylocator.R;
import com.exubit.familylocator.databinding.StartSettingsBinding;

public class StartSettingsFragment extends BaseFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        StartSettingsBinding binding = DataBindingUtil.inflate(inflater, R.layout.start_settings, container, false);
        return binding.getRoot();
    }
}
