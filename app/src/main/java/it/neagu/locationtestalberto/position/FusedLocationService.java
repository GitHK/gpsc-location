package it.neagu.locationtestalberto.position;

import android.app.IntentService;
import android.content.Intent;
import android.location.Location;
import android.util.Log;

import com.google.android.gms.location.LocationClient;


public class FusedLocationService extends IntentService {

    public FusedLocationService() {
        super("Fused Location");
    }

    public FusedLocationService(String name) {
        super("Fused Location");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("debug", "I have a position");
        Location location = intent.getParcelableExtra(LocationClient.KEY_LOCATION_CHANGED);
        if (location != null) {
            if (location.getAccuracy() <= FusedPositionManager.ACCURACY && location.getLatitude() != 0 && location.getLatitude() != 0) {
                // snd position to your application however you wish
                Log.d("debug", "I have a valid position: "+ location);
            }
        }
    }
}
