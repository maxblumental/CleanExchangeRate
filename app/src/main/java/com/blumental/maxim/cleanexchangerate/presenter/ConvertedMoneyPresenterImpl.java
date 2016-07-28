package com.blumental.maxim.cleanexchangerate.presenter;

import android.os.Bundle;

import com.blumental.maxim.cleanexchangerate.model.Money;
import com.blumental.maxim.cleanexchangerate.view.ConvertedMoneyView;

import java.util.ArrayList;

public class ConvertedMoneyPresenterImpl implements ConvertedMoneyPresenter {

    private ConvertedMoneyView view;

    @Override
    public void onCreate(ConvertedMoneyView view) {
        this.view = view;
    }

    @Override
    public void onDestroy() {
        view = null;
    }

    @Override
    public void onCreateView(Bundle args) {

        double amount = args.getDouble(BASE_AMOUNT_KEY);
        view.setBaseAmount(Double.toString(amount));

        String currency = args.getString(BASE_CURRENCY_KEY);
        view.setBaseCurrency(currency);

        ArrayList<Money> moneyList = args.getParcelableArrayList(MONEY_LIST_KEY);
        view.showConvertedMoney(moneyList);
    }
}
