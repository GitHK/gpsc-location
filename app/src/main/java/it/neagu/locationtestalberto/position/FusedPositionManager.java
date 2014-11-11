package it.neagu.locationtestalberto.position;


import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationRequest;


public class FusedPositionManager {

    public static int ACCURACY = 100;
    public static int REQUEST_INTERVAL = 10000; //ms
    public static int REQUEST_INTERVAL_REGULAR = 15000; //ms

    private LocationClient locationclient;
    private LocationRequest locationRequest;
    private Intent intentService;
    private PendingIntent pendingIntent;


    public FusedPositionManager(Context context, final GPSCConnectionHandler gPSCConnectionHandler) {

        intentService = new Intent(context, FusedLocationService.class);
        pendingIntent = PendingIntent.getService(context, 1, intentService, 0);

        int resp = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
        if (resp == ConnectionResult.SUCCESS) {
            locationclient = new LocationClient(context, new GooglePlayServicesClient.ConnectionCallbacks() {
                @Override
                public void onConnected(Bundle bundle) {
                    gPSCConnectionHandler.onConnected(bundle);
                }

                @Override
                public void onDisconnected() {
                    gPSCConnectionHandler.onDisconnected();
                }
            }, new GooglePlayServicesClient.OnConnectionFailedListener() {
                @Override
                public void onConnectionFailed(ConnectionResult connectionResult) {
                    gPSCConnectionHandler.onConnectionFailed(connectionResult);
                }
            }
            );
            locationclient.connect();
        } else {
            Log.e("error","googleplayservices is not available");
        }
    }

    public interface GPSCConnectionHandler {
        public void onConnected(Bundle bundle);
        public void onDisconnected();
        public void onConnectionFailed(ConnectionResult connectionResult);
    }


    public void stopPositionUpdates() {
        locationclient.removeLocationUpdates(pendingIntent);
        locationRequest = null;
    }

    public Location getLastPosition(){
        return locationclient.getLastLocation();
    }


    public void startPositionUpdates() {
        Log.d("debug","starting position updates");
        if (locationclient != null) {
            locationRequest = LocationRequest.create()
                    .setInterval(REQUEST_INTERVAL)
                    .setFastestInterval(REQUEST_INTERVAL_REGULAR)
                    .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
            locationclient.requestLocationUpdates(locationRequest, pendingIntent);
            Location loc = locationclient.getLastLocation();   // this fixes the Android 4.0.4 bug where it does not receive a position
            Log.d("debug","last position: "+ loc);
        }
    }

}
