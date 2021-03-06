package com.blumental.maxim.cleanexchangerate.executor;

import rx.Observable;

public interface Executor {
    <T> Observable<T> makeAsynchronous(Observable<T> observable);
}
