package ir.idpz.taxi.user.Map;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.example.idpz_lenovo.passengertexi.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cz.msebera.android.httpclient.Header;
import ir.idpz.taxi.user.Models.CrruentTaxis;
import ir.idpz.taxi.user.Models.PaymentTrackModel;
import ir.idpz.taxi.user.Models.Taxi;
import ir.idpz.taxi.user.Utils.AppController;
import ir.idpz.taxi.user.Utils.Helpers;

import static ir.idpz.taxi.user.Utils.Helpers.isNetworkAvailable;
import static ir.idpz.taxi.user.Utils.Helpers.noInternetDialog;
import static ir.idpz.taxi.user.Utils.Helpers.taxis;

public class LocationUpdate implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    Context mAct;
    public static GoogleApiClient mGoogleApiClient;
    LatLng latLng;

    public static Timer myTimer = new Timer();
    Location mLastLocation;

    String lastStation = "";

    int lineId;

    String Taxis;

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        if (ActivityCompat.checkSelfPermission(mAct, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mAct, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (isNetworkAvailable(AppController.getAppContext()))



            getNearestTaxis();
        else noInternetDialog();

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void StartSchedule(final Context act, int period) {
        this.lineId = lineId;
        mAct = act;

        //


        myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
//                            getLastLocation();

                            buildGoogleApiClient();
                            if (mGoogleApiClient != null) {
                                mGoogleApiClient.connect();
                            } else {

                                Toast.makeText(mAct, "عدم دریافت موقعیت مکانی...", Toast.LENGTH_SHORT).show();

                            }


                        } catch (Exception ex) {
                            Log.e("t", ex.toString());
                        }
                    }
                });
                thread.start();
            }

        }, 0, period);


    }

    protected synchronized void buildGoogleApiClient() {
        try {
            mGoogleApiClient = new GoogleApiClient.Builder(mAct)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        } catch (Exception ex) {
            Log.d("buildGoogleApiClient", ex.getMessage());
        }


    }


    public void CancelTimer() {

        myTimer.cancel();

        Toast.makeText(mAct, "متوقف شد", Toast.LENGTH_SHORT).show();
    }


    public void getNearestTaxis() {
        RequestParams params = new RequestParams();
        GPSTracker gps = new GPSTracker(mAct);

        double lat = gps.getLatitude();
        double lng = gps.getLongitude();
        params.put("lat", lat);
        params.put("lng", lng);
        params.put("car", "0");
        params.put("api_token", Helpers.getSharePrf("api_token"));

        AsyncHttpClient client = new AsyncHttpClient();
        // params.put("station_id", id);
        client.post("http://admin.idpz.ir/api/user-locale", params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                //    Toast.makeText(MapsActivity.this, responseString.toString(), Toast.LENGTH_SHORT).show();

                try {
                    Log.d("latlng", responseString);


                    EventBus.getDefault().post(new PaymentTrackModel(responseString));
                } catch (Exception e) {
                }
            }
        });
    }


}