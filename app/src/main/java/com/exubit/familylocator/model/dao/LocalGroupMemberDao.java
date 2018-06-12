package com.exubit.familylocator.model.dao;

import android.arch.persistence.db.SupportSQLiteQuery;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RawQuery;
import android.arch.persistence.room.Transaction;
import android.arch.persistence.room.Update;
import android.support.annotation.NonNull;

import com.exubit.familylocator.model.beans.GroupMember;
import com.exubit.familylocator.model.beans.SavedMember;
import com.exubit.familylocator.model.beans.StateMember;

import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface LocalGroupMemberDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertGroupMember(GroupMember... groupMembers);

    @Update
    void updateGroupMember(GroupMember groupMember);

    @Query("UPDATE groupmbers SET state_location = :location WHERE id =:id")
    void updateStateMemberLocation(String id, long location);

    @Query("UPDATE groupmbers SET location = :location WHERE id =:id")
    void updateGropMemberLocation(String id, long location);

    @Query("SELECT * FROM groupmbers where id =:id")
    Single<GroupMember> getGroupMemberSingle(String id);

    @Query("SELECT * FROM groupmbers where id =:id")
    GroupMember getGroupMember(String id);

    @Query("SELECT * FROM groupmbers")
    Flowable<GroupMember> getGroupMemberFlow();

    @RawQuery(observedEntities = GroupMember.class)
    GroupMember getUserViaQuery(SupportSQLiteQuery query);


    @Transaction
    default void updateStateMember(String id, StateMember stateMember) {
        GroupMember groupMember = getGroupMember(id);

        if (groupMember == null)
            insertGroupMember(getNewAppMember(id, stateMember));
        else {
            groupMember.setStateMember(stateMember);
            updateGroupMember(groupMember);
        }

    }

    @Transaction
    default void updateSavedMember(String id, SavedMember savedMember) {
        GroupMember groupMember = getGroupMember(id);

        if (groupMember == null)
            insertGroupMember(getNewAppMember(id, savedMember));
        else {
            groupMember.setSavedMember(savedMember);
            updateGroupMember(groupMember);
        }
    }

    @NonNull
    default GroupMember getNewAppMember(@NonNull final String id, final SavedMember savedMember) {
        GroupMember groupMember = new GroupMember();
        groupMember.setId(id);
        groupMember.setSavedMember(savedMember);
        return groupMember;
    }

    @NonNull
    default GroupMember getNewAppMember(@NonNull final String id, final StateMember stateMember) {
        GroupMember groupMember = new GroupMember();
        groupMember.setId(id);
        groupMember.setStateMember(stateMember);
        return groupMember;
    }

}
