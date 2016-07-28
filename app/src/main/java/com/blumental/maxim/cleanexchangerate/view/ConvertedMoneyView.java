package com.blumental.maxim.cleanexchangerate.view;

import com.blumental.maxim.cleanexchangerate.model.Money;

import java.util.List;

public interface ConvertedMoneyView {

    void setBaseAmount(String amount);

    void setBaseCurrency(String currency);

    void showConvertedMoney(List<Money> moneyList);
}
