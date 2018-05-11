package com.exubit.familylocator.model.auxiliary;

import android.support.annotation.NonNull;

import com.exubit.familylocator.bean.Member;
import com.exubit.familylocator.core.utils.Utils;
import com.exubit.familylocator.model.repository.MemberNetworkOperation;
import com.exubit.familylocator.model.repository.MemberRepository;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public class MemberNetListListener implements ChildEventListener {

    private final Utils utils;
    private final MemberRepository memberRepository;
    private long maxUpdateTime;


    public MemberNetListListener(@NonNull final MemberRepository memberRepository, @NonNull final Utils utils) {
        this.memberRepository = memberRepository;
        this.utils = utils;
        maxUpdateTime = utils.getLongSettings(MemberNetworkOperation.MAXUPDATETIME);
    }

    private void setMaxUpdateTime(final long updateTime) {
        if (updateTime > maxUpdateTime) {
            maxUpdateTime = updateTime;
            utils.setLongSettings(MemberNetworkOperation.MAXUPDATETIME, updateTime);
        }
    }

    private Member processMember(@NonNull final DataSnapshot dataSnapshot) {
        Member member = dataSnapshot.getValue(Member.class);
        member.setId(dataSnapshot.getKey());
        member.setBaseSynchronized(true);
        return member;
    }

    private void setMemberToLocalDb(@NonNull final DataSnapshot dataSnapshot){
        Member member = processMember(dataSnapshot);
        setMaxUpdateTime(member.getLastUpdateTime());
        if(!member.getEditor().equals(MemberNetworkOperation.EDITORKEY))
            memberRepository.setMemeber(member);
     }

    @Override
    public void onChildAdded(@NonNull final DataSnapshot dataSnapshot, @NonNull final String s) {
        setMemberToLocalDb(dataSnapshot);
    }

    @Override
    public void onChildChanged(@NonNull final DataSnapshot dataSnapshot, @NonNull final String s) {
        setMemberToLocalDb(dataSnapshot);
    }

    @Override
    public void onChildRemoved(@NonNull final DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(@NonNull final DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(@NonNull final DatabaseError databaseError) {

    }
}
