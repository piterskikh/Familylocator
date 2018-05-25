package com.exubit.familylocator.view.viewinterface;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.exubit.familylocator.bean.UserLocation;

public interface DefineAppStateFragmentInterface extends MvpView {

    @StateStrategyType(SkipStrategy.class)
    void goToPage(final int page);

}
