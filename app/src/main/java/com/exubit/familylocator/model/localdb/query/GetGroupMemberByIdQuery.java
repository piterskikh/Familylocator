package com.exubit.familylocator.model.localdb.query;

public class GetGroupMemberByIdQuery extends SQLiteQueryRoom {


    public GetGroupMemberByIdQuery(Object... argsArray) {
        super(argsArray);
    }

    @Override
     protected String getQueryString() {
        String query = "SELECT * FROM groupmembers WHERE id = ? LIMIT 1";
        return query;
    }
}
