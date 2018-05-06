package com.exubit.familylocator.core.dagger;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.NonNull;

import com.exubit.familylocator.core.utils.Utils;
import com.exubit.familylocator.core.utils.UtilsImpl;
import com.exubit.familylocator.model.dao.AppDatabase;
import com.exubit.familylocator.model.dao.MemberDao;
import com.exubit.familylocator.model.auxiliary.UpdateCodeMap;
import com.exubit.familylocator.model.repository.MemberLocalQuery;
import com.exubit.familylocator.model.repository.MemberNetworkQuery;
import com.exubit.familylocator.model.repository.MemberRepository;
import com.google.firebase.database.FirebaseDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Provides
    @Singleton
    @NonNull
    public Utils getUtils(@NonNull final Context context) {
        return new UtilsImpl(context);
    }

    @Provides
    @Singleton
    @NonNull
    public AppDatabase getRoomDb(@NonNull final Context context) {
        return Room.databaseBuilder(context,
                AppDatabase.class, "familyLocator").build();
    }

    @Provides
    @Singleton
    @NonNull
    public MemberDao getMemberDao(@NonNull final AppDatabase roomDb) {
        return roomDb.memberDao();
    }

    @Provides
    @Singleton
    @NonNull
    public FirebaseDatabase getFirebaseDatabase(@NonNull final AppDatabase roomDb) {
        return FirebaseDatabase.getInstance();
    }

    @Provides
    @Singleton
    @NonNull
    public UpdateCodeMap getUpdateCodeMap() {
        return new UpdateCodeMap();
    }

    @Provides
    @Singleton
    @NonNull
    public MemberLocalQuery getMemberLocalQuery(@NonNull final Utils utils
            , @NonNull final MemberDao memberDao
            , @NonNull final UpdateCodeMap updateCodeMap) {
        return new MemberLocalQuery(utils, memberDao, updateCodeMap);
    }

    @Provides
    @Singleton
    @NonNull
    public MemberNetworkQuery getMemberNetworkQuery(@NonNull final Utils utils
            , @NonNull FirebaseDatabase firebaseDatabase
            , @NonNull final UpdateCodeMap updateCodeMap) {
         return new MemberNetworkQuery(utils, firebaseDatabase, updateCodeMap);
    }

    @Provides
    @Singleton
    @NonNull
    public MemberRepository getMemberRepository(@NonNull final Utils utils
            , @NonNull final MemberLocalQuery memberLocalQuery
            , @NonNull final MemberNetworkQuery memberNetworkQuery) {
        return new MemberRepository(utils, memberLocalQuery, memberNetworkQuery);
    }


}
