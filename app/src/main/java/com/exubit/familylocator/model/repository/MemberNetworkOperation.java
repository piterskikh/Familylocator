package com.exubit.familylocator.model.repository;

import android.support.annotation.NonNull;

import com.exubit.familylocator.bean.Member;
import com.exubit.familylocator.core.App;
import com.exubit.familylocator.core.utils.Utils;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jakewharton.rxrelay2.BehaviorRelay;

import javax.inject.Inject;
import javax.inject.Named;

public class MemberNetworkOperation {

    private final String MAXUPDATETIME = "MAXUPDATETIME";
    private final Utils utils;
    private DatabaseReference usersRef;
    private long maxUpdateTime;

    @Inject
    @Named("baseOnline")
    BehaviorRelay<Boolean> baseOnlineRelay;

    public MemberNetworkOperation(@NonNull final Utils utils
            , @NonNull final DatabaseReference databaseReference) {
        App.getAppComponent().inject(this);
        this.utils = utils;
        usersRef = databaseReference.child("users");
        maxUpdateTime = utils.getLongSettings(MAXUPDATETIME);

        DatabaseReference connectedRef = databaseReference.child(".info/connected");
        connectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean connected = dataSnapshot.getValue(Boolean.class);

                if (connected)
                    baseOnlineRelay.accept(true);
                else
                    baseOnlineRelay.accept(false);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                baseOnlineRelay.accept(false);
            }
        });
    }

    public void setMemberToNet(Member member) {
        usersRef.child(member.getId()).setValue(member, this::setMemberCompletionListener);
    }

    private void setMemberCompletionListener(DatabaseError databaseError, DatabaseReference databaseReference) {
    }

    public Query getUsersQuery() {
        return usersRef.orderByChild("lastUpdateTime").startAt(utils.getLongSettings(MAXUPDATETIME) + 1, "lastUpdateTime");
    }

    public long getMaxUpdateTime() {
        return maxUpdateTime;
    }

    public void setMaxUpdateTime(long updateTime) {
        if (updateTime > maxUpdateTime) {
            maxUpdateTime = updateTime;
            utils.setLongSettings(MAXUPDATETIME, updateTime);
        }
     }
}

