package weather.test.application.utils;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Build;

import weather.test.application.app.App;

public class NetworkHelper {

    private static final String CONNECTION_RECEIVER_ACTION = "weather.test.CONNECTION_RECEIVER";
    public static final String  IS_NETWORK_AVAILABLE = "isNetworkAvailable";

    private boolean networkAvailable;
    private ConnectivityManager connectivityManager;
    private App mApp;
    private IntentFilter intentFilter;

    public NetworkHelper(App app) {
        mApp = app;
        connectivityManager = (ConnectivityManager) app.getSystemService(Context.CONNECTIVITY_SERVICE);

        intentFilter = new IntentFilter();
        intentFilter.addAction(CONNECTION_RECEIVER_ACTION);

        networkAvailable = isNetworkConnected();
        initConnectivityManager();
    }

    private void initConnectivityManager() {
        NetworkRequest.Builder networkRequestBuilder = new NetworkRequest.Builder();

        if (connectivityManager != null)
            connectivityManager.registerNetworkCallback(networkRequestBuilder.build(), new ConnectivityManager.NetworkCallback() {

                @Override
                public void onAvailable(Network network) {
                    super.onAvailable(network);
                    networkAvailable = true;
                    startBroadcast(true);
                }

                @Override
                public void onLost(Network network) {
                    super.onLost(network);
                    networkAvailable = false;
                    startBroadcast(false);
                }

            });
    }

    private boolean isNetworkConnected() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (connectivityManager != null) {
                NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
                if (capabilities != null) {
                    return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                            || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                            || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN);
                }
            }
        } else {
            if (connectivityManager != null) {
                NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
                if (activeNetwork != null) {
                    return activeNetwork.getType() == ConnectivityManager.TYPE_WIFI || activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE;
                }
            }
        }
        return false;
    }

    private void startBroadcast(boolean isConnected) {
        Intent intent = new Intent();
        intent.setAction(CONNECTION_RECEIVER_ACTION);
        intent.putExtra(IS_NETWORK_AVAILABLE, isConnected);
        mApp.sendBroadcast(intent);
    }

    public boolean isNetworkAvailable() {
        return networkAvailable;
    }

    public IntentFilter getIntentFilter() {
        return intentFilter;
    }
}
