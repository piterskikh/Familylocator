package com.exubit.familylocator.model.localdb.query;

import android.arch.persistence.db.SupportSQLiteProgram;
import android.arch.persistence.db.SupportSQLiteQuery;

public interface RoomQueryInterface extends SupportSQLiteQuery {

    @Override
    default void bindTo(SupportSQLiteProgram statement) {
    }

    @Override
    default int getArgCount() {
        return 0;
    }
}
