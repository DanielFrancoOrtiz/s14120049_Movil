package com.example.android_maps;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        1);
            }
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {
            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        2);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                }
                return;
            }
            case 2: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {

                }
                return;
            }
        }
    }
    Criteria criteria;
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        mMap.setMyLocationEnabled(true);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if(criteria==null) {
            criteria = new Criteria();
        }
        Location loc =
                locationManager.getLastKnownLocation(locationManager.getAllProviders().get(0));

        LatLng dest = new LatLng(20.031736, -101.152851);
        mMap.addMarker(new MarkerOptions().position(dest).title("Marker in my HOUSE"));

        LatLng origen = new LatLng(20.1394083 ,-101.1507207);
        /*
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(origen, 15));
        mMap.addMarker(new MarkerOptions().position(origen).title("Marker in the ITSUR"));
*/
        String str_origin = "origin="+origen.latitude+","+origen.longitude;
        String str_dest = "destination="+dest.latitude+","+dest.longitude;
        String parameters = str_origin+"&"+str_dest+"&sensor=false";

        String url = "https://maps.googleapis.com/maps/api/directions/json?"+parameters;
       DownloadTask downloadTask = new DownloadTask();
        //downloadTask.execute(url);

        if (loc != null) {

            Toast.makeText(MapsActivity.this, "latitude:" + loc.getLatitude() +
                    " longitude:" + loc.getLongitude(), Toast.LENGTH_SHORT).show();

            origen = new LatLng(loc.getLatitude() ,loc.getLongitude());
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(origen, 15));
            mMap.addMarker(new MarkerOptions().position(origen).title("Marker in the ITSUR"));
            str_origin = "origin="+origen.latitude+","+origen.longitude;
            str_dest = "destination="+dest.latitude+","+dest.longitude;
            parameters = str_origin+"&"+str_dest+"&sensor=false";
            url = "https://maps.googleapis.com/maps/api/directions/json?"+parameters;
            downloadTask = new DownloadTask();
            downloadTask.execute(url);
        }
        else{
            locationManager.
                    requestLocationUpdates(locationManager.
                            getBestProvider(criteria,false), 800, 0, this);
        Toast.makeText(this,"Entro en el Else primera parte", Toast.LENGTH_LONG).show();
//            Log.d("Holahudgfuhgfu",location.getLatitude()+"");
        }
    }



    private String download_Url(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            iStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
            StringBuffer sb = new StringBuffer();
            String line = "";
            while( ( line = br.readLine()) != null){
                sb.append(line);
            }
            data = sb.toString();
            br.close();
        }catch(Exception e){
            Log.d("Exception", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    @Override
    public void onLocationChanged(Location location) {
       // locationManager.removeUpdates(this);
        Toast.makeText(MapsActivity.this, "latitud:" + location.getLatitude() +
                " longitud:" + location.getLongitude(), Toast.LENGTH_SHORT).show();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        Location loc =
                locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria,false));

        LatLng dest = new LatLng(20.031736, -101.152851);
        mMap.addMarker(new MarkerOptions().position(dest).title("Marker in my house"));

        if (loc != null) {
            LatLng origen = new LatLng(loc.getLatitude() ,loc.getLongitude());
            Toast.makeText(MapsActivity.this, "latitude:" + loc.getLatitude() +
                    " longitude:" + loc.getLongitude(), Toast.LENGTH_SHORT).show();
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(origen, 15));
            String str_origin = "origin="+origen.latitude+","+origen.longitude;
            String str_dest = "destination="+dest.latitude+","+dest.longitude;
            String parameters = str_origin+"&"+str_dest+"&sensor=false";
            String url = "https://maps.googleapis.com/maps/api/directions/json?"+parameters;
            DownloadTask downloadTask = new DownloadTask();
            downloadTask.execute(url);
        }
        else{
            locationManager.
                    requestLocationUpdates(locationManager.
                            getBestProvider(criteria,false), 800, 0, this);
            Toast.makeText(this,"Entro en el Else", Toast.LENGTH_LONG).show();
//            Log.d("Holahudgfuhgfu",location.getLatitude()+"");
        }

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    private class DownloadTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... url) {
            String data = "";
            try{
                data = download_Url(url[0]);
            }catch(Exception e){
                Log.d("Error",e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            ParserTask parserTask = new ParserTask();
            parserTask.execute(result);
        }
    }

    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> >{

        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {
            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;
            try{
                jObject = new JSONObject(jsonData[0]);
                JSONParser parser = new JSONParser();
                routes = parser.parse(jObject);
            }catch(Exception e){
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();
            for(int i=0;i<result.size();i++){
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();
                List<HashMap<String, String>> path = result.get(i);
                for(int j=0;j<path.size();j++){
                    HashMap<String,String> point = path.get(j);
                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);
                    points.add(position);
                }
                lineOptions.addAll(points);
                lineOptions.width(4);
                lineOptions.color(Color.rgb(0,0,255));
            }
            if(lineOptions!=null) {
                mMap.addPolyline(lineOptions);
            }
        }
    }


}

