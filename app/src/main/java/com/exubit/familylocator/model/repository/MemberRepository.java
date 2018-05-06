package com.exubit.familylocator.model.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.exubit.familylocator.bean.Member;
import com.exubit.familylocator.core.utils.Utils;

import io.reactivex.Flowable;

public class MemberRepository {

    private final Utils utils;
    private final MemberLocalQuery memberLocalQuery;
    private final MemberNetworkQuery memberNetworkQuery;


    public enum Echo {
        NETTOLOCAL;
    }


    public MemberRepository(@NonNull final Utils utils
            , @NonNull final MemberLocalQuery memberLocalQuery
            , @NonNull final MemberNetworkQuery memberNetworkQuery) {

        this.utils = utils;
        this.memberLocalQuery = memberLocalQuery;
        this.memberNetworkQuery = memberNetworkQuery;
        this.memberNetworkQuery.setMemberRepository(this);
    }

    public Flowable<Member> getAllMemberFlow() {
        return memberLocalQuery.getMemberFlow();
    }

    public void setMemeber(@NonNull final Member member, final boolean... asynchronous) {
        setMemeber(member, null, asynchronous);
    }

    public void setMemeber(@NonNull final Member member, @Nullable final Echo echo, @Nullable final boolean... asynchronous) {
        memberLocalQuery.setMember(null, echo, Member.Fields.OBJECT, member, asynchronous);
    }

    public <V> void setMemeber(@NonNull final String id, @NonNull final Member.Fields field, @NonNull final V value, @Nullable final boolean... asynchronous) {
        setMemeber(id, field, value, null, asynchronous);
    }

    public <V> void setMemeber(@NonNull final String id, @NonNull final Member.Fields field, @NonNull final V value, @Nullable final Echo echo, @Nullable final boolean... asynchronous) {
        memberLocalQuery.setMember(id, echo, field, value, asynchronous);
    }


}
