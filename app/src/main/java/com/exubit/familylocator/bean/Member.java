package com.exubit.familylocator.bean;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ServerValue;

import java.util.Map;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Setter
@Entity
@IgnoreExtraProperties
@EqualsAndHashCode(exclude={"lastLocation", "updateCode", "baseSynchronized", "lat", "lng"})
public class Member {

    @NonNull
    @PrimaryKey
    @Getter(onMethod=@__({@Exclude}))
    private String id;
    @Getter
    private String changer;
    @Getter
    private long location;

    @Getter
    private boolean online;
    @Getter
    private boolean trackerOn;
    @Getter
    private boolean locationOn;

    @Ignore
    private long lastUpdateTime;

    private long lastUpdateTimeLong;

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
    public Map<String, String> getLastUpdateTime() {
        return ServerValue.TIMESTAMP;
    }

    @Exclude
    public long getLastUpdateTimeLong() {
        return lastUpdateTimeLong;
    }

    public void setLastUpdateTime(final long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
        this.lastUpdateTimeLong = this.lastUpdateTime;
    }




    public enum Fields {
        OBJECT,
        LOCATION,
        TRACKERON,
        LOCATIONON;
    }


}
