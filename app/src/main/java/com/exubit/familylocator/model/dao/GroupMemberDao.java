package com.exubit.familylocator.model.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import android.arch.persistence.room.Update;
import android.support.annotation.NonNull;

import com.exubit.familylocator.model.beans.GroupMember;
import com.exubit.familylocator.model.beans.SavedMember;
import com.exubit.familylocator.model.beans.StateMember;

import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public abstract class GroupMemberDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void insertGroupMember(GroupMember... groupMembers);

    @Update
    abstract void updateGroupMember(GroupMember groupMember);

    @Query("UPDATE groupmbers SET state_location = :location WHERE id =:id")
    abstract void updateStateMemberLocation(String id, long location);

    @Query("UPDATE groupmbers SET location = :location WHERE id =:id")
    public abstract void updateGropMemberLocation(String id, long location);

    @Query("SELECT * FROM groupmbers where id =:id")
    abstract Single<GroupMember> getGroupMemberSingle(String id);

    @Query("SELECT * FROM groupmbers where id =:id")
    abstract GroupMember getGroupMember(String id);

    @Query("SELECT * FROM groupmbers")
    public abstract Flowable<GroupMember> getGroupMemberFlow();


    @Transaction
    public void updateStateMember(String id, StateMember stateMember) {
        GroupMember groupMember = getGroupMember(id);

        if ( groupMember == null)
            insertGroupMember(getNewAppMember(id, stateMember));
        else {
            groupMember.setStateMember(stateMember);
            updateGroupMember(groupMember);
        }
    }

    @Transaction
    public void updateSavedMember(String id, SavedMember savedMember) {
        GroupMember groupMember = getGroupMember(id);

        if ( groupMember == null)
            insertGroupMember(getNewAppMember(id, savedMember));
        else {
            groupMember.setSavedMember(savedMember);
            updateGroupMember(groupMember);
        }
    }

    @NonNull
    private GroupMember getNewAppMember(@NonNull final String id, final SavedMember savedMember) {
        GroupMember groupMember = new GroupMember();
        groupMember.setId(id);
        groupMember.setSavedMember(savedMember);
        return groupMember;
    }

    @NonNull
    private GroupMember getNewAppMember(@NonNull final String id, final StateMember stateMember) {
        GroupMember groupMember = new GroupMember();
        groupMember.setId(id);
        groupMember.setStateMember(stateMember);
        return groupMember;
    }

}
