package com.exubit.familylocator.model.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.exubit.familylocator.bean.Member;
import com.exubit.familylocator.core.utils.Utils;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

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
    }

    public Flowable<Member> getAllMemberFlow(boolean... asynchronous) {
        return memberLocalOperation.getMemberFlow(asynchronous);
    }

    public Flowable<Member> filterUnchangedMemberFlow() {
        return getAllMemberFlow().filter(member -> member.getUpdateCode() != member.hashCode());
    }

    public void setMemeber(@NonNull final Member member,  @Nullable final boolean... asynchronous) {
        memberLocalOperation.setMember(null, Member.Fields.OBJECT, member, asynchronous);
    }



    public <V> void setMemeber(@NonNull final String id, @NonNull final Member.Fields field, @NonNull final V value, @Nullable final boolean... asynchronous) {
        memberLocalOperation.setMember(id, field, value, asynchronous);
    }

    public void startWork() {
       memberBaseToNetSubscribtion = getAllMemberFlow()
               .filter(member -> !member.isBaseSynchronized())
               .subscribe(memberNetworkOperation::setMemberToNet);
    }

    public Query getUsersQuery() {
        return memberNetworkOperation.getUsersQuery();
    }





}
