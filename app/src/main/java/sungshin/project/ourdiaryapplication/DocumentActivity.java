package sungshin.project.ourdiaryapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.w3c.dom.Document;

import java.math.BigInteger;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sungshin.project.ourdiaryapplication.Network.Diary;
import sungshin.project.ourdiaryapplication.Network.RetrofitManager;
import sungshin.project.ourdiaryapplication.Network.ServerApi;
import sungshin.project.ourdiaryapplication.home.ImageShowActivity;

public class DocumentActivity extends AppCompatActivity {

    //ImageButton settingBtn;
    ImageView chat_img;
//    ImageView like_img;
//    TextView like_textView;
//    TextView chat_textView;
    EditText diaryContentEt;
    Button changesave_btn1;
    ImageButton backBtn;
    TextView toolbarTitleTv;
    Intent intent;
    TextView diaryTitleTv;
    private ServerApi serverApi;
    ImageView image1;
    ImageView image2;
    ImageView image3;
    ImageView image4;
    ImageView like_img;
    BigInteger seq;
    Integer likeCount;
    TextView like_textView;

    //GridView 이미지 배열
    private int[] imageIDs = new int[] { R.drawable.docimg, R.drawable.docimg, R.drawable.docimg, R.drawable.docimg, R.drawable.docimg};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document);
        intent = getIntent();
        //settingBtn = findViewById(R.id.setting_btn);
//        Toolbar toolbar = findViewById(R.id.diary_toolbar);
//        setSupportActionBar(toolbar);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
//        actionBar.setDisplayShowTitleEnabled(false);
//        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
//        actionBar.setHomeAsUpIndicator(R.drawable.ic_chevron_left_black_24dp);
        chat_img = findViewById(R.id.chat_img);
        image1 = findViewById(R.id.image1_imageview);
        image2 = findViewById(R.id.image2_imageview);
        image3 = findViewById(R.id.image3_imageview);
        image4 = findViewById(R.id.image4_imageview);
        backBtn = findViewById(R.id.back_btn);
        toolbarTitleTv = findViewById(R.id.toolbar_title);
        diaryTitleTv = findViewById(R.id.diary_title);
        diaryContentEt = findViewById(R.id.diaryContentEt);
        changesave_btn1 = findViewById(R.id.changesave_btn1);
        like_img = findViewById(R.id.like_img);
        like_textView = findViewById(R.id.like_textView);
        toolbarTitleTv.setText(intent.getStringExtra("diary_date"));
        diaryTitleTv.setText(intent.getStringExtra("diary_title"));
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        String content = intent.getStringExtra("diary_content");
//        likeCount = new Integer(getIntent().getExtras().getString("diary_likecount"));
//        Log.d("정보", ">>>" + likeCount.toString());

        //좋아요 수
//        like_textView.setText(likeCount.toString());
        diaryContentEt.setText(content);

        Glide.with(this).load("http://blogfiles.naver.net/MjAxODA0MDJfMTY2/MDAxNTIyNjEzODAzNTU2.qc9dBd0xqz8zvfwsUFBYX87tqf5iUSS7VZXxhdLOb5Ig.mqOQJ2SD3sFyXufe6lxAVhGdh30Fn_5lavWPdIf-dtEg.JPEG.wjd9286/P20170408_180850930_F6F3D0B8-C35A-4489-BEDD-638E8DDB257C.JPG").into(image1);
        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DocumentActivity.this, ImageShowActivity.class);
                intent.putExtra("url", "http://blogfiles.naver.net/MjAxODA0MDJfMTY2/MDAxNTIyNjEzODAzNTU2.qc9dBd0xqz8zvfwsUFBYX87tqf5iUSS7VZXxhdLOb5Ig.mqOQJ2SD3sFyXufe6lxAVhGdh30Fn_5lavWPdIf-dtEg.JPEG.wjd9286/P20170408_180850930_F6F3D0B8-C35A-4489-BEDD-638E8DDB257C.JPG");
                startActivity(intent);
            }
        });
        Glide.with(this).load("http://blogfiles.naver.net/20140611_220/naddong2_1402496765245xLFER_JPEG/%28%BF%A9%C7%E0%29%C7%C1%B6%FB%BD%BA-%C6%C4%B8%AE-2014-06-02-16-22-09.jpg").into(image2);
        //GridView Adapter
        GridView doc_img = findViewById(R.id.doc_img);
        ImageGridAdapter imageGridAdapter = new ImageGridAdapter(this, imageIDs);
        doc_img.setAdapter(imageGridAdapter);

        //말풍선 리스트 item 개수 가져오기
        //todo:DB 댓글 개수 읽어오기
//        int chat_num = ((CommentActivity)CommentActivity.context_comment).chat_num;
//        chat_textView = findViewById(R.id.chat_textView);
//        chat_textView.setText(chat_num);


        //말풍선 누르면 댓글창으로 이동
        chat_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DocumentActivity.this, CommentActivity.class);
                startActivity(i);
            }
        });

        //하트 누르면 숫자 1씩 증가
//        like_img = findViewById(R.id.like_img);
//        like_textView = findViewById(R.id.like_textView);
//        like_img.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

        //좋아요 클릭
        like_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serverApi = RetrofitManager.getInstance().getServerApi(DocumentActivity.this);
                seq = new BigInteger(getIntent().getExtras().getString("diary_seq"));
                Log.d("정보", "Seq=" + seq.toString());

                serverApi.likesDiary(seq).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.isSuccessful()) {
                            likeCount++;
                            like_textView.setText(likeCount);
                            Toast.makeText(DocumentActivity.this, "좋아요를 클릭하셨습니다", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.d("정보", "LikesDiary Server Error" + response.errorBody().toString() + ":" + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.d("정보", "LikesDiary Server Failure");
                    }
                });
            }
        });
    }


    public void onClick(View button) {
        //팝업메뉴 객체 생성
        PopupMenu popup = new PopupMenu(this, button);
        //팝업 xml inflate
        popup.getMenuInflater().inflate(R.menu.popup,popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.edit:
                        //편집 클릭시 이벤트 작성
                        diaryContentEt.setEnabled(true);
                            changesave_btn1.setVisibility(View.VISIBLE);
                            //저장 버튼 클릭
                            changesave_btn1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    diaryContentEt.setEnabled(false);
                                    Toast.makeText(DocumentActivity.this, "글이 저장되었습니다",
                                            Toast.LENGTH_LONG).show();
                                    changesave_btn1.setVisibility(View.INVISIBLE);
                                }
                            });

                        break;
                    case R.id.delete:
                        //삭제 클릭시 이벤트 작성
                        AlertDialog.Builder builder = new AlertDialog.Builder(DocumentActivity.this);
                        builder.setMessage("글을 삭제할까요?");
                        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //글 삭제 서버연동
                                serverApi = RetrofitManager.getInstance().getServerApi(DocumentActivity.this);
                                seq = new BigInteger(getIntent().getExtras().getString("diary_seq"));
                                serverApi.deleteDiary(seq).enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        if (response.isSuccessful()) {

                                            Toast.makeText(DocumentActivity.this, "글이 삭제되었습니다", Toast.LENGTH_LONG).show();
                                            finish();
                                        } else {

                                        }
                                    }
                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {

                                    }
                                });
                            }
                        });
                        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(DocumentActivity.this,"삭제를 취소합니다",Toast.LENGTH_LONG).show();
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                        break;
                }
                return true;
            }
        });
        popup.show();
    }
}

