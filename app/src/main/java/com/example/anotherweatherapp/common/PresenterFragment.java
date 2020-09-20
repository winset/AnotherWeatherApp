package com.example.anotherweatherapp.common;



import moxy.MvpAppCompatFragment;

public abstract class PresenterFragment<P extends BasePresenter> extends MvpAppCompatFragment {

    protected abstract P getPresenter();

    @Override
    public void onDestroy() {
        super.onDestroy();
        getPresenter().disposeAll();
    }
}
