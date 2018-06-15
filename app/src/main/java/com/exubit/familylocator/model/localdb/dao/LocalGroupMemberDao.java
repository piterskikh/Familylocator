package com.exubit.familylocator.model.localdb.dao;

import android.arch.persistence.db.SupportSQLiteQuery;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.RawQuery;
import android.arch.persistence.room.Transaction;

import com.exubit.familylocator.model.beans.GroupMember;
import com.exubit.familylocator.model.localdb.query.QueryGroupMemberById;

import io.reactivex.Flowable;

@Dao
public interface LocalGroupMemberDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void create(GroupMember... groupMembers);

    @Delete
    void delete(GroupMember... groupMembers);

    @RawQuery(observedEntities = GroupMember.class)
    Flowable<GroupMember> get(SupportSQLiteQuery query);

    @Transaction
    default void update(GroupMember... groupMembers) {
        for (GroupMember groupMember : groupMembers) {
            SupportSQLiteQuery query =  new QueryGroupMemberById(groupMember.getId());
            get(query).take(1).subscribe(member -> create(member.update(groupMember)), e -> create(groupMember));
        }
    }

}
