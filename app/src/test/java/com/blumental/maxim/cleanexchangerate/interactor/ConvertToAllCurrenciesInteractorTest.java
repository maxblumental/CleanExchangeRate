package com.blumental.maxim.cleanexchangerate.interactor;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.blumental.maxim.cleanexchangerate.executor.Executor;
import com.blumental.maxim.cleanexchangerate.model.ConvertedMoney;
import com.blumental.maxim.cleanexchangerate.model.LatestRates;
import com.blumental.maxim.cleanexchangerate.model.Money;
import com.blumental.maxim.cleanexchangerate.model.Rates;
import com.blumental.maxim.cleanexchangerate.repository.Repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.observers.TestSubscriber;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ConvertToAllCurrenciesInteractorTest {

    private final double RUB_TO_USD = 60.0;

    private final int AMOUNT_USD = 10;

    @Mock
    Executor executor;

    @Mock
    Repository repository;

    @InjectMocks
    ConvertToAllCurrenciesInteractor interactor;

    @Test
    public void testInteractor() throws Exception {

        when(executor.makeAsynchronous(any(Observable.class)))
                .thenAnswer(new Answer<Observable>() {
                    @Override
                    public Observable answer(InvocationOnMock invocation) throws Throwable {
                        return (Observable) invocation.getArguments()[0];
                    }
                });

        when(repository.getLatestRates("USD"))
                .thenReturn(Observable.create(new Observable.OnSubscribe<LatestRates>() {
                    @Override
                    public void call(Subscriber<? super LatestRates> subscriber) {

                        LatestRates latestRates = createRepositoryResponse();

                        subscriber.onNext(latestRates);
                        subscriber.onCompleted();
                    }
                }));

        TestSubscriber<ConvertedMoney> testSubscriber = new TestSubscriber<>();

        Money argument = new Money(AMOUNT_USD, "USD");

        interactor.run(argument)
                .subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
        testSubscriber.assertCompleted();

        List<ConvertedMoney> results = testSubscriber.getOnNextEvents();

        assertEquals(results.size(), 1);

        Money rub = getRubles(results);

        assertNotNull(rub);
        assertEquals(rub.getAmount(), AMOUNT_USD * RUB_TO_USD, 0.001);
    }

    @NonNull
    private LatestRates createRepositoryResponse() {
        Rates rates = new Rates();
        rates.setRUB(RUB_TO_USD);
        return new LatestRates("USD", "01-01-2016", rates);
    }

    @Nullable
    private Money getRubles(List<ConvertedMoney> results) {
        ConvertedMoney convertedMoney = results.get(0);

        List<Money> inOtherCurrencies = convertedMoney.getAmountsInOtherCurrencies();

        Money rub = null;

        for (Money money : inOtherCurrencies) {
            if (money.getCurrency().equals("RUB")) {
                rub = money;
            }
        }
        return rub;
    }

}