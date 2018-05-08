package com.exubit.familylocator.core.dagger;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.NonNull;

import com.exubit.familylocator.core.utils.Utils;
import com.exubit.familylocator.core.utils.UtilsImpl;
import com.exubit.familylocator.model.dao.AppDatabase;
import com.exubit.familylocator.model.dao.MemberDao;
import com.exubit.familylocator.model.auxiliary.UpdateCodeMap;
import com.exubit.familylocator.model.repository.MemberLocalOperation;
import com.exubit.familylocator.model.repository.MemberNetworkOperation;
import com.exubit.familylocator.model.repository.MemberRepository;
import com.google.firebase.database.DatabaseReference;
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
    public DatabaseReference getFirebaseDatabase() {
        return FirebaseDatabase.getInstance().getReference();
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
    public MemberLocalOperation getMemberLocalOperation(@NonNull final Utils utils
            , @NonNull final MemberDao memberDao
            , @NonNull final UpdateCodeMap updateCodeMap) {
        return new MemberLocalOperation(utils, memberDao, updateCodeMap);
    }

    @Provides
    @Singleton
    @NonNull
    public MemberNetworkOperation getMemberNetworkOperation(@NonNull final Utils utils
            , @NonNull DatabaseReference databaseReference
            , @NonNull final UpdateCodeMap updateCodeMap) {
         return new MemberNetworkOperation(utils, databaseReference, updateCodeMap);
    }

    @Provides
    @Singleton
    @NonNull
    public MemberRepository getMemberRepository(@NonNull final Utils utils
            , @NonNull final MemberLocalOperation memberLocalOperation
            , @NonNull final MemberNetworkOperation memberNetworkOperation) {
        return new MemberRepository(utils, memberLocalOperation, memberNetworkOperation);
    }


}
