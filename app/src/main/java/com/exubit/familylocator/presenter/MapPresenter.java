package com.exubit.familylocator.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.exubit.familylocator.bean.Member;
import com.exubit.familylocator.bean.PointD;
import com.exubit.familylocator.bean.UserLocation;
import com.exubit.familylocator.core.App;
import com.exubit.familylocator.core.Utils;
import com.exubit.familylocator.core.UtilsImpl;
import com.exubit.familylocator.ui.uinterface.MapFragmentInterface;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class MapPresenter extends MvpPresenter<MapFragmentInterface> {

    private final Flowable<Member> MEMBERFLOW = App.getDb().memberDao().getAllFlowable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final DatabaseReference connectedRef = database.getReference(".info/connected");
    private final DatabaseReference myConnectionsRef = database.getReference("users/sergey/connections");
    private final DatabaseReference lastOnlineRef = database.getReference("/users/sergey/lastOnline");
    private Utils utils = new UtilsImpl();
    private Disposable subscription;


    public MapPresenter() {

        Member member = new Member("sergey", utils.getCellId(52.281461, 104.266230));


        Completable.fromAction(() -> {

            App.getDb().memberDao().insertMember(member);


        })
                .subscribeOn(Schedulers.io())
                .blockingAwait();

        setListeners();


    }

    private void setListeners() {

        /*DatabaseReference connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");
        connectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                boolean connected = snapshot.getValue(Boolean.class);
                if (connected) {
                    System.out.println("connected");
                } else {
                    System.out.println("not connected");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.err.println("Listener was cancelled");
            }
        });*/

        connectedRef.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot snapshot) {

                boolean connected = snapshot.getValue(Boolean.class);

                if (connected) {

                    DatabaseReference con = myConnectionsRef.push();
                    con.setValue(Boolean.TRUE);
                    lastOnlineRef.setValue(2);

                    con.onDisconnect().removeValue();
                    lastOnlineRef.onDisconnect().setValue(ServerValue.TIMESTAMP);


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.err.println("Ошибка");
            }


        });
    }



    @Override
    public void attachView(MapFragmentInterface view) {
        super.attachView(view);
        Log.d("zaza", "карта присоединилась");
        subscription = MEMBERFLOW.subscribe(this::placeMember);
    }

    @Override
    public void detachView(MapFragmentInterface view) {
        super.detachView(view);
        Log.d("zaza", "карта отсоединилась");
        subscription.dispose();
    }

    private void placeMember(@NonNull final Member member) {
        PointD pointD = utils.getLatLngFromCellId(member.getLocation());
        getViewState().changeUserLocation(new UserLocation(member.getId(), pointD.getX(), pointD.getY()));
    }


    public void changeOneLocation() {

        Member member = new Member();
        member.setId("sergey");
        member.setLocation(utils.getCellId(52.288102, 104.264539));

        Completable.fromAction(() -> {

                    App.getDb().memberDao().insertMember(member);
                }

        ).subscribeOn(Schedulers.io()).subscribe();


    }


}
