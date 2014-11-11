package it.neagu.locationtestalberto;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.common.ConnectionResult;

import it.neagu.locationtestalberto.position.FusedPositionManager;


public class MyActivity extends Activity {

    public FusedPositionManager fusedPositionManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        fusedPositionManager = new FusedPositionManager(this, new FusedPositionManager.GPSCConnectionHandler() {
            @Override
            public void onConnected(Bundle bundle) {
                Log.d("debug","GPSC connected");
                fusedPositionManager.getLastPosition(); // recover the first position as soon as possible for fast access
                fusedPositionManager.startPositionUpdates();
            }

            @Override
            public void onDisconnected() {
                Log.d("debug","GPSC disconnected");
            }

            @Override
            public void onConnectionFailed(ConnectionResult connectionResult) {
                Log.d("debug","GPSC connection fail: " + connectionResult);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
