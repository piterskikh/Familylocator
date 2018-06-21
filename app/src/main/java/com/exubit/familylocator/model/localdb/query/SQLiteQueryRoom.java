package com.exubit.familylocator.model.localdb.query;

import android.arch.persistence.db.SimpleSQLiteQuery;
import android.arch.persistence.db.SupportSQLiteProgram;
import android.arch.persistence.db.SupportSQLiteQuery;

abstract class SQLiteQueryRoom implements SupportSQLiteQuery {

    private final SimpleSQLiteQuery simpleSQLiteQuery;

    public SQLiteQueryRoom(Object... argsArray) {
        simpleSQLiteQuery = new SimpleSQLiteQuery(getQueryString(), argsArray);
    }

    abstract protected String getQueryString();

    @Override
    public String getSql() {
        return simpleSQLiteQuery.getSql();
    }

    @Override
    public void bindTo(SupportSQLiteProgram statement) {
        simpleSQLiteQuery.bindTo(statement);
    }

    @Override
    public int getArgCount() {
        return simpleSQLiteQuery.getArgCount();
    }


}
