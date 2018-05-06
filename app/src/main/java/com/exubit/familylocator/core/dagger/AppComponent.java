package com.exubit.familylocator.core.dagger;

import android.content.Context;

import com.exubit.familylocator.presenter.MapPresenter;
import com.exubit.familylocator.model.repository.MemberLocalQuery;
import com.exubit.familylocator.model.repository.MemberNetworkQuery;
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

    void inject(MemberLocalQuery memberLocalQuery);

    void inject(MemberNetworkQuery memberNetworkQuery);

    void inject(MemberRepository memberRepository);

}
