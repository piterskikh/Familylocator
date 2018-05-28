package com.exubit.familylocator.bean;

import android.support.annotation.NonNull;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class UserGroup {

    private String userGroupId;

    public UserGroup() {
    }

    public UserGroup(final String userGroupId) {
        this.userGroupId = userGroupId;
    }


}
