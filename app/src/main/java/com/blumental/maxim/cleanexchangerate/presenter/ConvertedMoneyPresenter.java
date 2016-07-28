package com.blumental.maxim.cleanexchangerate.presenter;

import android.os.Bundle;

import com.blumental.maxim.cleanexchangerate.view.ConvertedMoneyView;

public interface ConvertedMoneyPresenter {

    String MONEY_LIST_KEY = "money list key";

    String BASE_AMOUNT_KEY = "base amount key";

    String BASE_CURRENCY_KEY = "base currency key";

    void onCreate(ConvertedMoneyView view);

    void onDestroy();

    void onCreateView(Bundle args);
}
