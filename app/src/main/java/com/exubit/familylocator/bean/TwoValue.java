package com.exubit.familylocator.bean;

import android.support.annotation.NonNull;

public class TwoValue<K,V> {

    private K valueOne;
    private V valueTwo;

    public TwoValue() {
    }

    public TwoValue(K valueOne, V valueTwo) {
        this.valueOne = valueOne;
        this.valueTwo = valueTwo;
    }

    @NonNull
    public K getValueOne() {
        return valueOne;
    }

    public void setValueOne(@NonNull final K valueOne) {
        this.valueOne = valueOne;
    }

    @NonNull
    public V getValueTwo() {
        return valueTwo;
    }

    public void setValueTwo(@NonNull final V valueTwo) {
        this.valueTwo = valueTwo;
    }
}
