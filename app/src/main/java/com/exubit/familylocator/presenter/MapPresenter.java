package com.exubit.familylocator.presenter;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.exubit.familylocator.bean.PointD;
import com.exubit.familylocator.bean.UserLocation;
import com.exubit.familylocator.core.Utils;
import com.exubit.familylocator.core.UtilsImpl;
import com.exubit.familylocator.ui.uinterface.MapFragmentInterface;

import java.util.ArrayList;
import java.util.List;

@InjectViewState
public class MapPresenter extends MvpPresenter<MapFragmentInterface> {


    private List<UserLocation> arrayUserLocation = new ArrayList<>();
    private Utils utils = new UtilsImpl();


    public MapPresenter() {

        long cellId = utils.getCellId(52.281461, 104.266230);
        PointD pointD = utils.getLatLngFromCellId(cellId);

        arrayUserLocation.add(new UserLocation("Sergey", pointD.getX(), pointD.getY()));
    }

    @Override
    public void attachView(MapFragmentInterface view) {
        super.attachView(view);
        Log.d("zaza", "карта присоединилась");
        placeAllUsers();
    }

    @Override
    public void detachView(MapFragmentInterface view) {
        super.detachView(view);
        Log.d("zaza", "карта отсоединилась");
        placeAllUsers();
    }

    private void placeAllUsers(){
        for (UserLocation userLocation : arrayUserLocation) {
            getViewState().changeUserLocation(userLocation);
        }
    }


}
