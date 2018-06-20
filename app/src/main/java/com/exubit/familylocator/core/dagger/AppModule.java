package com.exubit.familylocator.core.dagger;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.exubit.familylocator.core.AppProcessor;
import com.exubit.familylocator.core.utils.Utils;
import com.exubit.familylocator.core.utils.UtilsImpl;
import com.exubit.familylocator.model.FirebaseRealtimeDatabase;
import com.exubit.familylocator.model.localdb.dao.AppDatabase;
import com.exubit.familylocator.model.localdb.dao.LocalGroupMemberDao;
import com.exubit.familylocator.model.localdb.extension.GroupMemberDbMethods;
import com.exubit.familylocator.model.repository.MemberLocalOperation;
import com.exubit.familylocator.model.repository.MemberNetworkOperation;
import com.exubit.familylocator.model.repository.MemberRepository;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jakewharton.rxrelay2.BehaviorRelay;

import javax.inject.Named;
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
    public SharedPreferences getSharedPreferences(@NonNull final Context context) {
        return context.getSharedPreferences("FamilyLocator", Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    @NonNull
    public SharedPreferences.Editor getEditor(@NonNull final Context context) {
        SharedPreferences sPref = context.getSharedPreferences("FamilyLocator", Context.MODE_PRIVATE);
        return sPref.edit();
    }


    @Provides
    @Singleton
    @NonNull
    public GroupMemberDbMethods getGroupMemberDbMethods(@NonNull final AppDatabase roomDb, @NonNull final Utils utils) {
        return new GroupMemberDbMethods(roomDb.getGroupMemberDao(), utils);
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
    public MemberNetworkOperation getMemberNetworkOperation(@NonNull final Utils utils
            , @NonNull DatabaseReference databaseReference) {
        return new MemberNetworkOperation(utils, databaseReference);
    }

    @Provides
    @Singleton
    @NonNull
    public FirebaseRealtimeDatabase getUserFirebaseDao(@NonNull DatabaseReference databaseReference) {
        return new FirebaseRealtimeDatabase(databaseReference);
    }

    @Provides
    @Singleton
    @NonNull
    public MemberLocalOperation getMemberLocalOperation(@NonNull final Utils utils) {
        return new MemberLocalOperation(utils);
    }

    @Provides
    @Singleton
    @NonNull
    public MemberRepository getMemberRepository(@NonNull final Utils utils
            , @NonNull final MemberLocalOperation memberLocalOperation
            , @NonNull final MemberNetworkOperation memberNetworkOperation) {
        return new MemberRepository(utils, memberLocalOperation, memberNetworkOperation);
    }

    @Provides
    @Singleton
    @NonNull
    public AppProcessor getMemberProcessor() {
        return new AppProcessor();
    }

    @Provides
    @Singleton
    @NonNull
    @Named("mapActive")
    public BehaviorRelay<Boolean> getMapActiveBus() {
        return BehaviorRelay.createDefault(false);
    }

    @Provides
    @Singleton
    @NonNull
    @Named("baseOnline")
    public BehaviorRelay<Boolean> getBaseOnlineState() {
        return BehaviorRelay.createDefault(false);
    }

}
