package com.exubit.familylocator.view.fragment;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.exubit.familylocator.BuildConfig;
import com.exubit.familylocator.R;
import com.exubit.familylocator.bean.UserLocation;
import com.exubit.familylocator.core.App;
import com.exubit.familylocator.core.utils.Utils;
import com.exubit.familylocator.databinding.YandexMapBinding;
import com.exubit.familylocator.presenter.MapPresenter;
import com.exubit.familylocator.view.viewinterface.MapFragmentInterface;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.IconStyle;
import com.yandex.mapkit.map.MapObject;
import com.yandex.mapkit.map.MapObjectCollection;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.runtime.image.ImageProvider;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;


public class YandexFragment extends BaseFragment implements MapFragmentInterface {

    public static YandexFragment newInstance() {
        return new YandexFragment();
    }

    @InjectPresenter
    MapPresenter mapPresenter;

    @Inject
    Utils utils;

    private MapView mapView;
    private CoordinatorLayout mainLayout;
    private MapObjectCollection mapObjects;
    private final Map<String, PlacemarkMapObject> userMapArray = new HashMap<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getAppComponent().inject(this);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        MapKitFactory.setApiKey(BuildConfig.YANDEX_MAP_API_KEY);
        MapKitFactory.initialize(getActivity());


        YandexMapBinding binding = DataBindingUtil.inflate(inflater, R.layout.yandex_map, container, false);
        mapView = binding.mapView;
        mainLayout = binding.mainYandexMapLayout;
        initMap();

        return binding.getRoot();
    }

    private void initMap() {

        final Point TARGET_LOCATION = new Point(52.281461, 104.266230);

        mapObjects = mapView.getMap().getMapObjects().addCollection();
        mapView.getMap().move(
                new CameraPosition(TARGET_LOCATION, 17.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 5),
                null);
    }


    @Override
    public void changeUserLocation(@NonNull final UserLocation userLocation) {
        PlacemarkMapObject userMark = userMapArray.get(userLocation.userId);
        Point point = new Point(userLocation.latitude, userLocation.longitude);
        if (userMark != null)
            userMark.setGeometry(point);
        else {
            Bitmap bitmap = utils.getBitmap(R.drawable.ic_add_location_black_24dp, getActivity());
            userMapArray.put(userLocation.userId, setUserPlacemark(userLocation.userId, point, bitmap));
        }
    }

    @Override
    public void showConnectionSnackbar(boolean show) {


        Snackbar.make(mainLayout, "Пора кормить кота!", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }


    private PlacemarkMapObject setUserPlacemark(@NonNull final String userId, @NonNull final Point point, @NonNull final Bitmap bitmap) {
        IconStyle iconStyle = new IconStyle();
        PointF pointF = new PointF();
        pointF.set(0.5f, 1f);
        iconStyle.setAnchor(pointF);
        iconStyle.setFlat(true);

        PlacemarkMapObject userMark = mapObjects.addPlacemark(point, ImageProvider.fromBitmap(bitmap), iconStyle);
        userMark.setUserData(userId);
        userMark.addTapListener(this::userMarkClickListener);
        return userMark;
    }

    private boolean userMarkClickListener(@NonNull final MapObject mapObject, @NonNull final Point point) {
        final String userId = (String) mapObject.getUserData();
        return true;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.simple_menu, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.show_subtitle:

                showConnectionSnackbar(true);
               // mapPresenter.changeOneLocation();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        MapKitFactory.getInstance().onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }


}
