package com.blumental.maxim.cleanexchangerate.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.blumental.maxim.cleanexchangerate.App;
import com.blumental.maxim.cleanexchangerate.R;
import com.blumental.maxim.cleanexchangerate.presenter.InputMoneyPresenter;
import com.blumental.maxim.cleanexchangerate.view.adapter.CurrencyAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;
import static com.jakewharton.rxbinding.view.RxView.clicks;

public class InputMoneyFragment extends Fragment implements InputMoneyView {

    public static final String TAG = InputMoneyFragment.class.getSimpleName();

    @Inject
    InputMoneyPresenter presenter;

    @BindView(R.id.amountEditText)
    EditText amount;

    @BindView(R.id.convertButton)
    Button convertButton;

    @BindView(R.id.currencySpinner)
    Spinner currencySpinner;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getComponent().inject(this);
        presenter.onCreate(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.input_money_fragment, container, false);
        ButterKnife.bind(this, view);

        presenter.observeConvertButtonClicks(clicks(convertButton));

        CurrencyAdapter adapter = new CurrencyAdapter(getContext());
        currencySpinner.setAdapter(adapter);
        return view;
    }

    @Override
    public void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }


    @Override
    public void switchToConvertedMoney(Bundle args) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        ConvertedMoneyFragment fragment = new ConvertedMoneyFragment();
        fragment.setArguments(args);

        fragmentManager.beginTransaction()
                .replace(R.id.mainFrame, fragment, ConvertedMoneyFragment.TAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public String getAmount() {
        return amount.getText().toString();
    }

    @Override
    public String getCurrency() {
        return currencySpinner.getSelectedItem().toString();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(GONE);
    }

    @Override
    public void showError(String errorMessage) {
        makeText(getContext(), errorMessage, LENGTH_LONG).show();
    }

    @Override
    public void disableConvertButton() {
        convertButton.setEnabled(false);
    }

    @Override
    public void enableConvertButton() {
        convertButton.setEnabled(true);
    }

    @Override
    public void hideKeyboard() {

        View view = getActivity().getCurrentFocus();

        if (view != null) {
            InputMethodManager imm =
                    (InputMethodManager) getContext().getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
