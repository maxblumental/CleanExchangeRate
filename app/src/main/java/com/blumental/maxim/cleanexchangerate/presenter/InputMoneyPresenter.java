package com.blumental.maxim.cleanexchangerate.presenter;

import com.blumental.maxim.cleanexchangerate.view.InputMoneyView;

import rx.Observable;

public interface InputMoneyPresenter {

    void onCreate(InputMoneyView view);

    void onDestroy();

    void observeConvertButtonClicks(Observable<Void> observable);
}
