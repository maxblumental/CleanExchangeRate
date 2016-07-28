package com.blumental.maxim.cleanexchangerate.di;

import com.blumental.maxim.cleanexchangerate.presenter.ConvertedMoneyPresenter;
import com.blumental.maxim.cleanexchangerate.presenter.ConvertedMoneyPresenterImpl;
import com.blumental.maxim.cleanexchangerate.presenter.InputMoneyPresenter;
import com.blumental.maxim.cleanexchangerate.presenter.InputMoneyPresenterImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class PresenterModule {

    @Provides
    @Singleton
    ConvertedMoneyPresenter getConvertedMoneyPresenter() {
        return new ConvertedMoneyPresenterImpl();
    }

    @Provides
    @Singleton
    InputMoneyPresenter getInputMoneyPresenter() {
        return new InputMoneyPresenterImpl();
    }
}
