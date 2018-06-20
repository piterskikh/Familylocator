package com.exubit.familylocator.model.localdb.extension;

import android.support.annotation.NonNull;

import com.exubit.familylocator.model.beans.GroupMember;
import com.exubit.familylocator.model.localdb.dao.LocalGroupMemberDao;
import com.exubit.familylocator.model.localdb.query.GetGroupMemberByIdQuery;

import java.util.NoSuchElementException;

import io.reactivex.Completable;
import io.reactivex.schedulers.Schedulers;

public class GroupMemberDbMethods {

    private final LocalGroupMemberDao dao;

    public GroupMemberDbMethods(@NonNull final LocalGroupMemberDao dao) {
        this.dao = dao;
    }

    public GroupMember getGroupMemberById(@NonNull final String id) {

        GetGroupMemberByIdQuery query = new GetGroupMemberByIdQuery(id);

        try {
            return dao.getMaybe(query).toSingle().subscribeOn(Schedulers.io()).blockingGet();
        } catch (NoSuchElementException e) {
            return null; //item is not found, it's ok
        }
    }

    public void update(@NonNull final GroupMember... groupMembers){
        Completable.fromAction(() -> dao.update(groupMembers)).subscribeOn(Schedulers.io()).subscribe();
    }


}
