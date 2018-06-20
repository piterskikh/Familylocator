package com.exubit.familylocator.model.beans;


import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity(tableName = "groupmembers")
public class GroupMember {

    @NonNull
    @PrimaryKey
    private String id;
    private long location;
    private String currentAddress;
    @Embedded(prefix = "saved_")
    private SavedMember savedMember;
    @Embedded(prefix = "state_")
    private StateMember stateMember;

    public void setSavedMember(final SavedMember savedMember) {
        this.savedMember = savedMember;
        if (savedMember != null)
            savedMember.setId(getId());
    }

    public void setStateMember(final StateMember stateMember) {
        this.stateMember = stateMember;
        if (stateMember != null)
            stateMember.setId(getId());
    }

    @Ignore
    public GroupMember update(GroupMember updateGroupMember) {

        if (updateGroupMember.getLocation() != 0)
            location = updateGroupMember.getLocation();

        if (updateGroupMember.getCurrentAddress() != null)
            currentAddress = updateGroupMember.getCurrentAddress();

        if (updateGroupMember.getSavedMember() != null) {
            if (savedMember == null)
                savedMember = updateGroupMember.getSavedMember();
            else
                savedMember.update(updateGroupMember.getSavedMember());
        }

        if (updateGroupMember.getStateMember() != null) {
            if (stateMember == null)
                stateMember = updateGroupMember.getStateMember();
            else
                stateMember.update(updateGroupMember.getStateMember());
        }

        return this;
    }

}
