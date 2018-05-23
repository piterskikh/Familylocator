package com.exubit.familylocator.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.exubit.familylocator.core.App;
import com.exubit.familylocator.core.utils.Utils;
import com.exubit.familylocator.model.repository.MemberRepository;
import com.exubit.familylocator.view.viewinterface.DefineAppStateFragmentInterface;
import com.jakewharton.rxrelay2.BehaviorRelay;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.disposables.Disposable;

@InjectViewState
public class DefineAppStateFragmentPresenter extends MvpPresenter<DefineAppStateFragmentInterface> {

    @Inject
    Utils utils;
    @Inject
    MemberRepository memberRepository;
    @Inject
    @Named("mapActive")
    BehaviorRelay<Boolean> memberListRelay;

    @Inject
    @Named("baseOnline")
    BehaviorRelay<Boolean> baseOnlineRelay;

    private Disposable subscription;
    private Disposable baseOnlineRelaySubscription;




    public DefineAppStateFragmentPresenter() {
        App.getAppComponent().inject(this);
    }

    @Override
    public void attachView(DefineAppStateFragmentInterface view) {
        super.attachView(view);

     }

    @Override
    public void detachView(DefineAppStateFragmentInterface view) {
        super.detachView(view);

     }




}
