package sungshin.project.ourdiaryapplication.newpost;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import sungshin.project.ourdiaryapplication.R;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LocClickActivity  extends FragmentActivity implements OnMapReadyCallback {
    GoogleMap gMap;
    Geocoder geocoder;
    Button finishBtn;
    String location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loc_click);

        finishBtn = findViewById(R.id.locClickActivity_finish_btn);

        Intent intent = getIntent();
        location = intent.getStringExtra("loc");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.locClickActivity_map);
        mapFragment.getMapAsync(this);

        finishBtn.setOnClickListener(v -> finish());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        geocoder = new Geocoder(this);

        // 맵 터치 이벤트 구현 //
        gMap.setOnMapClickListener(new GoogleMap.OnMapClickListener(){
            @Override
            public void onMapClick(LatLng point) {
                MarkerOptions mOptions = new MarkerOptions();
                // 마커 타이틀
                mOptions.title("마커 좌표");
                Double latitude = point.latitude; // 위도
                Double longitude = point.longitude; // 경도
                // 마커의 스니펫(간단한 텍스트) 설정
                mOptions.snippet(latitude.toString() + ", " + longitude.toString());
                // LatLng: 위도 경도 쌍을 나타냄
                mOptions.position(new LatLng(latitude, longitude));
                // 마커(핀) 추가
                googleMap.addMarker(mOptions);
            }
        });

        List<Address> addressList = new ArrayList<>();
        try {
            // 지오 코딩을 이용해 변환
            addressList = geocoder.getFromLocationName(
                    location, // 주소
                    10); // 최대 검색 결과 개수
            Log.d("MapsActivity", "검색한 위치 주소 : " + addressList.get(0).toString());
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        // 콤마를 기준으로 split
        String []splitStr = addressList.get(0).toString().split(",");
        String address = splitStr[0].substring(splitStr[0].indexOf("국") + 2 ,splitStr[0].length() - 2); // 주소
        Log.d("MapsActivity", "검색한 위치 주소 : " + address);

        String latitude = String.format("%.5f", addressList.get(0).getLatitude());// 위도

        Log.d("MapsActivity", "검색한 위치 위도 : " + latitude);
        String longitude = String.format("%.5f", addressList.get(0).getLongitude()); // 경도
        Log.d("MapsActivity", "검색한 위치 경도 : " + longitude);

        // 좌표(위도, 경도) 생성
        LatLng point = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
        // 마커 생성
        MarkerOptions mOptions2 = new MarkerOptions();
        mOptions2.title(location);
        mOptions2.snippet(address);
        mOptions2.position(point);
        // 마커 추가
        gMap.addMarker(mOptions2);
        // 해당 좌표로 화면 줌
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point,15));
    }
}
