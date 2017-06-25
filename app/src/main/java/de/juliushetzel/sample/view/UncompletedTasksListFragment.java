package de.juliushetzel.sample.view;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.juliushetzel.sample.R;
import de.juliushetzel.sample.model.TaskRepositoryImpl;
import de.juliushetzel.sample.viewmodel.UnCompletedTasksViewModel;
import jhetzel.vagar.Vagar;
import jhetzel.vagar.ViewModel;
import jhetzel.vagar.annotation.Assemble;

@Assemble(
        viewModel = UnCompletedTasksViewModel.class,
        layout = R.layout.fragment_task_list
)
public class UncompletedTasksListFragment extends Fragment implements ViewModel.Factory<ViewModel> {

    public static UncompletedTasksListFragment newInstance() {
        UncompletedTasksListFragment fragment = new UncompletedTasksListFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return Vagar.bind(this, this, container).getRoot();
    }

    @Override
    public ViewModel createViewModel() {
        return new UnCompletedTasksViewModel(TaskRepositoryImpl.getInstance());
    }
}