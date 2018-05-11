package com.exubit.familylocator.model.repository;

import android.support.annotation.NonNull;

import com.exubit.familylocator.bean.Member;
import com.exubit.familylocator.core.utils.Utils;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import java.util.UUID;

public class MemberNetworkOperation {

    public static final String MAXUPDATETIME = "MAXUPDATETIME";
    public static final String EDITORKEY = UUID.randomUUID().toString();
    private final Utils utils;
    private final DatabaseReference connectedRef;
    private DatabaseReference usersRef;


    public MemberNetworkOperation(@NonNull final Utils utils
            , @NonNull final DatabaseReference databaseReference) {
        this.utils = utils;
        connectedRef = databaseReference.child(".info/connected");
        usersRef = databaseReference.child("users");
    }


    public void setMemberToNet(Member member) {
        member.setEditor(EDITORKEY);
        usersRef.child(member.getId()).setValue(member, this::setMemberCompletionListener);
    }

    private void setMemberCompletionListener(DatabaseError databaseError, DatabaseReference databaseReference) {
        int i = 1;
        // databaseReference.getKey();
    }


    public Query getUsersQuery() {
        return usersRef.orderByChild("lastUpdateTime").startAt(utils.getLongSettings(MAXUPDATETIME)+1, "lastUpdateTime");
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

