package com.exubit.familylocator.model.repository;

import android.annotation.SuppressLint;
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

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import durdinapps.rxfirebase2.RxFirebaseDatabase;
import io.reactivex.Maybe;
import io.reactivex.Observable;

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

        getGroupId(name).filter(groupId -> groupId.getUserGroupId()!=null).map(groupId -> {
            List<String> userList = new ArrayList<>();
            userList.add(name);
            userList.add(groupId.getUserGroupId());
            return userList;}).toFlowable().flatMap(user-> getGroupId(user).toFlowable());
        //.flatMap(user -> getGroupId(name));
String [] o = {"1","2","3"};
Observable.fromArray(o);




                //defaultIfEmpty(new UserGroup("123")).subscribe(result -> {

          // UserGroup lo = result;
        });

        return false;

    }


    private Maybe<UserGroup> getGroupId(@NonNull final String name) {
        Query query = usersRef.child(name);
        return RxFirebaseDatabase.observeSingleValueEvent(query, UserGroup.class);
    }


}


