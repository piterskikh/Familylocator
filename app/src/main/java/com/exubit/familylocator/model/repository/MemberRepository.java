package com.exubit.familylocator.model.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.exubit.familylocator.bean.Member;
import com.exubit.familylocator.core.utils.Utils;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.Query;

import io.reactivex.Flowable;

public class MemberRepository {


    private final Utils utils;
    private final MemberLocalOperation memberLocalOperation;
    private final MemberNetworkOperation memberNetworkOperation;

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


    public void setMemeber(@NonNull final Member member, @Nullable final boolean... asynchronous) {
        memberLocalOperation.setMember(null, Member.Fields.OBJECT, member, asynchronous);
    }


    public <V> void setMemeber(@NonNull final String id, @NonNull final Member.Fields field, @NonNull final V value, @Nullable final boolean... asynchronous) {
        memberLocalOperation.setMember(id, field, value, asynchronous);
    }

    @NonNull
    public Flowable<Member> getChangedMemberFlow() {
        return getAllMemberFlow(true)
                .filter(member -> member.hashCode() != member.getBaseHashCode());
    }

    public void setMemberToNet(@NonNull final Member member) {
        memberNetworkOperation.setMemberToNet(member);
    }

    @NonNull
    public ChildEventListener getMemberListListener() {
        return memberNetworkOperation.getChildEventListener();
    }


    @NonNull
    public Query getUsersQuery() {
        return memberNetworkOperation.getUsersQuery();
    }


}
