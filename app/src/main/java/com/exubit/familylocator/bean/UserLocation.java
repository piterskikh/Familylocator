package com.exubit.familylocator.bean;

public class UserLocation {
    public final String userId;
    public final double latitude, longitude;

    public UserLocation(String userId, double latitude, double longitude) {
        this.userId = userId;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
