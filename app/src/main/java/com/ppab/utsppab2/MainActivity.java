package com.ppab.utsppab2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ppab.utsppab2.api.ApiClient;
import com.ppab.utsppab2.api.BaseApiService;
import com.ppab.utsppab2.api.MapData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    GoogleMap mMap;
    SupportMapFragment mapFragment;
    SearchView searchView;
    BaseApiService mApiService;
    private MapData mapAtTheMoment;
    private ArrayList<destination> destinations = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mApiService = ApiClient.getAPIService();
        searchView = findViewById(R.id.sv_location);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.google_map);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                String loc = searchView.getQuery().toString();
                String city, country;

                if (loc != null || !loc.equals("")){
                    if (loc.contains(",")){
                        String[] place = loc.split(",");
                        city = place[0];
                        country = place[1];
                    }else{
                        city = loc;
                        country = "";
                    }

                    mApiService.mapReqApi(city, country).enqueue(new Callback<List<MapData>>() {
                        @Override
                        public void onResponse(Call<List<MapData>> call, Response<List<MapData>> response) {
                            if (!response.isSuccessful()){
                                Toast.makeText(MainActivity.this, response.code(), Toast.LENGTH_SHORT).show();
                                return;
                            }

                            if (response.body() == null){
                                Toast.makeText(MainActivity.this, "tidak ditemukan!", Toast.LENGTH_SHORT).show();
                            }else{
                                List<MapData> mapData = response.body();
                                LatLng latLng = new LatLng(mapData.get(0).getLatitude(), mapData.get(0).getLongitude());
                                mMap.setOnMarkerClickListener(MainActivity.this);
                                mMap.addMarker(new MarkerOptions().position(latLng).title(mapData.get(0).getName()));
                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,9));
                                mapAtTheMoment = mapData.get(0);
                            }
                        }

                        @Override
                        public void onFailure(Call<List<MapData>> call, Throwable t) {
                            Toast.makeText(MainActivity.this, "failure", Toast.LENGTH_SHORT).show();
                        }
                    });
//                    Geocoder geocoder = new Geocoder(MainActivity.this);
//                    try {
//                        addressList = geocoder.getFromLocationName(loc, 1);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    Address address = addressList.get(0);
//                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
//                    mMap.addMarker(new MarkerOptions().position(latLng).title(loc));
//                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        mapFragment.getMapAsync(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.map:
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                return true;
            case R.id.wishlist:
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("destinations", destinations);
                Intent intent = new Intent(this, WishlishActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                return true;
            case R.id.dashboard:
                Bundle bundles = new Bundle();
                bundles.putParcelableArrayList("destinations", destinations);
                Intent intents = new Intent(this, dashboardActivity.class);
                intents.putExtras(bundles);
                startActivity(intents);
                return true;
            case R.id.language:
                Intent languageIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(languageIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
    }


    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                destinations.add(new destination(mapAtTheMoment.getName(), mapAtTheMoment.getState(), mapAtTheMoment.getID()));
                Toast.makeText(MainActivity.this, R.string.success, Toast.LENGTH_SHORT).show();
            }
        }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setMessage(R.string.dialog1);
        builder.show();
        return true;
    }

}