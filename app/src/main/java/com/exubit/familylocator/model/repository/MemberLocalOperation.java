package com.exubit.familylocator.model.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.exubit.familylocator.bean.Member;
import com.exubit.familylocator.core.utils.Utils;


import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class MemberLocalOperation {

    private final Utils utils;

    //private final Flowable<Member> MEMBERFLOW;

    public MemberLocalOperation(@NonNull final Utils utils) {


        this.utils = utils;
       /* this.memberDao = memberDao;
        MEMBERFLOW = memberDao.getAllFlowable().subscribeOn(Schedulers.io());*/

    }

    public <K> void setMember(@Nullable final String id
            , @NonNull final Member.FirebaseFields field
            , @NonNull final K value
            , @Nullable final boolean... asynchronous) {


        Action action = () -> {


           /* switch (field) {

                case LOCATION:
                    if (value instanceof Long)
                        memberDao.updateLocation((Long) value, id);
                    break;
                *//*case :
                    if (value instanceof Member)
                        memberDao.insertMember((Member) value);
                    break;*//*

                case LOCATIONON:
                    if (value instanceof Boolean)
                        memberDao.updateLocationOn((Boolean) value, id);
                    break;

                case TRACKERON:
                    if (value instanceof Boolean)
                        memberDao.updateTrackerOn((Boolean) value, id);
                    break;
            }*/

        };

        utils.executeAction(action, (asynchronous == null || asynchronous.length <= 0) || asynchronous[0]);
    }


    public Flowable<Member> getMemberFlow(boolean... asynchronous) {
      /*  if (utils.isFalse(asynchronous))
            return MEMBERFLOW.observeOn(AndroidSchedulers.mainThread());*/
        return null;
    }

}
