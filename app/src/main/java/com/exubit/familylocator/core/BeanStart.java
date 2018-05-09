package com.exubit.familylocator.core;

import javax.inject.Inject;

public class BeanStart {

    @Inject
    AppProcessor appProcessor;

    public BeanStart() {
        App.getAppComponent().inject(this);
    }
}
