package com.exubit.familylocator.model.repository;

import android.support.annotation.NonNull;

import com.exubit.familylocator.core.utils.Utils;
import com.exubit.familylocator.model.auxiliary.UpdateCodeMap;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MemberNetworkQuery {

    private final Utils utils;
    private final FirebaseDatabase firebaseDatabase;
    private final UpdateCodeMap updateCodeMapMap;
    private final DatabaseReference connectedRef;
    private MemberRepository memberRepository;


    public MemberNetworkQuery(@NonNull final Utils utils
            , @NonNull final FirebaseDatabase firebaseDatabase
            , @NonNull final UpdateCodeMap updateCodeMapMap) {

        this.utils = utils;
        this.firebaseDatabase = firebaseDatabase;
        this.updateCodeMapMap = updateCodeMapMap;
        connectedRef = firebaseDatabase.getReference(".info/connected");
    }

    public void setMemberRepository(@NonNull final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
}









 /*   private void setListeners() {


        connectedRef.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot snapshot) {

                boolean connected = snapshot.getValue(Boolean.class);

                if (connected) {


                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.err.println("Ошибка");
            }


        });
    }
}*/

