package com.example.anotherweatherapp.common;




import io.reactivex.rxjava3.disposables.CompositeDisposable;
import moxy.MvpPresenter;

public class BasePresenter<V extends BaseView> extends MvpPresenter<V> {

   protected final CompositeDisposable compositeDisposable = new CompositeDisposable();


   public void disposeAll(){
       compositeDisposable.clear();
   }
}
