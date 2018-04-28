package com.exubit.familylocator;

import android.support.v4.app.Fragment;

import com.exubit.familylocator.core.SingleFragmentActivity;
import com.exubit.familylocator.ui.fragment.YandexFragment;

public class MainActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return YandexFragment.newInstance();
    }


}
