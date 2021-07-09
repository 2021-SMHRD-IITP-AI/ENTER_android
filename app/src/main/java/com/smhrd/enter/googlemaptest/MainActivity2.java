package com.smhrd.enter.googlemaptest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MainActivity2 extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap googleMap;
    private EditText edt_search;
    private Button btn_search;
    private Geocoder geocoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        edt_search = findViewById(R.id.edt_search);
        btn_search = findViewById(R.id.btn_search);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }



    @Override //본격적으로 위도경도를 통한 마커 찍기
    public void onMapReady(GoogleMap googleMap) {
        String text1 = "물품 카테고리 : 식품 \n요청시간 : 7월 8일 21시\n요청사항 : 조심히 다뤄주세요";
        String text2 = "물품 카테고리 : 전자제품 \n요청시간 : 7월 9일 12시\n요청사항 : 조심히 다뤄주세요";
        String text3 = "물품 카테고리 : 가구 \n요청 시간 : 7월 10일 11시\n요청사항 : 최대한 빨리와주세요";

        this.googleMap = googleMap;
        // 롯데월드 37.5111° N, 127.0982° E
        LatLng lotte = new LatLng(37.5111, 127.0982);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(lotte)); //카메 중심 위도
        googleMap.moveCamera(CameraUpdateFactory.zoomTo(15)); // 카메라 범위
        MarkerOptions markerOptions = new MarkerOptions().position(lotte).title("서울 >> 광주");
        markerOptions.snippet(text1);
        googleMap.addMarker(markerOptions);

        //롯데타워 37.5125° N, 127.1024° E
        LatLng tower = new LatLng(37.5088, 127.0950);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(lotte));
        googleMap.moveCamera(CameraUpdateFactory.zoomTo(15));
        MarkerOptions markerOptions2 = new MarkerOptions().position(tower).title("서울 >> 대구");
        markerOptions2.snippet(text2);
        googleMap.addMarker(markerOptions2);

        //잠실역 37.5133° N, 127.1001° E
        LatLng station = new LatLng(37.5133, 127.1001);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(lotte));
        googleMap.moveCamera(CameraUpdateFactory.zoomTo(15));
        MarkerOptions markerOptions3 = new MarkerOptions().position(station).title("서울 >> 제주");
        markerOptions3.snippet(text3);
        googleMap.addMarker(markerOptions3);



        //snippet 한줄이상으로 출력하는 방법
        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {

                Context context = getApplicationContext(); //or getActivity(), YourActivity.this, etc.

                LinearLayout info = new LinearLayout(context);
                info.setOrientation(LinearLayout.VERTICAL);

                TextView title = new TextView(context);
                title.setTextColor(Color.BLACK);
                title.setGravity(Gravity.CENTER);
                title.setTypeface(null, Typeface.BOLD);
                title.setText(marker.getTitle());

                TextView snippet = new TextView(context);
                snippet.setTextColor(Color.GRAY);
                snippet.setText(marker.getSnippet());

                info.addView(title);
                info.addView(snippet);

                return info;
            }
        });

       final Geocoder geocoder = new Geocoder(getApplicationContext());

       btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            List<Address> list = null;

            String str = edt_search.getText().toString();

                try {
                    list = geocoder.getFromLocationName(
                            str,
                            10
                    );
                    double lat = list.get(0).getLatitude();
                    double lon = list.get(0).getLongitude();

                    Log.v("result","내가 조회한 위도 경도" +lat+" " + lon);

                    LatLng search = new LatLng(lat,lon);
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(search));
                    googleMap.moveCamera(CameraUpdateFactory.zoomTo(15));

               } catch (IOException e) {
                    e.printStackTrace();
                }



           }
        });


       //만약에 등록할때 이걸 사용하면 된다.
        List<Address> ex = null;

        try {
            ex = geocoder.getFromLocationName(
                    "광주광역시청",
                    10
            );
            double lat = ex.get(0).getLatitude();
            double lon = ex.get(0).getLongitude();

            Log.v("result","내가 조회한 위도 경도" +lat+" " + lon);

            LatLng enroll = new LatLng(lat,lon);
            MarkerOptions markerOptions4 = new MarkerOptions().position(enroll).title("서울 >> 대구");
            markerOptions4.snippet(text2);
            googleMap.addMarker(markerOptions4);

        } catch (IOException e) {
            e.printStackTrace();
        }


        List<Address> ex1 = null;
        try {
            ex1 = geocoder.getFromLocationName(
                    "광주 가정법원",
                    10
            );
            double lat = ex1.get(0).getLatitude();
            double lon = ex1.get(0).getLongitude();

            Log.v("result","내가 조회한 위도 경도" +lat+" " + lon);

            LatLng test = new LatLng(lat,lon);
            MarkerOptions markerOptions5 = new MarkerOptions().position(test).title("서울 >> 대구");
            markerOptions5.snippet(text2);
            googleMap.addMarker(markerOptions5);

        } catch (IOException e) {
            e.printStackTrace();
        }


        //위치 접근 제한
        googleMap.setOnInfoWindowClickListener(infoWindowClickListener);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
        } else {
            checkLocationPermissionWithRationale();
        }
    }

    //마커 내 정보창 클릭이벤트
    GoogleMap.OnInfoWindowClickListener infoWindowClickListener = new GoogleMap.OnInfoWindowClickListener() {
        @Override
        public void onInfoWindowClick(Marker marker) {
           Intent intent = new Intent(getApplicationContext(),C_Delivery.class);
           startActivity(intent);
        }
    };

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private void checkLocationPermissionWithRationale() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                new AlertDialog.Builder(this)
                        .setTitle("위치정보")
                        .setMessage("이 앱을 사용하기 위해서는 위치정보에 접근이 필요합니다. 위치정보 접근을 허용하여 주세요.")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(MainActivity2.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        }).create().show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        googleMap.setMyLocationEnabled(true);
                    }
                } else {
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }
}
