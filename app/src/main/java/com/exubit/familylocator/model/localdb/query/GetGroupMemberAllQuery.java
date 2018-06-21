package com.exubit.familylocator.model.localdb.query;

public class GetGroupMemberAllQuery extends SQLiteQueryRoom {
    @Override
     protected String getQueryString() {
        String query = "SELECT * FROM groupmembers";
        return query;
    }
}
