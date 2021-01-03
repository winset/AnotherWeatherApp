package com.example.anotherweatherapp.ui.main;

import androidx.fragment.app.Fragment;

import com.example.anotherweatherapp.common.SingleFragmentActivity;

public class MainActivity extends SingleFragmentActivity {

    @Override
    protected Fragment getFragment() {
        return MainFragment.newInstance();
    }

}
