package com.exubit.familylocator.model.repository;

import android.support.annotation.NonNull;

import com.exubit.familylocator.bean.Member;
import com.exubit.familylocator.bean.UserGroup;
import com.exubit.familylocator.core.App;
import com.exubit.familylocator.core.utils.Utils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jakewharton.rxrelay2.BehaviorRelay;

import java.util.Arrays;

import javax.inject.Inject;
import javax.inject.Named;

import durdinapps.rxfirebase2.RxFirebaseDatabase;
import io.reactivex.Maybe;

public class MemberNetworkOperation {

    private final String MAXUPDATETIME = "MAXUPDATETIME";
    private final Utils utils;
    @Inject
    @Named("baseOnline")
    BehaviorRelay<Boolean> baseOnlineRelay;
    private DatabaseReference usersRef;
    private DatabaseReference communityRef;
    private long maxUpdateTime;

    public MemberNetworkOperation(@NonNull final Utils utils
            , @NonNull final DatabaseReference databaseReference) {
        App.getAppComponent().inject(this);
        this.utils = utils;
        usersRef = databaseReference.child("users");
        communityRef = databaseReference.child("community");
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

    public DatabaseReference getCommunityRef() {
        return communityRef;
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


    public boolean userHasGroup(@NonNull final String name) {

        final UserGroup[] groupArr = {null};
        Maybe<UserGroup> result = getRecord(usersRef, "sergey", UserGroup.class)
                .toFlowable()
                .doOnNext(group -> groupArr[0] = group)
                .filter(groupId -> groupId.getUserGroupId() != null)
                .map(groupId -> Arrays.asList(name, groupId.getUserGroupId()))
                .flatMapIterable(ids -> ids)
                .flatMap(user -> getRecord(communityRef, user, Member.class).toFlowable())
                .toList()
                .map(member -> {
                    for (Member m : member) {
                        boolean properResult = true;
                        if (m.getLastUpdateTime() == 0) {
                            //if(m.getId().equals())
                            properResult = false;

                        }
                    }
                    return groupArr[0];
                })
                .toMaybe().defaultIfEmpty(new UserGroup(null));


        return false;

    }


    private <T> Maybe<T> getRecord(@NonNull final DatabaseReference ref, @NonNull final String name, @NonNull Class<T> clazz) {
       // RxFirebaseDatabase.observeSingleValueEvent(ref.child(name)).map(dataSnapshot -> this::getMemberObject);

        return RxFirebaseDatabase.observeSingleValueEvent(ref.child(name), clazz);
    }

    private Member getMemberObject(@NonNull final DataSnapshot dataSnapshot) {
        Member member = dataSnapshot.getValue(Member.class);
        member.setId(dataSnapshot.getKey());
        member.setBaseHashCode(member.hashCode());
        return member;
    }
}


