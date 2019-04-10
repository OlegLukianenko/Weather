package weather.test.application.app.home;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

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
    private Geocoder geocoder;
    private MarkerOptions markerOptions;

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
            if(mMap.getMyLocation()!=null||mMap.getMyLocation()!=null) {
                latitude = (float) mMap.getMyLocation().getLatitude();
                longitude = (float) mMap.getMyLocation().getLongitude();
                viewModel.getPlaceIsAvailable().postValue(true);
            }
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
            Address address;
            String addressTex = "";

            try {
                geocoder = new Geocoder(mContext);
                latitude = (float) params[0].latitude;
                longitude = (float) params[0].longitude;

                viewModel.getPlaceIsAvailable().postValue(true);

                List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);

                if (addresses != null && addresses.size() > 0) {
                    address = addresses.get(0);

                    if (address.getLocality() != null)
                        addressTex = address.getLocality() + ",  " + address.getCountryName();
                    else
                        addressTex = address.getCountryName();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return addressTex;

        }

        @Override
        protected void onPostExecute(String addressText) {
            markerOptions.title(addressText);
        }
    }
}
