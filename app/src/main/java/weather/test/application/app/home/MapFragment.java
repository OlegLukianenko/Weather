package weather.test.application.app.home;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import javax.inject.Inject;

import weather.test.application.R;
import weather.test.application.base.BaseFragment;
import weather.test.application.databinding.FragmentMapBinding;
import weather.test.application.di.viewmodel.Injectable;
import weather.test.application.viewmodel.MapFragmentViewModel;

public class MapFragment extends BaseFragment<FragmentMapBinding> implements
        Injectable, OnMapReadyCallback {

    @Inject
    MapFragmentViewModel viewModel;

    @Inject
    protected SharedPreferences sharedPreferences;

    private GoogleMap mMap;
    private float latitude;
    private float longitude;
    private MarkerOptions markerOptions;
    private FusedLocationProviderClient fusedLocationClient;


    @Override
    protected int getLayout() {
        return R.layout.fragment_map;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
        binding.setHandler(this);
        viewModel.getPlaceIsAvailable().postValue(false);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.mapFragment);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
    }

    public void changePlaceClick() {
        sharedPreferences.edit().putFloat("latitude", latitude).apply();
        sharedPreferences.edit().putFloat("longitude", longitude).apply();
        getActivity().onBackPressed();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(50, 31)));
        mMap.animateCamera(CameraUpdateFactory.zoomTo((float) 4.6));
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(false);

        mMap.setOnMyLocationButtonClickListener(() -> {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            }
            fusedLocationClient.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if ((location != null)) {
                        latitude = (float) location.getLatitude();
                        longitude = (float) location.getLongitude();
                        viewModel.getPlaceIsAvailable().postValue(true);
                    }
                }
            });
            return false;
        });

        //обработчик клика на карте
        mMap.setOnMapClickListener(arg0 -> {
            mMap.clear();
            mMap.animateCamera(CameraUpdateFactory.newLatLng(arg0));
            markerOptions = new MarkerOptions();
            markerOptions.position(arg0);
            mMap.addMarker(markerOptions);
            new ReverseGeocodingTask(getContext()).execute(arg0);

        });

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);

    }

    private class ReverseGeocodingTask extends AsyncTask<LatLng, Void, String> {
        Context mContext;

        public ReverseGeocodingTask(Context context) {
            super();
            mContext = context;
        }

        @Override
        protected String doInBackground(LatLng... params) {
            String addressTex = "";
            latitude = (float) params[0].latitude;
            longitude = (float) params[0].longitude;
            viewModel.getPlaceIsAvailable().postValue(true);
            return addressTex;
        }

        @Override
        protected void onPostExecute(String addressText) {
            markerOptions.title(addressText);
        }
    }
}
