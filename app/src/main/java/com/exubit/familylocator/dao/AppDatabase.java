package com.exubit.familylocator.dao;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.exubit.familylocator.bean.Member;

@Database(entities = {Member.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract MemberDao memberDao();

}
