package com.exubit.familylocator.model.beans;

import android.arch.persistence.room.Ignore;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@IgnoreExtraProperties
@EqualsAndHashCode(exclude = {"id"})
abstract class RtBaseEntity {
    @Ignore
    @Getter(onMethod = @__({@Exclude}))
    private String id;
}
