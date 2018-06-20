package com.exubit.familylocator.presenter;

import android.arch.persistence.db.SimpleSQLiteQuery;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.exubit.familylocator.core.App;
import com.exubit.familylocator.core.utils.Utils;
import com.exubit.familylocator.model.beans.GroupMember;
import com.exubit.familylocator.model.beans.SavedMember;
import com.exubit.familylocator.model.localdb.dao.LocalGroupMemberDao;
import com.exubit.familylocator.model.localdb.extension.GroupMemberDbMethods;
import com.exubit.familylocator.model.repository.MemberRepository;
import com.exubit.familylocator.view.viewinterface.DefineAppStateFragmentInterface;
import com.jakewharton.rxrelay2.BehaviorRelay;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class DefineAppStateFragmentPresenter extends MvpPresenter<DefineAppStateFragmentInterface> {

    @Inject
    GroupMemberDbMethods groupMemberDbMethods;


    public DefineAppStateFragmentPresenter() {
        App.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();


        GroupMember groupMember = new GroupMember();
        groupMember.setId("sergey");


        SavedMember savedMember = new SavedMember();
        savedMember.setNickName("loloweewew21");
        savedMember.setAvatar("732478.jpg");

        groupMember.setSavedMember(savedMember);




    /*    localGroupMemberDao.getAnotherOne("sergey1").subscribeOn(Schedulers.io()).subscribe(member ->{

            System.out.println(member.getId());

        }, e->{

           e.printStackTrace();

        });*/

        /*Completable.fromAction(() -> localGroupMemberDao.update(groupMember)).subscribeOn(Schedulers.io()).subscribe(() ->{

            String jj = "sasasa";

        }, e -> {

          e.printStackTrace();

       });*/


        GroupMember groupMember1 = null;

        //  SupportSQLiteQuery query = new GetGroupMemberByIdQuery("sergey1");

        String query = "SELECT * FROM groupmembers WHERE id = ? LIMIT 1";
        SimpleSQLiteQuery simpleSQLiteQuery = new SimpleSQLiteQuery(query, new Object[]{"sergey1"});

        groupMember1 = groupMemberDbMethods.getGroupMemberById("sergey");

                //.subscribeOn(Schedulers.io()).blockingGet(null);
                /*.map(gm -> {
                    if (gm.getId() == null)
                        return null;
                    return gm;
                })
                .toFlowable()
                .subscribeOn(Schedulers.io())
                .blockingFirst(null);*/

               /* .subscribe(gm -> {

                    Thread.sleep(1000);
                    System.out.println(gm.getId());

                }

                , e -> {

                    System.out.println("OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                }
        );*/

        System.out.println("Получено");









 /*     Completable.fromAction(() -> localGroupMemberDao.updateSavedMember("sergey", savedMember)).subscribeOn(Schedulers.io()).subscribe(() ->{

            String jj = "sasasa";

        }, e -> {

          e.printStackTrace();

       });*/


     /* localGroupMemberDao.getAppMember("sergey").subscribe(member->{
        GroupMember member1 = member;
    }, error->{

        String jj = "sasasa";
    });*/

/*        localGroupMemberDao.getGroupMemberFlow().subscribeOn(Schedulers.io()).subscribe(member ->{

            GroupMember member1 = member;

        }, error -> {

            error.printStackTrace();

        });*/

    }


}
