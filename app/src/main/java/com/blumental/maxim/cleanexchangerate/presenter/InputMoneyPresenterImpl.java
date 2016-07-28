package com.blumental.maxim.cleanexchangerate.presenter;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.blumental.maxim.cleanexchangerate.App;
import com.blumental.maxim.cleanexchangerate.interactor.ConvertToAllCurrenciesInteractor;
import com.blumental.maxim.cleanexchangerate.mapper.ConvertedMoneyToBundle;
import com.blumental.maxim.cleanexchangerate.model.Money;
import com.blumental.maxim.cleanexchangerate.view.InputMoneyView;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

public class InputMoneyPresenterImpl implements InputMoneyPresenter {

    private InputMoneyView view;

    private CompositeSubscription compositeSubscription;

    public InputMoneyPresenterImpl() {
        compositeSubscription = new CompositeSubscription();
    }

    @Override
    public void onCreate(InputMoneyView view) {
        App.getComponent().inject(this);
        this.view = view;
    }

    @Override
    public void onDestroy() {
        compositeSubscription.unsubscribe();
        view = null;
    }

    @Override
    public void observeConvertButtonClicks(Observable<Void> observable) {

        Subscription subscription = observable
                .debounce(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        handleConvertButtonClick();
                    }
                });

        compositeSubscription.add(subscription);
    }

    private void handleConvertButtonClick() {

        view.hideKeyboard();
        view.showProgress();
        view.disableConvertButton();

        String amount = view.getAmount();
        String currency = view.getCurrency();

        if (isInputWrong(amount, currency)) {
            onWrongInput();
            return;
        }

        Money money = new Money(Integer.parseInt(amount), currency);

        ConvertToAllCurrenciesInteractor interactor = ConvertToAllCurrenciesInteractor.create();

        Subscriber<Bundle> subscriber = createConvertToAllCurrenciesSubscriber();
        Subscription subscription = interactor.run(money)
                .map(ConvertedMoneyToBundle.map())
                .subscribe(subscriber);

        compositeSubscription.add(subscription);
    }

    private void onWrongInput() {
        view.showError("Enter amount and select a currency!");
        view.hideProgress();
        view.enableConvertButton();
    }

    @NonNull
    private Subscriber<Bundle> createConvertToAllCurrenciesSubscriber() {
        return new Subscriber<Bundle>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                view.hideProgress();
                view.enableConvertButton();
                view.showError(e.getMessage());
            }

            @Override
            public void onNext(Bundle bundle) {
                view.hideProgress();
                view.enableConvertButton();
                view.switchToConvertedMoney(bundle);
            }
        };
    }

    private boolean isInputWrong(String amount, String currency) {
        return amount.isEmpty() || currency.isEmpty();
    }
}
