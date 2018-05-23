package com.exubit.familylocator.core.dagger;

import android.content.Context;

import com.exubit.familylocator.core.AppProcessor;
import com.exubit.familylocator.core.BeanStart;
import com.exubit.familylocator.presenter.DefineAppStateFragmentPresenter;
import com.exubit.familylocator.presenter.MapPresenter;
import com.exubit.familylocator.model.repository.MemberLocalOperation;
import com.exubit.familylocator.model.repository.MemberNetworkOperation;
import com.exubit.familylocator.model.repository.MemberRepository;
import com.exubit.familylocator.view.fragment.YandexFragment;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder setContext(Context context);

        AppComponent build();
    }

    void inject(MapPresenter mapPresenter);

    void inject(YandexFragment yandexFragment);

    void inject(MemberLocalOperation memberLocalOperation);

    void inject(MemberNetworkOperation memberNetworkOperation);

    void inject(MemberRepository memberRepository);

    void inject(BeanStart beanStart);

    void inject(AppProcessor appProcessor);

    void inject(DefineAppStateFragmentPresenter defineAppStateFragmentPresenter);

}
