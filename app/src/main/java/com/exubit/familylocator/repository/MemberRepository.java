package com.exubit.familylocator.repository;

import android.arch.lifecycle.LiveData;

import com.exubit.familylocator.bean.Member;
import com.exubit.familylocator.core.App;
import com.exubit.familylocator.dao.AppDatabase;
import com.exubit.familylocator.dao.MemberDao;

import java.util.List;

public class MemberRepository {

    private MemberDao memberDao;
    private LiveData<List<Member>> allMemebers;
    private AppDatabase db;

    public MemberRepository() {
       /* db = App.getDb();
        memberDao = db.memberDao();
        allMemebers = memberDao.getAllLiveData();*/
    }


}
