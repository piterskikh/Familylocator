package com.exubit.familylocator.bean;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;
import com.google.firebase.database.ServerValue;

import java.util.Map;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Entity
@IgnoreExtraProperties
@EqualsAndHashCode(exclude={"lastLocation", "updateCode", "baseSynchronized", "lat", "lng"})
public class Member {

    @NonNull
    @PrimaryKey
    @Getter(onMethod=@__({@Exclude}))
    private String id;

    private long location;
    private boolean online;
    private boolean trackerOn;
    private boolean locationOn;

    @Getter(onMethod=@__({@Ignore}))
    private String editor;
    @Ignore
    private long lastUpdateTimeNet;

    @Getter(onMethod=@__({@Exclude}))
    private long lastUpdateTime;
    @Getter(onMethod=@__({@Exclude}))
    private long lastLocation;
    @Getter(onMethod=@__({@Exclude}))
    private int updateCode;
    @Getter(onMethod=@__({@Exclude}))
    private boolean baseSynchronized;
    @Getter(onMethod=@__({@Exclude, @Ignore}))
    private double lat, lng;

    public Member() {
    }

    @Ignore
    public Member(@NonNull final String id
            , final long location) {
        this.id = id;
        this.location = location;
    }

    @Ignore
    @PropertyName("lastUpdateTime")
    public Map<String, String> getLastUpdateTimeNet() {
        return ServerValue.TIMESTAMP;
    }


    @Ignore
    public void setLastUpdateTimeNet(final long lastUpdateTimeNet) {
        this.lastUpdateTime = lastUpdateTimeNet;
    }




    public enum Fields {
        OBJECT,
        LOCATION,
        TRACKERON,
        LOCATIONON;
    }


}
