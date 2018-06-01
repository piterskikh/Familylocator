package com.exubit.familylocator.core.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.location.Location;
import android.support.annotation.NonNull;

import com.exubit.familylocator.bean.PointD;

import java.util.Map;

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

    boolean isFalse(boolean[] array);

    SharedPreferences.Editor getEditor();

    void setLongSettings(String key, long value);

    long getLongSettings(String key);

    String getUserName();

    <K,V> Map<K,V> getMergedMap(@NonNull final Map<K,V>... maps);



}
