package weather.test.application.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;

import weather.test.application.R;

public class CheckInternetDialog {

    private static MyListenerInet listener = null;

    public static void onCreateAlertDialog(final Context context) {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setTitle(R.string.oops)
                .setMessage(R.string.internet_connection)
                .setNegativeButton(R.string.cancel, (dialog, which) -> {
                    if (listener != null)
                        listener.callSetNegativeButton();
                })
                .setNeutralButton(R.string.settings, (dialog, which) ->
                        context.startActivity(new Intent
                                (Settings.ACTION_WIFI_SETTINGS)))
                .setPositiveButton(R.string.ok, (dialog, which) -> {
                    if (!CheckInternetDialog.isOnline(context)) {
                        CheckInternetDialog.onCreateAlertDialog(context);
                    } else {
                        if (listener != null)
                            listener.callSetPositiveButton();
                    }
                }).show();
    }


    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    public interface MyListenerInet {
        void callSetPositiveButton();

        void callSetNegativeButton();
    }

    public static void setMyListenerInet(MyListenerInet lis) {
        listener = lis;
    }

}
