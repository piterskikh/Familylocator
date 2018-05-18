package com.exubit.familylocator.core;

import android.support.annotation.NonNull;

import com.exubit.familylocator.core.utils.Utils;
import com.exubit.familylocator.model.repository.MemberRepository;
import com.google.firebase.database.Query;
import com.jakewharton.rxrelay2.BehaviorRelay;

import javax.inject.Inject;
import javax.inject.Named;

public class AppProcessor {

    @Inject
    Utils utils;
    @Inject
    MemberRepository memberRepository;
    @Inject
    @Named("mapActive")
    BehaviorRelay<Boolean> memberListRelay;

    private Query usersQuery;




    public AppProcessor() {
        App.getAppComponent().inject(this);
        init();
    }

    private void init() {
        memberRepository.getChangedMemberFlow().subscribe(memberRepository::setMemberToNet);
        memberListRelay.subscribe(this::memberListRelayListenner);
    }


    private void memberListRelayListenner(@NonNull final Boolean event) {
        if (event)
            setMemberListListenner();
        else
            deleteMemberListListenner();
    }


    private void setMemberListListenner() {
        usersQuery =  memberRepository.getUsersQuery();
        usersQuery.addChildEventListener(memberRepository.getMemberNetListListener());
    }

    private void deleteMemberListListenner() {
        if (usersQuery!=null) {
            usersQuery.addChildEventListener(memberRepository.getMemberNetListListener());
            usersQuery = null;
        }
    }


}
