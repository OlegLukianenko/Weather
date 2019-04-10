package weather.test.application.app.home;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import weather.test.application.R;
import weather.test.application.base.BaseFragment;
import weather.test.application.data.retrofit.response.City;
import weather.test.application.databinding.FragmentPlaceBinding;
import weather.test.application.di.viewmodel.Injectable;
import weather.test.application.utils.KeyboardStateManager;
import weather.test.application.viewmodel.PlaceFragmentViewModel;

public class PlaceFragment extends BaseFragment<FragmentPlaceBinding> implements Injectable {

    @Inject
    PlaceFragmentViewModel viewModel;

    @Inject
    protected SharedPreferences sharedPreferences;

    @Override
    protected int getLayout() {
        return R.layout.fragment_place;
    }

    private ArrayAdapter<City> mAdapter = null;
    private  List<City> cityList = new ArrayList<>();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
        viewModel.getProgressBarEvent().postValue(true);
        viewModel.sendCityRequest();
        initSubscribers();
    }

    private void initSubscribers() {
        viewModel.getListCityFromApi().observe(this, locationCity -> {
            viewModel.getProgressBarEvent().postValue(false);
            cityList.addAll(locationCity.results);
            mAdapter = new ArrayAdapter<>(getActivity(),
                    android.R.layout.simple_dropdown_item_1line, locationCity.results);
            binding.autoCompleteTextView.setAdapter(mAdapter);
        });

        binding.btnSearch.setOnClickListener(view -> {
            for(int i=0;i<cityList.size();i++)
            {
                if(cityList.get(i).city.toLowerCase().equals(binding.autoCompleteTextView.getText().toString().toLowerCase()))
                {
                    sharedPreferences.edit().putFloat("latitude", cityList.get(i).lat).apply();
                    sharedPreferences.edit().putFloat("longitude", cityList.get(i).lon).apply();
                    if (getActivity() != null) {
                        KeyboardStateManager.hideSoftKeyboard(getActivity());
                        getActivity().onBackPressed();
                    }
                }
            }
        });

        binding.btnBack.setOnClickListener(view -> {
            KeyboardStateManager.hideSoftKeyboard(getActivity());
            getActivity().onBackPressed();
        });

    }
}
