package com.exubit.familylocator;

import android.support.v4.app.Fragment;

import com.exubit.familylocator.view.activity.SingleFragmentActivity;
import com.exubit.familylocator.view.fragment.YandexFragment;

public class MainActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return YandexFragment.newInstance();
    }


}
