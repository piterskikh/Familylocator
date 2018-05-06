package com.exubit.familylocator.core.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.support.annotation.NonNull;

import com.exubit.familylocator.bean.PointD;
import com.exubit.familylocator.core.utils.s2.S2CellId;
import com.exubit.familylocator.core.utils.s2.S2LatLng;

import java.util.Date;
import java.util.UUID;

import io.reactivex.Completable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class UtilsImpl implements Utils {

    private final Context CONTEXT;

    public UtilsImpl(Context context) {
        this.CONTEXT = context;
    }

    @Override
    public Bitmap getBitmap(final int drawableRes, @NonNull final Context context) {
        Context appContext = context.getApplicationContext();
        Drawable drawable = appContext.getResources().getDrawable(drawableRes);
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    @Override
    public long getCellId(@NonNull final PointD pointD) {
        return S2CellId.fromLatLng(getS2LatLng(pointD.getX(), pointD.getY())).id();
    }

    @Override
    public long getCellId(Location location) {
        return getCellId(new PointD(location.getLatitude(), location.getLongitude()));
    }

    @Override
    public long getCellId(final double lat, final double lng) {
        return getCellId(new PointD(lat, lng));
    }

    @Override
    public PointD getLatLngFromCellId(long cellId) {
        S2LatLng s2LatLng = new S2CellId(cellId).toLatLng();
        return new PointD(s2LatLng.latDegrees(), s2LatLng.lngDegrees());
    }

    @Override
    public void executeAction(@NonNull final Action action) {
        executeAction(action, true);
    }

    @Override
    public void executeAction(@NonNull final Action action, final boolean asynchronous) {
        Completable localAction = Completable.fromAction(action).subscribeOn(Schedulers.io());
        if (asynchronous)
            localAction.subscribe();
        else
            localAction.blockingAwait();
    }

    @Override
    public long getCurrentLocalTime() {
        return new Date().getTime();
    }

    @Override
    public String getUniqueString() {
        return UUID.randomUUID().toString();
    }

    @Override
    public Context getContext() {
        return CONTEXT;
    }

    @NonNull
    private S2LatLng getS2LatLng(final double latDegrees, final double lngDegrees) {
        return S2LatLng.fromDegrees(latDegrees, lngDegrees);
    }


}
