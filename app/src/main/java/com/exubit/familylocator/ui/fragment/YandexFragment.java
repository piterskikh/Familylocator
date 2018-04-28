package com.exubit.familylocator.ui.fragment;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.exubit.familylocator.BuildConfig;
import com.exubit.familylocator.R;
import com.exubit.familylocator.bean.UserLocation;
import com.exubit.familylocator.core.BaseFragment;
import com.exubit.familylocator.core.Utils;
import com.exubit.familylocator.core.UtilsImpl;
import com.exubit.familylocator.databinding.YandexMapBinding;
import com.exubit.familylocator.presenter.MapPresenter;
import com.exubit.familylocator.ui.uinterface.MapFragmentInterface;
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


public class YandexFragment extends BaseFragment implements MapFragmentInterface {

    public static YandexFragment newInstance() {
        return new YandexFragment();
    }

    @InjectPresenter
    MapPresenter mapPresenter;

    private MapView mapView;
    private MapObjectCollection mapObjects;
    private Utils utils = new UtilsImpl();
    private final Map<String, PlacemarkMapObject> userMapArray = new HashMap<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        MapKitFactory.setApiKey(BuildConfig.YANDEX_MAP_API_KEY);
        MapKitFactory.initialize(getActivity());


        YandexMapBinding binding = DataBindingUtil.inflate(inflater, R.layout.yandex_map, container, false);
        mapView = binding.mapView;
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
