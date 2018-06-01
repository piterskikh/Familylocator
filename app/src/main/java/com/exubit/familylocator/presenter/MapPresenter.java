package com.exubit.familylocator.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.exubit.familylocator.bean.Member;
import com.exubit.familylocator.bean.PointD;
import com.exubit.familylocator.bean.UserLocation;
import com.exubit.familylocator.core.App;
import com.exubit.familylocator.core.utils.Utils;
import com.exubit.familylocator.model.repository.MemberRepository;
import com.exubit.familylocator.view.viewinterface.MapFragmentInterface;
import com.jakewharton.rxrelay2.BehaviorRelay;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.disposables.Disposable;

@InjectViewState
public class MapPresenter extends MvpPresenter<MapFragmentInterface> {

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

    private boolean attachedView;


    public MapPresenter() {
        App.getAppComponent().inject(this);


       // Member member = new Member("sergey", utils.getCellId(52.281461, 104.266230));
       // memberRepository.setMember(member, false);

    }

    @Override
    public void attachView(MapFragmentInterface view) {
        super.attachView(view);
        Log.d("zaza", "карта присоединилась");
        subscription = memberRepository.getAllMemberFlow(false).subscribe(this::placeMember);
        baseOnlineRelaySubscription = baseOnlineRelay.subscribe(this::defineOnlineDbState);
        attachedView = true;
        memberListRelay.accept(attachedView);
     }

    @Override
    public void detachView(MapFragmentInterface view) {
        super.detachView(view);
        Log.d("zaza", "карта отсоединилась");
        subscription.dispose();
        baseOnlineRelaySubscription.dispose();
        attachedView = false;
        memberListRelay.accept(attachedView);
     }

    private void placeMember(@NonNull final Member member) {
        PointD pointD = utils.getLatLngFromCellId(member.getLocation());
        getViewState().changeUserLocation(new UserLocation(member.getId(), pointD.getX(), pointD.getY()));
    }


    public void changeOneLocation() {
        /*Member member = new Member();
        member.setId("sergey");
        member.setLocation(utils.getCellId(52.288102, 104.264539));*/
        Member member = new Member("sergey", utils.getCellId(52.281461, 104.266230));
        //memberRepository.setMember(member);
    }

    private void defineOnlineDbState(@NonNull final Boolean state){


        getViewState().showConnectionSnackbar(state);


    }


}
