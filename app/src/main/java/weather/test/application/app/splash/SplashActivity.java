package weather.test.application.app.splash;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import weather.test.application.R;
import weather.test.application.app.home.MainActivity;
import weather.test.application.base.BaseActivity;
import weather.test.application.databinding.ActivitySplashBinding;
import weather.test.application.utils.CheckInternetDialog;
import weather.test.application.utils.NetworkHelper;
import weather.test.application.viewmodel.SplashActivityViewModel;

import static weather.test.application.utils.Utils.collapse;
import static weather.test.application.utils.Utils.expand;


public class SplashActivity extends BaseActivity<ActivitySplashBinding> implements
        HasSupportFragmentInjector, CheckInternetDialog.MyListenerInet {

    @Inject
    SplashActivityViewModel viewModel;

    @Inject
    protected NetworkHelper networkHelper;

    @Inject
    protected SharedPreferences sharedPreferences;

    @Override
    protected int getLayout() {
        return R.layout.activity_splash;
    }

    private LocationManager locationManager;

    private static final int PICK_GET_PERMISSION = 9999;
    private boolean dialogIsOpen = true;
    private BroadcastReceiver internetConnectionReceiver;

    @Override
    protected void onCreate() {
        viewModel.getInternetIsAvailable().postValue(networkHelper.isNetworkAvailable());
        initProfileBroadcastReceiver();
        CheckInternetDialog.setMyListenerInet(this);
        viewModel.getInternetIsAvailable().observe(this, Void -> showSnackBar());
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (dialogIsOpen)
            checkPermission();
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(locationListener);
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return null;
    }

    private void startMainActivity(double latitude, double longitude) {
        sharedPreferences.edit().putFloat("latitude", (float) latitude).apply();
        sharedPreferences.edit().putFloat("longitude", (float) longitude).apply();

        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
        finish();
    }

    public boolean checkInternet() {
        if (!CheckInternetDialog.isOnline(this)) {
            //hideProgressBar();
            CheckInternetDialog.onCreateAlertDialog(this);
        } else {
            checkGpsNetworkEnabled();
            return true;
        }
        return false;
    }


    private void initProfileBroadcastReceiver() {
        internetConnectionReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                viewModel.getInternetIsAvailable().postValue(networkHelper.isNetworkAvailable());
                showSnackBar();
            }
        };

        registerReceiver(internetConnectionReceiver, networkHelper.getIntentFilter());
    }


    private void showSnackBar() {
        if (networkHelper.isNetworkAvailable()) {
            new Handler().postDelayed(() -> collapse(binding.includeLayoutSnackBar.containerWrap), 1500);
        } else {
            expand(binding.includeLayoutSnackBar.containerWrap, this);
        }
    }

    public void checkPermission() {
        List<String> containerPermission = new ArrayList<>();
        List<String> permissions = new ArrayList<>();
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);

        for (int i = 0; i < permissions.size(); i++) {
            if (ContextCompat.checkSelfPermission(this, permissions.get(i)) == PackageManager.PERMISSION_DENIED)
                containerPermission.add(permissions.get(i));
        }

        if (!containerPermission.isEmpty()) {
            ActivityCompat.requestPermissions(this, containerPermission.toArray(new String[containerPermission.size()]), PICK_GET_PERMISSION);
        } else {
            checkInternet();
        }
    }


    private void checkGpsNetworkEnabled() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean gpsEnabled = false;
        boolean networkEnabled = false;

        try {
            gpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
            networkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }

        if (!gpsEnabled || !networkEnabled) {
            new AlertDialog.Builder(this)
                    .setCancelable(false)
                    .setMessage(R.string.gps_network_not_enabled)
                    .setNegativeButton(R.string.cancel, (dialogInterface, i) -> finish())
                    .setPositiveButton(R.string.open_location_settings, (dialogInterface, i) ->
                            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)))
                    .show();
        } else {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                        5000, 0, locationListener);
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                        5000, 0, locationListener);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PICK_GET_PERMISSION: {
                if (permissions.length == 0) {
                    return;
                }
                boolean allPermissionsGranted = true;
                if (grantResults.length > 0) {
                    for (int grantResult : grantResults) {
                        if (grantResult != PackageManager.PERMISSION_GRANTED) {
                            allPermissionsGranted = false;
                            break;
                        }
                    }
                }
                if (!allPermissionsGranted) {
                    boolean somePermissionsForeverDenied = false;
                    for (String permission : permissions) {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                            //denied
                            if (permission.equals(permissions)) {
                                ActivityCompat.requestPermissions(this, new String[]{permission}, PICK_GET_PERMISSION);
                            }
                        } else {
                            if (ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
                                //allowed
                            } else {
                                //set to never ask again
                                somePermissionsForeverDenied = true;
                                dialogIsOpen = false;
                            }
                        }
                    }
                    if (somePermissionsForeverDenied) {
                        onCreateAlertDialog(SplashActivity.this, permissions);
                    }
                } else {

                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        checkInternet();
                    }
                }
                break;
            }
        }
    }

    public void onCreateAlertDialog(final Activity activity, final String[] permissions) {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle(R.string.requires_permission)
                .setMessage(R.string.enable_permission);
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                activity.finish();
            }
        });
        builder.setPositiveButton(R.string.settings, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                for (String permission : permissions) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                        if (permission.equals(permission)) {
                            ActivityCompat.requestPermissions(activity, new String[]{permission}, PICK_GET_PERMISSION);
                        }
                    } else {
                        openApplicationSettings();
                        return;
                    }
                }
            }
        }).show();
    }

    public void openApplicationSettings() {
        dialogIsOpen = true;
        Intent appSettingsIntent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.parse("package:" + this.getPackageName()));
        startActivityForResult(appSettingsIntent, PICK_GET_PERMISSION);
    }


    private LocationListener locationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            showLocation(location);
        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        @Override
        public void onProviderEnabled(String provider) {
            if (ActivityCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                showLocation(locationManager.getLastKnownLocation(provider));
            }

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };

    private void showLocation(Location location) {
        if (location == null)
            return;
        if (location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
            startMainActivity(location.getLatitude(), location.getLongitude());
        } else if (location.getProvider().equals(LocationManager.NETWORK_PROVIDER)) {
            startMainActivity(location.getLatitude(), location.getLongitude());
        }
    }

    @Override
    public void callSetPositiveButton() {
        checkGpsNetworkEnabled();
    }

    @Override
    public void callSetNegativeButton() {
        finish();
    }
}
