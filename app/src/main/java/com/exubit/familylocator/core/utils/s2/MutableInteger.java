package com.exubit.familylocator.core.utils.s2;

public class MutableInteger {

    private int value;
    private Integer cachedIntegerValue = null;

    public MutableInteger(final int i) {
        value = i;
    }

    public int intValue() {
        return value;
    }

    public Integer integerValue() {
        if (cachedIntegerValue == null) {
            cachedIntegerValue = intValue();
        }
        return cachedIntegerValue;
    }

    @Override
    public boolean equals(final Object o) {
        return o instanceof MutableInteger && ((MutableInteger) o).value == this.value;
    }

    @Override
    public int hashCode() {
        return integerValue().hashCode();
    }

    public void setValue(final int value) {
        this.value = value;
        cachedIntegerValue = null;
    }

    public void increment() {
        add(1);
    }

    public void add(final int amount) {
        setValue(value + amount);
    }

    public void decrement() {
        subtract(1);
    }

    public void subtract(final int amount) {
        add(amount * -1);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
