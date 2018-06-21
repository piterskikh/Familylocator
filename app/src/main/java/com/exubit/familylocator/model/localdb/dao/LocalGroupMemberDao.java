package com.exubit.familylocator.model.localdb.dao;

import android.arch.persistence.db.SupportSQLiteQuery;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.RawQuery;
import android.arch.persistence.room.Transaction;
import android.support.annotation.NonNull;

import com.exubit.familylocator.model.beans.GroupMember;
import com.exubit.familylocator.model.localdb.query.GetGroupMemberByIdQuery;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.schedulers.Schedulers;

@Dao
public interface LocalGroupMemberDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(@NonNull GroupMember... groupMembers);

    @Delete
    void delete(@NonNull GroupMember... groupMembers);

    @RawQuery(observedEntities = GroupMember.class)
    Maybe<GroupMember> getMaybe(@NonNull SupportSQLiteQuery query);

    @RawQuery(observedEntities = GroupMember.class)
    Flowable<GroupMember> getFlow(@NonNull SupportSQLiteQuery query);

    @Transaction
    default void update(@NonNull final GroupMember... groupMembers) {
        for (GroupMember groupMember : groupMembers) {
            SupportSQLiteQuery query = new GetGroupMemberByIdQuery(groupMember.getId());
            getMaybe(query).toSingle().subscribeOn(Schedulers.io())
                    .subscribe(member -> insert(member.update(groupMember)), e -> insert(groupMember));
        }
    }


}
