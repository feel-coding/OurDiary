package sungshin.project.ourdiaryapplication.newpost;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import sungshin.project.ourdiaryapplication.R;
import sungshin.project.ourdiaryapplication.friendlist.FrdSearchActivity;
import sungshin.project.ourdiaryapplication.friendlist.FrdsearchAdapter;

import android.app.DatePickerDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class NewPostActivity extends AppCompatActivity {

    private ImageButton locAddBtn;
    private Button locResult;
    private ImageButton dateAddBtn;
    private Button dateResult;
    private DatePickerDialog.OnDateSetListener callbackMethod;
    private Button cancelBtn;
    private Button finishBtn;
    private ImageButton photoBtn;
    private GridView gridView;
    ClipData clipData;
    ImageButton newpost_fTag_addBtn;


    public static final int PICTURE_REQUEST_CODE = 100;
    public static final int MAP_SEARCH_REQUEST_CODE = 101;
    public static final int REQUEST_FRIENDTAG = 102;

    long now = System.currentTimeMillis();
    Date currentTime = new Date(now);
    SimpleDateFormat weekdayFormat = new SimpleDateFormat("EE", Locale.getDefault());
    SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.getDefault());
    SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.getDefault());
    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());

    String currentWeekDay = weekdayFormat.format(currentTime);
    String currentYear = yearFormat.format(currentTime);
    String currentMonth = monthFormat.format(currentTime);
    String currentDay = dayFormat.format(currentTime);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        locAddBtn = findViewById(R.id.newpost_loc_addBtn);
        locResult = findViewById(R.id.newpost_loc_btn);
        dateResult = findViewById(R.id.newpost_date_btn);
        dateAddBtn = findViewById(R.id.newpost_date_addBtn);
        cancelBtn = findViewById(R.id.btn_newpost_cancel);
        photoBtn = findViewById(R.id.newpost_photo_btn);
        gridView = findViewById(R.id.newpost_photo_gridview);

        dateResult.setText(currentYear +"-"+currentMonth+"-"+currentDay+"-"+currentWeekDay);
        locResult.setVisibility(View.INVISIBLE);

        initDetailContainerBtn();
        initBtn();

        //친구태그 +버튼 클릭
        newpost_fTag_addBtn = findViewById(R.id.newpost_fTag_addBtn);
        newpost_fTag_addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(NewPostActivity.this, FrdSearchActivity.class);
                startActivityForResult(i, REQUEST_FRIENDTAG);
            }
        });
    }

    private void initBtn(){

        cancelBtn.setOnClickListener(v -> finish());

        photoBtn.setOnClickListener( v -> {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
                    //사진을 여러개 선택할수 있도록 한다
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    intent.setType("image/*");
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICTURE_REQUEST_CODE);
                }
        );

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case PICTURE_REQUEST_CODE : {
                if (resultCode == RESULT_OK) {
                    //ClipData 또는 Uri를 가져온다
                    Uri uri = data.getData();
                    clipData = data.getClipData();

                    //이미지 URI 를 이용하여 이미지뷰에 순서대로 세팅한다.
                    if (clipData != null) {

                        if(clipData.getItemCount() > 5){
                            Toast.makeText(this, "최대 5장까지 선택가능합니다.", Toast.LENGTH_SHORT).show();
                        }else {
                            ImageAdapter imageAdapter = new ImageAdapter(this);
                            gridView.setAdapter(imageAdapter);
                        }

                    } else if (uri != null) {
//                    image1.setImageURI(uri);
                    }
                }
            }
            break;

            case MAP_SEARCH_REQUEST_CODE : {
                if (resultCode == RESULT_OK) {
                    String loc = data.getExtras().getString("location");
                    locResult.setVisibility(View.VISIBLE);
                    locResult.setText(loc);
                }
            }
            break;

            //todo:FrdSearchActivity에서 넘겨준 친구 데이터 받아 서버 Diary에 친구 리스트 추가, 화면에 이름 추가
            case REQUEST_FRIENDTAG: {
                if(resultCode == RESULT_OK) {
                    Toast.makeText(NewPostActivity.this,data.getStringExtra("Name")
                            +data.getStringExtra("Nick")+data.getIntExtra("Seq",0),Toast.LENGTH_SHORT).show();
                }
            }
            break;
            default:
                throw new IllegalStateException("Unexpected value: " + requestCode);
        }
    }

    private void initDetailContainerBtn(){

        // 위치 Add Button
        locAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewPostActivity.this, MapsActivity.class);
                startActivityForResult(intent, MAP_SEARCH_REQUEST_CODE);
            }
        });

        // 날짜 Add Button
        callbackMethod = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = year + "-" + (month+1) + "-" + dayOfMonth;
                String day = "";

                try{
                    day = getDateDay(date, "yyyy-MM-dd");
                } catch (Exception e){

                }

                dateResult.setText(date + "-" +day);
            }
        };

        dateAddBtn.setOnClickListener(v->
                new DatePickerDialog(this, callbackMethod, Integer.parseInt(currentYear),Integer.parseInt(currentMonth)-1
                        ,Integer.parseInt(currentDay)).show()
        );

        locResult.setOnClickListener(v -> {
            Intent intent = new Intent(NewPostActivity.this, LocClickActivity.class);
            intent.putExtra("loc", locResult.getText().toString());
            startActivity(intent);
        });
    }

    // 요일 구하는 함수
    public static String getDateDay(String date, String dateType) throws Exception{

        String day = "";

        SimpleDateFormat dateFormat = new SimpleDateFormat(dateType);
        Date nDate = dateFormat.parse(date);

        Calendar cal = Calendar.getInstance();
        cal.setTime(nDate);

        int dayNum = cal.get(Calendar.DAY_OF_WEEK);

        switch (dayNum) {
            case 1:
                day = "일";
                break;
            case 2:
                day = "월";
                break;
            case 3:
                day = "화";
                break;
            case 4:
                day = "수";
                break;
            case 5:
                day = "목";
                break;
            case 6:
                day = "금";
                break;
            case 7:
                day = "토";
                break;

        }

        return day;
    }

    class ImageAdapter extends BaseAdapter{

        Context context;

        public ImageAdapter(Context context) {
            this.context = context;
        }
        @Override
        public int getCount() {
            return clipData.getItemCount();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ImageView imageView;

            if(convertView == null){
                imageView = new ImageView(context);
                imageView.setLayoutParams(new GridView.LayoutParams(150,150));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                gridView.setStretchMode(GridView.STRETCH_SPACING_UNIFORM);
            }else{
                imageView = (ImageView) convertView;
            }


            imageView.setImageURI(clipData.getItemAt(position).getUri());
            Log.d("newpost", "그리드뷰 아이템 uri");
            return imageView;
        }

    }
}
