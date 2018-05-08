package com.exubit.familylocator.model.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.exubit.familylocator.bean.Member;
import com.exubit.familylocator.core.utils.Utils;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;

public class MemberRepository {

    private final Utils utils;
    private final MemberLocalOperation memberLocalOperation;
    private final MemberNetworkOperation memberNetworkOperation;
    private Disposable memberBaseToNetSubscribtion;


    public MemberRepository(@NonNull final Utils utils
            , @NonNull final MemberLocalOperation memberLocalOperation
            , @NonNull final MemberNetworkOperation memberNetworkOperation) {

        this.utils = utils;
        this.memberLocalOperation = memberLocalOperation;
        this.memberNetworkOperation = memberNetworkOperation;
        this.memberNetworkOperation.setMemberRepository(this);
    }

    public Flowable<Member> getAllMemberFlow(boolean... asynchronous) {
        return memberLocalOperation.getMemberFlow(asynchronous);
    }

    public Flowable<Member> filterUnchangedMemberFlow() {
        return getAllMemberFlow().filter(member -> member.getUpdateCode() != member.hashCode());
    }

    public void setMemeber(@NonNull final Member member, final boolean... asynchronous) {
        setMemeber(member, null, asynchronous);
    }

    public void setMemeber(@NonNull final Member member, @Nullable final Echo echo, @Nullable final boolean... asynchronous) {
        memberLocalOperation.setMember(null, echo, Member.Fields.OBJECT, member, asynchronous);
    }

    public <V> void setMemeber(@NonNull final String id, @NonNull final Member.Fields field, @NonNull final V value, @Nullable final boolean... asynchronous) {
        setMemeber(id, field, value, null, asynchronous);
    }

    public <V> void setMemeber(@NonNull final String id, @NonNull final Member.Fields field, @NonNull final V value, @Nullable final Echo echo, @Nullable final boolean... asynchronous) {
        memberLocalOperation.setMember(id, echo, field, value, asynchronous);
    }

    public void startWork() {
        memberBaseToNetSubscribtion = getAllMemberFlow().subscribe(memberNetworkOperation::setMemberToNet);
    }

    public enum Echo {
        NETTOLOCAL;
    }


}
