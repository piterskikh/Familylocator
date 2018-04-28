package com.exubit.familylocator.core;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.support.annotation.NonNull;

import com.exubit.familylocator.bean.PointD;
import com.exubit.familylocator.utils.s2.S2CellId;
import com.exubit.familylocator.utils.s2.S2LatLng;

public class UtilsImpl implements Utils {

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

    @NonNull
    private S2LatLng getS2LatLng(final double latDegrees, final double lngDegrees) {
        return S2LatLng.fromDegrees(latDegrees, lngDegrees);
    }
}
