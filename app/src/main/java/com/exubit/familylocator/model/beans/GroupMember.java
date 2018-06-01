package com.exubit.familylocator.model.beans;


import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity(tableName = "groupmbers")
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

}
