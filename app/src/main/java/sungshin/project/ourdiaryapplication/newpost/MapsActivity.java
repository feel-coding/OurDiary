package sungshin.project.ourdiaryapplication.newpost;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import sungshin.project.ourdiaryapplication.R;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MapsActivity  extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Geocoder geocoder;
    private Button searchButton;
    private Button doneButton;
    private EditText editText;
    private String str;
    private  List<Address> addressList;
    private String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        editText = findViewById(R.id.maps_search_edt);
        searchButton = findViewById(R.id.maps_search_btn);
        doneButton = findViewById(R.id.maps_done_btn);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.maps_map);
        mapFragment.getMapAsync(this);
    }


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
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        geocoder = new Geocoder(this);

        // 맵 터치 이벤트 구현 //
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener(){
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
        ////////////////////

        // 버튼 이벤트
        searchButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                str = editText.getText().toString();
                addressList = new ArrayList<>();

                try {
                    // editText에 입력한 텍스트(주소, 지역, 장소 등)을 지오 코딩을 이용해 변환
                    addressList = geocoder.getFromLocationName(
                            str, // 주소
                            10); // 최대 검색 결과 개수
//                    Log.d("MapsActivity", "검색한 위치 주소 : " + addressList.get(0).toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    // 콤마를 기준으로 split
                    String[] splitStr = addressList.get(0).toString().split(",");
                    address = splitStr[0].substring(splitStr[0].indexOf("국") + 2, splitStr[0].length() - 2); // 주소
                    Log.d("MapsActivity", "검색한 위치 주소 : " + address);

                    String latitude = String.format("%.5f", addressList.get(0).getLatitude());// 위도

                    Log.d("MapsActivity", "검색한 위치 위도 : " + latitude);
                    String longitude = String.format("%.5f", addressList.get(0).getLongitude()); // 경도
                    Log.d("MapsActivity", "검색한 위치 경도 : " + longitude);

                    // 좌표(위도, 경도) 생성
                    LatLng point = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                    // 마커 생성
                    MarkerOptions mOptions2 = new MarkerOptions();
                    mOptions2.title(str);
                    mOptions2.snippet(address);
                    mOptions2.position(point);
                    // 마커 추가
                    mMap.addMarker(mOptions2);
                    // 해당 좌표로 화면 줌
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 15));
                } catch (IndexOutOfBoundsException e) {
                    Toast.makeText(MapsActivity.this, "잘못된 주소입니다. 다시 입력해주세요.", Toast.LENGTH_LONG).show();
                }
            }
        });

        doneButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapsActivity.this, NewPostActivity.class);
                if( address != null) {
                    intent.putExtra("location", str);
                    setResult(RESULT_OK, intent);
                }else{
                    startActivity(intent);
                }

                finish();
            }
        });

        // Add a marker in Seoul and move the camera
        LatLng cityHallStation = new LatLng(37.565931, 126.976917);
        mMap.addMarker(new MarkerOptions().position(cityHallStation).title("시청역"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(cityHallStation));
    }
}
