package sungshin.project.ourdiaryapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

public class DocumentActivity extends AppCompatActivity {

    ImageButton settingBtn;
    ImageView chat_img;
//    ImageView like_img;
//    TextView like_textView;
//    TextView chat_textView;
    TextView diaryContentTv;

    //GridView 이미지 배열
    private int[] imageIDs = new int[] { R.drawable.docimg, R.drawable.docimg, R.drawable.docimg, R.drawable.docimg, R.drawable.docimg};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document);
        settingBtn = findViewById(R.id.setting_btn);
        chat_img = findViewById(R.id.chat_img);
        diaryContentTv = findViewById(R.id.diaryContentTv);
        Intent intent = getIntent();
        String content = intent.getStringExtra("diary_content");
        diaryContentTv.setText(content);

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
                        //todo:클릭시 이벤트 작성
                        break;
                    case R.id.delete:
                        //todo:클릭시 이벤트 작성
                        break;
                }
                return true;
            }
        });
        popup.show();
    }
}

