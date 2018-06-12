package com.exubit.familylocator.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.exubit.familylocator.core.App;
import com.exubit.familylocator.core.utils.Utils;
import com.exubit.familylocator.model.dao.LocalGroupMemberDao;
import com.exubit.familylocator.model.repository.MemberRepository;
import com.exubit.familylocator.view.viewinterface.DefineAppStateFragmentInterface;
import com.jakewharton.rxrelay2.BehaviorRelay;

import javax.inject.Inject;
import javax.inject.Named;

@InjectViewState
public class DefineAppStateFragmentPresenter extends MvpPresenter<DefineAppStateFragmentInterface> {

    @Inject
    Utils utils;
    @Inject
    MemberRepository memberRepository;

    @Inject
    LocalGroupMemberDao localGroupMemberDao;

    @Inject
    @Named("mapActive")
    BehaviorRelay<Boolean> memberListRelay;

    @Inject
    @Named("baseOnline")
    BehaviorRelay<Boolean> baseOnlineRelay;

    public DefineAppStateFragmentPresenter() {
        App.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();




/*
        GroupMember groupMember = new GroupMember();
        groupMember.setId("sergey");


        SavedMember savedMember = new SavedMember();
        savedMember.setNickName("loloweewew21");
        savedMember.setAvatar("732478.jpg");




   groupMember.setSavedMember(savedMember);*/


    /*Completable.fromAction(() -> localGroupMemberDao.updateGropMemberLocation("sergey", 123L)).subscribeOn(Schedulers.io()).subscribe(() ->{

            String jj = "sasasa";

        }, e -> {

          e.printStackTrace();

       });*/

 /*     Completable.fromAction(() -> localGroupMemberDao.updateSavedMember("sergey", savedMember)).subscribeOn(Schedulers.io()).subscribe(() ->{

            String jj = "sasasa";

        }, e -> {

          e.printStackTrace();

       });*/


     /* localGroupMemberDao.getAppMember("sergey").subscribe(member->{
        GroupMember member1 = member;
    }, error->{

        String jj = "sasasa";
    });*/

/*        localGroupMemberDao.getGroupMemberFlow().subscribeOn(Schedulers.io()).subscribe(member ->{

            GroupMember member1 = member;

        }, error -> {

            error.printStackTrace();

        });*/

    }


    }
