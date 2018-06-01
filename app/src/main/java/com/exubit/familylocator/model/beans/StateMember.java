package com.exubit.familylocator.model.beans;

import android.arch.persistence.room.Ignore;
import android.support.annotation.NonNull;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.PropertyName;
import com.google.firebase.database.ServerValue;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StateMember extends RtBaseEntity{

    private String nickName;
    private String phone;
    private String avatar;
    private long location;
    private long updateTime;
    private boolean online;
    private boolean trackerOn;
    private boolean locationOn;
    private boolean isDeleted;


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
}
