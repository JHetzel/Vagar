package de.juliushetzel.sample.second;


import android.os.Bundle;
import android.support.v4.util.Pair;

import de.juliushetzel.vagar.ViewModel;
import de.juliushetzel.vagar.databinding.ObservablePair;

public class OtherViewModel implements ViewModel{

    public final ObservablePair<Class<?>, Bundle> navigationObservable = new ObservablePair<>();

    public OtherViewModel(){}

    public void finish(){
        navigationObservable.set(new Pair<Class<?>, Bundle>(null, null));
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }
}