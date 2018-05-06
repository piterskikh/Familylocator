package com.exubit.familylocator.bean;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.firebase.database.Exclude;

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
    @Exclude
    private long lastLocation;
    @Exclude
    @NonNull
    private String updateCode;
    private long lastUpdateTime;
    private boolean online;
    private boolean trackerOn;
    private boolean locationOn;
    @Exclude
    private boolean baseSynchronized;

    @Exclude
    @Ignore
    double lat, lng;

    public Member() {
    }

    @Ignore
    public Member(@NonNull final String id
            , final long location
            , @NonNull final String updateCode) {

        this.id = id;
        this.location = location;
        this.updateCode = updateCode;
    }


    public enum Fields {
        OBJECT,
        LOCATION,
        TRACKERON,
        LOCATIONON;
    }


}
