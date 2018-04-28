package com.exubit.familylocator.ui.uinterface;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.exubit.familylocator.bean.UserLocation;

public interface MapFragmentInterface extends MvpView {

    @StateStrategyType(SkipStrategy.class)
    void changeUserLocation(@NonNull final UserLocation userLocation);

}
