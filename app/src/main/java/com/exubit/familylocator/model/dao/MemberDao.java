package com.exubit.familylocator.model.dao;

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

    @Query("SELECT count(*) FROM member")
    int memberNumber();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMember(Member... members);

    @Query("UPDATE member SET location = :location, updateCode = :updateCode WHERE id =:id")
    void updateLocation(long location, String updateCode, String id);

    @Query("UPDATE member SET trackerOn = :trackerOn, updateCode = :updateCode WHERE id =:id")
    void updateTrackerOn(boolean trackerOn, String updateCode, String id);

    @Query("UPDATE member SET locationOn = :locationOn, updateCode = :updateCode WHERE id =:id")
    void updateLocationOn(boolean locationOn, String updateCode, String id);

    @Delete
    void deleteMember(Member... members);

    @Query("SELECT * FROM member")
    LiveData<List<Member>> getAllLiveData();

    @Query("SELECT * FROM member")
    Flowable<Member> getAllFlowable();


}
