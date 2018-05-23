package com.exubit.familylocator.view.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.exubit.familylocator.R;
import com.exubit.familylocator.bean.UserLocation;
import com.exubit.familylocator.databinding.FragmentDefineAppStateBinding;
import com.exubit.familylocator.presenter.DefineAppStateFragmentPresenter;
import com.exubit.familylocator.view.viewinterface.DefineAppStateFragmentInterface;

public class DefineAppStateFragment extends BaseFragment implements DefineAppStateFragmentInterface {

    @InjectPresenter
    DefineAppStateFragmentPresenter fragmentPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        FragmentDefineAppStateBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_define_app_state, container, false);
        binding.ProgressViewText.setText("Loading...");
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        return binding.getRoot();
    }


    @Override
    public void changeUserLocation(@NonNull UserLocation userLocation) {

    }
}
