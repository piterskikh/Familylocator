package com.exubit.familylocator.model.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.exubit.familylocator.bean.Member;
import com.exubit.familylocator.core.utils.Utils;
import com.exubit.familylocator.model.auxiliary.UpdateCodeMap;
import com.exubit.familylocator.model.dao.MemberDao;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class MemberLocalOperation {

    private final Utils utils;
    private final MemberDao memberDao;
    private final UpdateCodeMap updateCodeMapMap;
    private final Flowable<Member> memberFlow;

    public MemberLocalOperation(@NonNull final Utils utils
            , @NonNull final MemberDao memberDao
            , @NonNull final UpdateCodeMap updateCodeMapMap) {

        this.utils = utils;
        this.memberDao = memberDao;
        this.updateCodeMapMap = updateCodeMapMap;

        memberFlow = memberDao.getAllFlowable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    public <K> void setMember(@Nullable final String id
            , @Nullable final MemberRepository.Echo echo
            , @NonNull final Member.Fields field
            , @NonNull final K value
            , @Nullable final boolean... asynchronous) {

        Member member = new Member();


        Action action = () -> {

            final String updateCode = utils.getUniqueString();
            final long updateTime = utils.getCurrentLocalTime();
            boolean saveIsPossible = true;

            switch (field) {

                case LOCATION:
                    if (value instanceof Long) {
                        memberDao.updateLocation((Long) value, updateTime, updateCode, id);
                        break;
                    }
                case OBJECT:
                    if (value instanceof Member) {
                        memberDao.insertMember((Member) value);
                        break;
                    }
                case LOCATIONON:
                    if (value instanceof Boolean) {
                        memberDao.updateLocationOn((Boolean) value, updateTime, updateCode, id);
                        break;
                    }
                case TRACKERON:
                    if (value instanceof Boolean) {
                        memberDao.updateTrackerOn((Boolean) value, updateTime, updateCode, id);
                        break;
                    }
                default:
                    saveIsPossible = false;
            }

            if (saveIsPossible && echo != null) {
                updateCodeMapMap.getUpdateCodeMap().put(updateCode, echo);
            }
        };

        utils.executeAction(action, (asynchronous == null || asynchronous.length <= 0) || asynchronous[0]);
    }


    public Flowable<Member> getMemberFlow(boolean... asynchronous) {
       /* Flowable<Member> flow = memberFlow;
        if (utils.isFalse(asynchronous))
            flow.observeOn(AndroidSchedulers.mainThread());*/
        return memberFlow;
    }

}
