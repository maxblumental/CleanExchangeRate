package com.blumental.maxim.cleanexchangerate.di;

import com.blumental.maxim.cleanexchangerate.executor.ExecutorImpl;
import com.blumental.maxim.cleanexchangerate.presenter.InputMoneyPresenterImpl;
import com.blumental.maxim.cleanexchangerate.repository.RepositoryImpl;
import com.blumental.maxim.cleanexchangerate.repository.fixer_service.FixerServiceFactory;
import com.blumental.maxim.cleanexchangerate.view.ConvertedMoneyFragment;
import com.blumental.maxim.cleanexchangerate.view.InputMoneyFragment;
import com.blumental.maxim.cleanexchangerate.view.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {PresenterModule.class, FixerServiceFactory.class})
public interface ApplicationComponent {

    ExecutorImpl executor();

    RepositoryImpl repository();

    void inject(MainActivity mainActivity);

    void inject(InputMoneyFragment fragment);

    void inject(ConvertedMoneyFragment fragment);

    void inject(InputMoneyPresenterImpl presenter);
}
