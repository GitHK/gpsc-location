package it.neagu.gpsclocationserviceexample.position;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Parcelable;
import android.util.Log;

import it.neagu.gpsclocationserviceexample.H;


public class ValidPositionReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Parcelable.Creator<Location> creator = Location.CREATOR;
        Location loc = creator.createFromParcel(H.unmarshall(intent.getExtras().getByteArray("location")));
        Log.d("debug", "I [receiver] have a position"+loc);
    }


}
