package com.blumental.maxim.cleanexchangerate.repository;

import com.blumental.maxim.cleanexchangerate.model.LatestRates;

import rx.Observable;

public interface Repository {

    Observable<LatestRates> getLatestRates(String baseCurrency);
}
