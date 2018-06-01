package com.exubit.familylocator.model.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.exubit.familylocator.bean.Member;
import com.exubit.familylocator.core.utils.Utils;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import io.reactivex.Flowable;

public class MemberRepository {


    private final Utils utils;
    private final MemberLocalOperation memberLocalOperation;
    private final MemberNetworkOperation memberNetworkOperation;
    private final ChildEventListener memberNetListListener;

    public MemberRepository(@NonNull final Utils utils
            , @NonNull final MemberLocalOperation memberLocalOperation
            , @NonNull final MemberNetworkOperation memberNetworkOperation) {

        this.utils = utils;
        this.memberLocalOperation = memberLocalOperation;
        this.memberNetworkOperation = memberNetworkOperation;
        this.memberNetListListener = new MemberNetListListener();
    }

    public Flowable<Member> getAllMemberFlow(boolean... asynchronous) {
        return memberLocalOperation.getMemberFlow(asynchronous);
    }


   /* public void setMember(@NonNull final Member member, @Nullable final boolean... asynchronous) {
        memberLocalOperation.setMember(null, Member.Fields.OBJECT, member, asynchronous);
    }


    public <V> void setMember(@NonNull final String id, @NonNull final Member.Fields field, @NonNull final V value, @Nullable final boolean... asynchronous) {
        memberLocalOperation.setMember(id, field, value, asynchronous);
    }*/

    @NonNull
    public Flowable<Member> getChangedMemberFlow() {
        return getAllMemberFlow(true)
                .filter(member -> member.hashCode() != member.getBaseHashCode());
    }

    public void setMemberToNet(@NonNull final Member member) {
        memberNetworkOperation.setMemberToNet(member);
    }

    @NonNull
    public ChildEventListener getMemberNetListListener() {
        return memberNetListListener;
    }


    @NonNull
    public Query getUsersQuery() {
        return memberNetworkOperation.getUsersQuery();
    }


    private void setMemberToLocalDb(@NonNull final DataSnapshot dataSnapshot) {
        Member member = dataSnapshot.getValue(Member.class);
        member.setId(dataSnapshot.getKey());
        member.setBaseHashCode(member.hashCode());
        memberNetworkOperation.setMaxUpdateTime(member.getLastUpdateTime());
      //  setMember(member, true);
    }


    public boolean userHasGroup(@NonNull final String name) {
        return memberNetworkOperation.userHasGroup(name);
    }


    private class MemberNetListListener implements ChildEventListener {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            setMemberToLocalDb(dataSnapshot);
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            setMemberToLocalDb(dataSnapshot);
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    }


}
