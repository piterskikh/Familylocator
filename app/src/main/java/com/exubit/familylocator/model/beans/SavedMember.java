package com.exubit.familylocator.model.beans;


import android.arch.persistence.room.Ignore;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SavedMember extends RtBaseEntity {

    private String nickName;
    private String avatar;

    @Ignore
    public void update(SavedMember updatedSavedMember) {

        if (updatedSavedMember.getNickName() != null)
            nickName = updatedSavedMember.getNickName();

        if (updatedSavedMember.getAvatar() != null)
            avatar = updatedSavedMember.getAvatar();
    }
}
