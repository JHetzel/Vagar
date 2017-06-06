package de.juliushetzel.vagar;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import java.util.concurrent.atomic.AtomicLong;

final class ViewModelProvider {

    /**
     * Returns the Config Persistent View Model or creates one if not existing
     * and saves it under the given @param tag
     * @param fragmentManager needed for the view model to get saved in a Retained Fragment
     * @param factory needed to get a new instance of the view model
     * @param tag the tag under which the view model holding fragment will be saved in the fragment manager
     * @param <T> extends @link{ViewModel} the view model
     * @return the retained view model
     */
    public static <T extends ViewModel> T get(@NonNull FragmentManager fragmentManager,
                                       @NonNull ViewModel.Factory<T> factory,
                                       @NonNull String tag) {

        @SuppressWarnings("unchecked")
        ViewModelHolder<T> viewModelHolder = getViewModelHolder(fragmentManager, factory, tag);
        return viewModelHolder.getViewModel();
    }


    public static <T extends ViewModel> ViewModelHolder<T> getViewModelHolder(@NonNull FragmentManager fragmentManager,
                                                              @NonNull ViewModel.Factory<T> factory,
                                                              @NonNull String tag){
        @SuppressWarnings("unchecked")
        ViewModelHolder<T> viewModelHolder = (ViewModelHolder<T>) fragmentManager
                .findFragmentByTag(tag);

        if (viewModelHolder == null) {
            viewModelHolder = ViewModelHolder.newInstance(factory.createViewModel());
            fragmentManager
                    .beginTransaction()
                    .add(viewModelHolder, tag)
                    .commit();
        }
        return viewModelHolder;
    }
}