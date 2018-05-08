package com.exubit.familylocator.bean;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.firebase.database.Exclude;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@EqualsAndHashCode(exclude={"lastLocation", "updateCode", "baseSynchronized", "lat", "lng"})
public class Member {

    @NonNull
    @PrimaryKey
    private String id;
    private String changer;
    private long location;
    private long lastUpdateTime;
    private boolean online;
    private boolean trackerOn;
    private boolean locationOn;

    @Exclude
    private long lastLocation;
    @Exclude
    private int updateCode;
    @Exclude
    private boolean baseSynchronized;

    @Exclude
    @Ignore
    double lat, lng;

    public Member() {
    }

    @Ignore
    public Member(@NonNull final String id
            , final long location) {
        this.id = id;
        this.location = location;
    }


    public enum Fields {
        OBJECT,
        LOCATION,
        TRACKERON,
        LOCATIONON;
    }


}
