package com.blumental.maxim.cleanexchangerate.interactor;

import rx.Observable;

public interface Interactor<T, R> {

    Observable<R> run(T argument);
}
