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

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

@InjectViewState
public class MapPresenter extends MvpPresenter<MapFragmentInterface> {

    @Inject
    Utils utils;
    @Inject
    MemberRepository memberRepository;

    private Disposable subscription;


    public MapPresenter() {
        App.getAppComponent().inject(this);
        Member member = new Member("sergey", utils.getCellId(52.281461, 104.266230));
        memberRepository.setMemeber(member, false);

    }

    @Override
    public void attachView(MapFragmentInterface view) {
        super.attachView(view);
        Log.d("zaza", "карта присоединилась");
        subscription = memberRepository.getAllMemberFlow().subscribe(this::placeMember);
        memberRepository.startWork();
    }

    @Override
    public void detachView(MapFragmentInterface view) {
        super.detachView(view);
        Log.d("zaza", "карта отсоединилась");
        subscription.dispose();
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
        memberRepository.setMemeber(member);
    }

    void test(){



    }


}
