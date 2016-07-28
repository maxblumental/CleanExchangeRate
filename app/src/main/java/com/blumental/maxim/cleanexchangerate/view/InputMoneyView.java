package com.blumental.maxim.cleanexchangerate.view;

import android.os.Bundle;

public interface InputMoneyView {

    void switchToConvertedMoney(Bundle args);

    String getAmount();

    String getCurrency();

    void showProgress();

    void hideProgress();

    void showError(String errorMessage);

    void disableConvertButton();

    void enableConvertButton();

    void hideKeyboard();
}
