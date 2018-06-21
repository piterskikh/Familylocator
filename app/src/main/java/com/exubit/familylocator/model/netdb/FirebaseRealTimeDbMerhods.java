package com.exubit.familylocator.model.netdb;

import android.support.annotation.NonNull;

import com.exubit.familylocator.model.beans.StateMember;
import com.google.firebase.database.Query;

import durdinapps.rxfirebase2.RxFirebaseDatabase;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

public class FirebaseRealTimeDbMerhods {


    public Flowable<StateMember> getGroupMembers(@NonNull final Query query) {
        return getFlowable(query, StateMember.class);
    }


    private <T> Flowable<T> getFlowable(@NonNull final Query query, @NonNull final Class<T> clazz) {
        return RxFirebaseDatabase.observeValueEvent(query, clazz).subscribeOn(Schedulers.io());
    }

}
