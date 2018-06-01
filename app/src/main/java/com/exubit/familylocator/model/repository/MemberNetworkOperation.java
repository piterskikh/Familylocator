package com.exubit.familylocator.model.repository;

import android.support.annotation.NonNull;

import com.exubit.familylocator.bean.Member;
import com.exubit.familylocator.core.App;
import com.exubit.familylocator.core.utils.Utils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.jakewharton.rxrelay2.BehaviorRelay;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import durdinapps.rxfirebase2.RxFirebaseDatabase;
import io.reactivex.Maybe;
import io.reactivex.Single;

public class MemberNetworkOperation {

    private final String MAXUPDATETIME = "MAXUPDATETIME";
    private final Utils utils;
    @Inject
    @Named("baseOnline")
    BehaviorRelay<Boolean> baseOnlineRelay;
    private DatabaseReference usersRef;
    private DatabaseReference groupsRef;
    private long maxUpdateTime;

    public MemberNetworkOperation(@NonNull final Utils utils
            , @NonNull final DatabaseReference databaseReference) {
        App.getAppComponent().inject(this);
        this.utils = utils;
        usersRef = databaseReference.child("users");
        groupsRef = databaseReference.child("groups");
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

    public DatabaseReference getGroupsRef() {
        return groupsRef;
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


        /*usersRef.child("sergey").child("userGroupId").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                DataSnapshot myDataSnapshot = dataSnapshot;

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

        final String[] groupArr = {null};
        // Maybe<String> result =
    /*    getRecord(usersRef.child("sergey").child("userGroupId"), String.class)

                .toFlowable()
                .doOnNext(group -> groupArr[0] = group)

                .filter(groupId -> groupId != null)
                .map(groupId -> Arrays.asList(name, groupId))
                .flatMapIterable(ids -> ids)
                .flatMap(userId -> getRecord(groupsRef.child(userId).child("lastUpdateTime")).toFlowable())
                .map(this::getId)
                .filter(userId -> userId != null)
                .toList()
                .flatMap(array -> checkAndCorrectBaseStrucure(groupArr[0], array))
                .toMaybe()
                .defaultIfEmpty(false)

                .subscribe(res -> {

                    boolean o = res;
                });*/
        //.map(member -> {
        //    for (Member m : member) {
        //        boolean properResult = true;
        //        if (m.getLastUpdateTime() == 0) {
        //if(m.getId().equals())
        //            properResult = false;

        //          }
        //     }
        //      return groupArr[0];
        //   })*/
        //  .toMaybe()
        //     .defaultIfEmpty("");

        /*result.subscribe(s -> {

            String g = s;

        });*/


        return false;

    }

    private <T> Maybe<T> getRecord(@NonNull final DatabaseReference ref, final Class<T> clazz) {
        return RxFirebaseDatabase.observeSingleValueEvent(ref, clazz);
    }

    private Maybe<DataSnapshot> getRecord(@NonNull final DatabaseReference ref) {
        return RxFirebaseDatabase.observeSingleValueEvent(ref);
    }

   /* private <T> T getSimpleObject(@NonNull final DataSnapshot dataSnapshot, @NonNull final Class<T> clazz) {
        T result = null;
        if (dataSnapshot.getKey().equals("userGroupId"))
            result = dataSnapshot.getValue(clazz);
        else if (dataSnapshot.getRef().toString().equals("lastUpdateTime"))

        return result;
    }*/

    private Member getMemberObject(@NonNull final DataSnapshot dataSnapshot) {
        Member member = dataSnapshot.getValue(Member.class);
        member.setId(dataSnapshot.getKey());
        member.setBaseHashCode(member.hashCode());
        return member;
    }

    private String getId(@NonNull final DataSnapshot dataSnapshot) {
        return dataSnapshot.getValue() != null ? dataSnapshot.getKey() : null;
    }

    String getStr(@NonNull final DataSnapshot dataSnapshot) {

        return (String) dataSnapshot.getValue();

    }

    private Single<Boolean> checkAndCorrectBaseStrucure(@NonNull final String currentUser, @NonNull final String groupName, @NonNull final List<String> userList) {

        if (userList.size() == 0) {

        } else if (userList.contains(currentUser) && userList.contains(groupName)) {


        } else if (userList.contains(currentUser) && !userList.contains(groupName)) {

        } else if (!userList.contains(currentUser) && userList.contains(groupName)) {

        }

       /* Firebase ref = Firebase(url: "https://<YOUR-FIREBASE-APP>.firebaseio.com");
        Firebase userRef = ref.child("user");
        Map newUserData = new HashMap();
        newUserData.put("age", 30);
        newUserData.put("city", "Provo, UT");
        userRef.updateChildren(newUserData);*/
        //RxFirebaseDatabase.updateChildren();
       // getRecord(usersRef.setValue(null));

        return Single.just(true);
    }

    private void deleteUserFromGroup(@NonNull final String user, @NonNull final String groupName) {
        if (user.equals(groupName)){
            deleteGroup(groupName);
            return;
        }



     }

    private void deleteGroup(@NonNull final String groupName) {
    }


    public static Map<String, Object> getUpdatePair(@NonNull final String property, @NonNull final Object value) {
        Map<String, Object> updateMap = new HashMap<>(2);
        updateMap.put(Member.FirebaseFields.LASTUPDATETIME.getFbField(), ServerValue.TIMESTAMP);
        updateMap.put(property, value);
        return updateMap;
    }
}


