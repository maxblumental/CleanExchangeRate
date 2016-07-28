package com.blumental.maxim.cleanexchangerate.repository;

import com.blumental.maxim.cleanexchangerate.model.LatestRates;
import com.blumental.maxim.cleanexchangerate.repository.fixer_service.FixerService;

import javax.inject.Inject;

import rx.Observable;

public class RepositoryImpl implements Repository {

    private FixerService service;

    @Inject
    public RepositoryImpl(FixerService service) {
        this.service = service;
    }

    @Override
    public Observable<LatestRates> getLatestRates(String baseCurrency) {
        return service.getLatestRates(baseCurrency);
    }
}
