package com.exubit.familylocator.core;

import android.support.annotation.NonNull;

import com.exubit.familylocator.core.utils.Utils;
import com.exubit.familylocator.model.repository.MemberRepository;
import com.google.firebase.database.ServerValue;

import io.reactivex.disposables.Disposable;

public class AppProcessor {

    private final Utils utils;
    private final MemberRepository memberRepository;
    private Disposable memberBaseToNetSubscribtion;

    public AppProcessor(@NonNull final Utils utils
            , @NonNull final MemberRepository memberRepository) {
        this.utils = utils;
        this.memberRepository = memberRepository;
    }

    private void lolo(){


    }
}
