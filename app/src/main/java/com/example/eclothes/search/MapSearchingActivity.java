package com.example.eclothes.search;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.eclothes.R;
import com.example.eclothes.APIManager;
import com.example.eclothes.Models.District;
import com.example.eclothes.Models.Merchant;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapSearchingActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Toolbar toolbarWidget;
    String targetDistrict, targetDistrictID;
    String[] test_address;
    HashMap<String, String> district_id;
    List<Merchant> merchantList;
    List<Marker> markerList;

    View district1, district2, district3, district4, district5, district6, district7, district8, district9, district10, district11, district12, district13, district14, district15, district16, district17, district18;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_searching);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            targetDistrict = !bundle.getString("district").isEmpty() ? bundle.getString("district") : null;
        }

        test_address = new String[] {"625 Nathan Rd, Mong Kok", "Shop L2-23, Level 2, V Walk, 28 Sham Mong Rd, Sham Shui Po", "NO.7 ON 19TH FLOOR HO KING COMMERCIAL CENTRE NOS.2-16, Fa Yuen St, Mong Kok",
                                    "Shop No. L2-53&55, Level 2, V Walk, 28 Sham Mong Rd, Sham Shui Po", "Grand Century Place, L1/F, 199B", "Grand Century Place, L2/F, 248"};

        district1 = (View) findViewById(R.id.district1);
        district2 = (View) findViewById(R.id.district2);
        district3 = (View) findViewById(R.id.district3);
        district4 = (View) findViewById(R.id.district4);
        district5 = (View) findViewById(R.id.district5);
        district6 = (View) findViewById(R.id.district6);
        district7 = (View) findViewById(R.id.district7);
        district8 = (View) findViewById(R.id.district8);
        district9 = (View) findViewById(R.id.district9);
        district10 = (View) findViewById(R.id.district10);
        district11 = (View) findViewById(R.id.district11);
        district12 = (View) findViewById(R.id.district12);
        district13 = (View) findViewById(R.id.district13);
        district14 = (View) findViewById(R.id.district14);
        district15 = (View) findViewById(R.id.district15);
        district16 = (View) findViewById(R.id.district16);
        district17 = (View) findViewById(R.id.district17);
        district18 = (View) findViewById(R.id.district18);

        toolbarWidget = (Toolbar) findViewById(R.id.mapAppBar);

        district_id = new HashMap<String, String>();
        getExistingDistrict();

        if (targetDistrict != null && !targetDistrict.equals("")) {
            targetDistrictID = district_id.get(targetDistrict);

            getExistingMerchant(null, targetDistrictID);
        }

        // navigationIcon onClick
        toolbarWidget.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i("Map toolbarWidget", "navigationIcon Clicked!");
                        endActivity();
                    }
                }

        );

        if(district1 != null) {
            Log.i("testing", "testing");
            district1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    targetDistrict = "Islands";
                    targetDistrictID = district_id.get(targetDistrict);
                    Log.i("onOptionsItemSelected", targetDistrict);
                    getExistingMerchant(null, targetDistrictID);
                }
            });
        }

        if(district2 != null) {
            district2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    targetDistrict = "Kwai Tsing";
                    targetDistrictID = district_id.get(targetDistrict);
                    Log.i("onOptionsItemSelected", targetDistrict);
                    getExistingMerchant(null, targetDistrictID);
                }
            });
        }

        if(district3 != null) {
            district3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    targetDistrict = "North";
                    targetDistrictID = district_id.get(targetDistrict);
                    Log.i("onOptionsItemSelected", targetDistrict);
                    getExistingMerchant(null, targetDistrictID);
                }
            });
        }

        if(district4 != null) {
            district4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    targetDistrict = "Sai Kung";
                    targetDistrictID = district_id.get(targetDistrict);
                    Log.i("onOptionsItemSelected", targetDistrict);
                    getExistingMerchant(null, targetDistrictID);
                }
            });
        }

        if(district5 != null) {
            district5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    targetDistrict = "Sha Tin";
                    targetDistrictID = district_id.get(targetDistrict);
                    Log.i("onOptionsItemSelected", targetDistrict);
                    getExistingMerchant(null, targetDistrictID);
                }
            });
        }

        if(district6 != null) {
            district6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    targetDistrict = "Tai Po";
                    targetDistrictID = district_id.get(targetDistrict);
                    Log.i("onOptionsItemSelected", targetDistrict);
                    getExistingMerchant(null, targetDistrictID);
                }
            });
        }

        if(district7 != null) {
            district7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    targetDistrict = "Tsuen Wan";
                    targetDistrictID = district_id.get(targetDistrict);
                    Log.i("onOptionsItemSelected", targetDistrict);
                    getExistingMerchant(null, targetDistrictID);
                }
            });
        }

        if(district8 != null) {
            district8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    targetDistrict = "Tuen Mun";
                    targetDistrictID = district_id.get(targetDistrict);
                    Log.i("onOptionsItemSelected", targetDistrict);
                    getExistingMerchant(null, targetDistrictID);
                }
            });
        }

        if(district9 != null) {
            district9.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    targetDistrict = "Yuen Long";
                    targetDistrictID = district_id.get(targetDistrict);
                    Log.i("onOptionsItemSelected", targetDistrict);
                    getExistingMerchant(null, targetDistrictID);
                }
            });
        }

        if(district10 != null) {
            district10.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    targetDistrict = "Kowloon City";
                    targetDistrictID = district_id.get(targetDistrict);
                    Log.i("onOptionsItemSelected", targetDistrict);
                    getExistingMerchant(null, targetDistrictID);
                }
            });
        }

        if(district11 != null) {
            district11.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    targetDistrict = "Kwun Tong";
                    targetDistrictID = district_id.get(targetDistrict);
                    Log.i("onOptionsItemSelected", targetDistrict);
                    getExistingMerchant(null, targetDistrictID);
                }
            });
        }

        if(district12 != null) {
            district12.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    targetDistrict = "Sham Shui Po";
                    targetDistrictID = district_id.get(targetDistrict);
                    Log.i("onOptionsItemSelected", targetDistrict);
                    getExistingMerchant(null, targetDistrictID);
                }
            });
        }

        if(district13 != null) {
            district13.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    targetDistrict = "Wong Tai Sin";
                    targetDistrictID = district_id.get(targetDistrict);
                    Log.i("onOptionsItemSelected", targetDistrict);
                    getExistingMerchant(null, targetDistrictID);
                }
            });
        }

        if(district14 != null) {
            district14.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    targetDistrict = "Yau Tsim Mong";
                    targetDistrictID = district_id.get(targetDistrict);
                    Log.i("onOptionsItemSelected", targetDistrict);
                    getExistingMerchant(null, targetDistrictID);
                }
            });
        }

        if(district15 != null) {
            district15.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    targetDistrict = "Central and Western";
                    targetDistrictID = district_id.get(targetDistrict);
                    Log.i("onOptionsItemSelected", targetDistrict);
                    getExistingMerchant(null, targetDistrictID);
                }
            });
        }

        if(district16 != null) {
            district16.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    targetDistrict = "Eastern";
                    targetDistrictID = district_id.get(targetDistrict);
                    Log.i("onOptionsItemSelected", targetDistrict);
                    getExistingMerchant(null, targetDistrictID);
                }
            });
        }

        if(district17 != null) {
            district17.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    targetDistrict = "Southern";
                    targetDistrictID = district_id.get(targetDistrict);
                    Log.i("onOptionsItemSelected", targetDistrict);
                    getExistingMerchant(null, targetDistrictID);
                }
            });
        }

        if(district18 != null) {
            district18.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    targetDistrict = "Wan Chai";
                    targetDistrictID = district_id.get(targetDistrict);
                    Log.i("onOptionsItemSelected", targetDistrict);
                    getExistingMerchant(null, targetDistrictID);
                }
            });
        }
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_map_district, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i("onOptionsItemSelected", "Selected District: " +item.getTitle());
        switch (item.getItemId()) {
            case R.id.district1:
                targetDistrict = "Islands";
            case R.id.district2:
                targetDistrict = "Kwai Tsing";
            case R.id.district3:
                targetDistrict = "North";
            case R.id.district4:
                targetDistrict = "Sai Kung";
            case R.id.district5:
                targetDistrict = "Sha Tin";
            case R.id.district6:
                targetDistrict = "Tai Po";
            case R.id.district7:
                targetDistrict = "Tsuen Wan";
            case R.id.district8:
                targetDistrict = "Tuen Mun";
            case R.id.district9:
                targetDistrict = "Yuen Long";
            case R.id.district10:
                targetDistrict = "Kowloon City";
            case R.id.district11:
                targetDistrict = "Kwun Tong";
            case R.id.district12:
                targetDistrict = "Sham Shui Po";
            case R.id.district13:
                targetDistrict = "Wong Tai Sin";
            case R.id.district14:
                targetDistrict = "Yau Tsim Mong";
            case R.id.district15:
                targetDistrict = "Central and Western";
            case R.id.district16:
                targetDistrict = "Eastern";
            case R.id.district17:
                targetDistrict = "Southern";
            case R.id.district18:
                targetDistrict = "Wan Chai";
        }
        targetDistrictID = district_id.get(targetDistrict);

        Log.i("onOptionsItemSelected", targetDistrict);

        getExistingMerchant(null, targetDistrictID);

        return true;
    }
     */

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (markerList == null) {
            markerList = new ArrayList<Marker>();
            // Set Sydney and move the camera
            LatLng sydney = new LatLng(22.321550, 114.171112);

            Address address = null;
            Geocoder geocoder = new Geocoder(MapSearchingActivity.this);

            LatLng latLng = null;

            try{
                for (String location : test_address) {
                    address = geocoder.getFromLocationName(location, 1).get(0);

                    latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    markerList.add(mMap.addMarker(new MarkerOptions().position(latLng).title(location)));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15));
        } else {
            updateMapView(merchantList);
        }
    }

    private void updateMapView(List<Merchant> merchants) {
        Log.i("updateMapView", "Marker Remove");
        /*
        for (Marker marker : markerList){
            marker.remove();
        }

        markerList = null;
         */
        mMap.clear();

        markerList = new ArrayList<Marker>();

        Address address = null;
        Geocoder geocoder = new Geocoder(MapSearchingActivity.this);

        LatLng sydney = null;
        LatLng latLng = null;

        Log.i("updateMapView", "Before Try");

        try{
            address = geocoder.getFromLocationName(targetDistrict + ", Hong Kong", 1).get(0);
            sydney = new LatLng(address.getLatitude(), address.getLongitude());

            for (Merchant merchant : merchants) {
                if (merchant.getLocation() == null)
                    continue;

                address = geocoder.getFromLocationName(merchant.getLocation().getAddress(), 1).get(0);

                latLng = new LatLng(address.getLatitude(), address.getLongitude());
                markerList.add(mMap.addMarker(new MarkerOptions().position(latLng).title(merchant.getShopName())));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.i("updateMapView", "After Try");

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15));
    }

    private void getExistingDistrict() {
        Call<List<District>> call = APIManager.getInstance().getAPIService().getDistricts();

        call.enqueue(new Callback<List<District>>() {
            @Override
            public void onResponse(Call<List<District>> call, Response<List<District>> response) {
                if (!response.isSuccessful()) {
                    Log.i("onResponse", "Fail!");
                    return;
                }

                List<District> districts = response.body();

                for (District district : districts) {
                    district_id.put( district.getDistrict(), district.get_id());
                    Log.i("districts", district.get_id() + ", " + district.getDistrict());
                }
            }

            @Override
            public void onFailure(Call<List<District>> call, Throwable t) {
                Toast.makeText(MapSearchingActivity.this, "Fail to get data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getExistingMerchant(String keyword, String district_id) {
        Call<List<Merchant>> call = APIManager.getInstance().getAPIService().getMerchants(keyword, district_id);

        call.enqueue(new Callback<List<Merchant>>() {
            @Override
            public void onResponse(Call<List<Merchant>> call, Response<List<Merchant>> response) {
                if (!response.isSuccessful()) {
                    Log.i("onResponse", "Fail!");
                    return;
                }

                Log.i("getExistingMerchant", response.code() + "");

                merchantList = new ArrayList<>();
                merchantList = response.body();

                updateMapView(merchantList);
            }

            @Override
            public void onFailure(Call<List<Merchant>> call, Throwable t) {
                Toast.makeText(MapSearchingActivity.this, "Fail to get data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void endActivity() {
        Log.i("endActivity", "Activity Finish!");
        finish();
    }
}