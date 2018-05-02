package com.exubit.familylocator.bean;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Member {

    @NonNull
    @PrimaryKey
    private String id;

    private long location;

    public Member() {
    }

    public Member(@NonNull String id, long location) {
        this.id = id;
        this.location = location;

    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public long getLocation() {
        return location;
    }

    public void setLocation(long location) {
        this.location = location;
    }
}
