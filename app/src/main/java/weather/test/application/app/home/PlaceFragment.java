package weather.test.application.app.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import javax.inject.Inject;

import weather.test.application.R;
import weather.test.application.base.BaseFragment;
import weather.test.application.databinding.FragmentPlaceBinding;
import weather.test.application.di.viewmodel.Injectable;
import weather.test.application.viewmodel.PlaceFragmentViewModel;

public class PlaceFragment extends BaseFragment<FragmentPlaceBinding> implements Injectable {

    @Inject
    PlaceFragmentViewModel viewModel;

    @Override
    protected int getLayout() {
        return R.layout.fragment_place;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.sendCityRequest();
        initSubscribers();
    }

    private void initSubscribers() {
        viewModel.getListCityFromApi().observe(this, locationCity -> {
            Log.d("", "initSubscribers: ");
        });
    }
}
