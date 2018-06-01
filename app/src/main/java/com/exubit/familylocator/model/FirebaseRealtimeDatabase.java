package com.exubit.familylocator.model;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.Map;

import durdinapps.rxfirebase2.RxFirebaseDatabase;
import io.reactivex.Maybe;

public class FirebaseRealtimeDatabase {

    private final String PATH = "groups";
    private final DatabaseReference usersRef;

    public FirebaseRealtimeDatabase(DatabaseReference databaseReference) {
        usersRef = databaseReference.child(PATH);
    }

    @NonNull
    public Map<String, Object> deleteUserFromGroup(@NonNull final String userName) {
        Map<String, Object> map = new HashMap<>();
        map.put(PATH + "/" + userName, null);
        return map;
    }

    @NonNull
    public Maybe<String> getGroupId(@NonNull final String userName) {
        return RxFirebaseDatabase.observeSingleValueEvent(usersRef.child(userName), String.class);
    }

    Long oo(DataSnapshot dataSnapshot) {
        return 7L;
    }


}
