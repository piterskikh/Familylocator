package com.exubit.familylocator.model.localdb.extension;

import android.support.annotation.NonNull;

import com.exubit.familylocator.core.utils.Utils;
import com.exubit.familylocator.model.beans.GroupMember;
import com.exubit.familylocator.model.localdb.dao.LocalGroupMemberDao;
import com.exubit.familylocator.model.localdb.query.GetGroupMemberByIdQuery;

import java.util.NoSuchElementException;

import io.reactivex.schedulers.Schedulers;

public class GroupMemberDbMethods {

    private final LocalGroupMemberDao dao;
    private final Utils utils;

    public GroupMemberDbMethods(@NonNull final LocalGroupMemberDao dao, @NonNull final Utils utils) {
        this.dao = dao;
        this.utils = utils;
    }

    public GroupMember getGroupMemberById(@NonNull final String id) {

        GetGroupMemberByIdQuery query = new GetGroupMemberByIdQuery(id);

        try {
            return dao.getMaybe(query).toSingle().subscribeOn(Schedulers.io()).blockingGet();
        } catch (NoSuchElementException e) {
            return null; //item is not found, it's ok
        }
    }

    public void update(@NonNull final GroupMember... groupMembers) {
        utils.executeAction(() -> dao.update(groupMembers));
    }


}
