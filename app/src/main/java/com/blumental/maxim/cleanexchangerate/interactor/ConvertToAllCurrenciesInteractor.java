package com.blumental.maxim.cleanexchangerate.interactor;

import com.blumental.maxim.cleanexchangerate.App;
import com.blumental.maxim.cleanexchangerate.di.ApplicationComponent;
import com.blumental.maxim.cleanexchangerate.executor.Executor;
import com.blumental.maxim.cleanexchangerate.mapper.LatesRatesToConvertedMoneyMapper;
import com.blumental.maxim.cleanexchangerate.model.ConvertedMoney;
import com.blumental.maxim.cleanexchangerate.model.Money;
import com.blumental.maxim.cleanexchangerate.repository.Repository;

import javax.inject.Inject;

import rx.Observable;

public class ConvertToAllCurrenciesInteractor implements Interactor<Money, ConvertedMoney> {

    private Executor executor;

    private Repository repository;

    @Inject
    ConvertToAllCurrenciesInteractor(Executor executor, Repository repository) {

        this.executor = executor;

        this.repository = repository;
    }

    public static ConvertToAllCurrenciesInteractor create() {

        ApplicationComponent component = App.getComponent();

        Executor executor = component.executor();
        Repository repository = component.repository();

        return new ConvertToAllCurrenciesInteractor(executor, repository);
    }

    @Override
    public Observable<ConvertedMoney> run(final Money argument) {

        Observable<ConvertedMoney> observable =
                repository
                        .getLatestRates(argument.getCurrency())
                        .map(LatesRatesToConvertedMoneyMapper.map(argument.getAmount()));

        return executor.makeAsynchronous(observable);
    }
}
