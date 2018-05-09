package com.exubit.familylocator.model.repository;

import android.support.annotation.NonNull;

import com.exubit.familylocator.bean.Member;
import com.exubit.familylocator.core.utils.Utils;
import com.exubit.familylocator.model.auxiliary.UpdateCodeMap;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.time.Instant;
import java.util.Date;

public class MemberNetworkOperation {

    private final Utils utils;
    private final DatabaseReference databaseReference ;
    private final UpdateCodeMap updateCodeMapMap;
    private final DatabaseReference connectedRef;
    private final DatabaseReference usersRef;
    private MemberRepository memberRepository;





    public MemberNetworkOperation(@NonNull final Utils utils
            , @NonNull final DatabaseReference databaseReference
            , @NonNull final UpdateCodeMap updateCodeMapMap) {

        this.utils = utils;
        this.databaseReference = databaseReference;
        this.updateCodeMapMap = updateCodeMapMap;
        connectedRef = databaseReference.child(".info/connected");
        usersRef = databaseReference.child("users");
    }

    public void setMemberRepository(@NonNull final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void setMemberToNet(Member member){
              usersRef.child(member.getId()).setValue(member, this::setMemberCompletionListener);
    }

    private void setMemberCompletionListener(DatabaseError databaseError, DatabaseReference databaseReference) {
        int i =1;
       // databaseReference.getKey();
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

