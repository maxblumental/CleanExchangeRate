package com.blumental.maxim.cleanexchangerate.view;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.blumental.maxim.cleanexchangerate.App;
import com.blumental.maxim.cleanexchangerate.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        App.getComponent().inject(this);

        switchToInputMoneyScreen();
    }

    private void switchToInputMoneyScreen() {
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.mainFrame, new InputMoneyFragment(), InputMoneyFragment.TAG)
                .commit();
    }
}
