package com.example.anotherweatherapp.common;

import android.os.Bundle;

import com.example.anotherweatherapp.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public abstract class SingleFragmentActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener,RefreshOwner {

    protected abstract Fragment getFragment();

    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_fragment_activity);

        mSwipeRefreshLayout = findViewById(R.id.refresher);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        if(savedInstanceState == null){
            changeFragment(getFragment());
        }

    }

    private void changeFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container,fragment);

        fragmentTransaction.commit();
    }

    @Override
    public void onRefresh() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);

        if (fragment instanceof Refreshable){
            ((Refreshable) fragment).onRefreshData();
        }else setRefreshState(false);
    }

    @Override
    public void setRefreshState(boolean refreshState) {
        mSwipeRefreshLayout.post(() -> mSwipeRefreshLayout.setRefreshing(refreshState));
    }
}
