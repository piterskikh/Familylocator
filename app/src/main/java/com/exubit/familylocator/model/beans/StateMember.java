package com.exubit.familylocator.model.beans;

import android.arch.persistence.room.Ignore;
import android.support.annotation.NonNull;

import com.google.firebase.database.PropertyName;
import com.google.firebase.database.ServerValue;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StateMember extends RtBaseEntity {

    private String nickName;
    private String phone;
    private String avatar;
    private long location;
    private long updateTime;
    private Boolean online;
    private Boolean trackerOn;
    private Boolean locationOn;
    private Boolean isDeleted;


    public StateMember() {
        online = online == null ? true : online;
        trackerOn = trackerOn == null ? true : trackerOn;
        locationOn = locationOn == null ? true : locationOn;
        isDeleted = isDeleted == null ? true : isDeleted;
    }

    @Ignore
    @NonNull
    @PropertyName("updateTime")
    public Map<String, String> getLastUpdateTimeNet() {
        return ServerValue.TIMESTAMP;
    }

    @Ignore
    public void setUpdateTimeNet(final long lastUpdateTimeNet) {
        this.updateTime = lastUpdateTimeNet;
    }

    @Ignore
    public void update(StateMember updatedStateMember) {

        if (updatedStateMember.getNickName() != null)
            nickName = updatedStateMember.getNickName();

        if (updatedStateMember.getPhone() != null)
            phone = updatedStateMember.getPhone();

        if (updatedStateMember.getAvatar() != null)
            avatar = updatedStateMember.getAvatar();

        if (updatedStateMember.getLocation() != 0)
            location = updatedStateMember.getLocation();

        if (updatedStateMember.getUpdateTime() != 0)
            updateTime = updatedStateMember.getUpdateTime();

        if (updatedStateMember.getUpdateTime() != 0)
            updateTime = updatedStateMember.getUpdateTime();

        if (updatedStateMember.getOnline() != null)
            updateTime = updatedStateMember.getUpdateTime();

        if (updatedStateMember.getTrackerOn() != null)
            trackerOn = updatedStateMember.getTrackerOn();

        if (updatedStateMember.getLocationOn() != null)
            locationOn = updatedStateMember.getLocationOn();

        if (updatedStateMember.getIsDeleted() != null)
            isDeleted = updatedStateMember.getIsDeleted();
    }

}
