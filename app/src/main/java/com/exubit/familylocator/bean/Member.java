package com.exubit.familylocator.bean;



import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;
import com.google.firebase.database.ServerValue;

import java.util.Map;

import io.reactivex.annotations.NonNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Entity
@IgnoreExtraProperties
@EqualsAndHashCode(exclude = {"baseHashCode", "lat", "lng"})
public class Member {

    @NonNull
    @PrimaryKey
    @Getter(onMethod = @__({@Exclude}))
    private String id;
    private String nickName;
    private String phone;
    private String avatar;
    private long location;
    private boolean online;
    private boolean trackerOn;
    private boolean locationOn;
    @Getter(onMethod = @__({@Exclude}))
    private long lastUpdateTime;
    @Getter(onMethod = @__({@Exclude}))
    private int baseHashCode;
    @Getter(onMethod = @__({@Exclude, @Ignore}))
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

    public enum FirebaseFields {

        LOCATION("location"),
        NICKNAME("nickName"),
        PHONE("phone"),
        ONLINE("online"),
        TRACKERON("trackerOn"),
        LOCATIONON("locationOn"),
        ISDELETED("isDeleted"),
        LASTUPDATETIME("lastUpdateTime");

        private String fbField;

        FirebaseFields(String fbField) {
            this.fbField = fbField;
        }

        public String getFbField() {
            return fbField;
        }
    }


}
