package com.exubit.familylocator.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.exubit.familylocator.bean.Member;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface MemberDao {

    @Query("SELECT * FROM member")
    List<Member> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMember(Member... members);

    @Delete
    void deleteMember(Member... members);

    @Query("SELECT * FROM member")
    LiveData<List<Member>> getAllLiveData();

    @Query("SELECT * FROM member")
    Flowable<Member> getAllFlowable();


}
