package com.exubit.familylocator.core;

import android.support.annotation.NonNull;

import com.exubit.familylocator.core.utils.Utils;
import com.exubit.familylocator.model.auxiliary.MemberNetListListener;
import com.exubit.familylocator.model.repository.MemberRepository;

import io.reactivex.disposables.Disposable;

public class AppProcessor {

    private final Utils utils;
    private final MemberRepository memberRepository;
    private final MemberNetListListener memberNetListListener;

    public AppProcessor(@NonNull final Utils utils, @NonNull final MemberRepository memberRepository) {
        this.utils = utils;
        this.memberRepository = memberRepository;
        memberNetListListener = new MemberNetListListener(memberRepository, utils);
        setMemberNetListenner();
    }


    private void setMemberNetListenner() {
        memberRepository.getUsersQuery().addChildEventListener(memberNetListListener);
    }


}
