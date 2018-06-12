package com.exubit.familylocator.model.dao;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.exubit.familylocator.model.beans.GroupMember;


@Database(entities = {GroupMember.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract LocalGroupMemberDao appMemberDao();
}
