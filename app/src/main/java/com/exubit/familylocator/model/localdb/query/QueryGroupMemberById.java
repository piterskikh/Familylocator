package com.exubit.familylocator.model.localdb.query;

import android.arch.persistence.db.SimpleSQLiteQuery;

public class QueryGroupMemberById implements RoomQueryInterface {

    private final SimpleSQLiteQuery simpleSQLiteQuery;

    public QueryGroupMemberById(Object id) {
        String query = "SELECT * FROM Groupmbers WHERE id = ? LIMIT 1";
        simpleSQLiteQuery = new SimpleSQLiteQuery(query, new Object[]{id});
    }

    @Override
    public String getSql() {
        return simpleSQLiteQuery.getSql();
    }


}
