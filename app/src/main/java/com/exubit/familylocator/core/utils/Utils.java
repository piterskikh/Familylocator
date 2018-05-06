package com.exubit.familylocator.core.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.location.Location;

import com.exubit.familylocator.bean.PointD;

import io.reactivex.functions.Action;

public interface Utils {

    Bitmap getBitmap(int drawableRes, Context context);
    long getCellId(Location location);
    long getCellId(PointD pointD);
    long getCellId(double lat, double lng);
    PointD getLatLngFromCellId(long cellId);
    void executeAction(Action action);
    void executeAction(Action action, boolean asynchronous);
    long getCurrentLocalTime();
    String getUniqueString();
    Context getContext();

}
