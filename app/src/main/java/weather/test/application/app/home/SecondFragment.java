package weather.test.application.app.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import javax.inject.Inject;

import weather.test.application.R;
import weather.test.application.base.BaseFragment;
import weather.test.application.databinding.FragmentSecondBinding;
import weather.test.application.di.viewmodel.Injectable;
import weather.test.application.viewmodel.SecondFragmentViewModel;

public class SecondFragment extends BaseFragment<FragmentSecondBinding> implements Injectable {

    @Inject
    SecondFragmentViewModel secondFragmentViewModel;

    @Override
    protected int getLayout() {
        return R.layout.fragment_second;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonTwo.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SecondActivity.class);
            startActivity(intent);
        });
    }
}
