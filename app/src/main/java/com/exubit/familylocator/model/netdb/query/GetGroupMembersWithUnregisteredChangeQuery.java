package com.exubit.familylocator.model.netdb.query;

import android.support.annotation.NonNull;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class GetGroupMembersWithUnregisteredChangeQuery {


    private final Query query;

    GetGroupMembersWithUnregisteredChangeQuery(@NonNull final DatabaseReference databaseReference, final int lustUpdateTime, String... children) {

        DatabaseReference localDatabaseReference = databaseReference;
        for (String child : children) {
            localDatabaseReference.child(child);
        }
        query = localDatabaseReference.orderByChild("lastUpdateTime")
                .startAt(lustUpdateTime + 1, "lastUpdateTime");
    }

    @NonNull
    public Query getQuery() {
        return query;
    }
}
