package sungshin.project.ourdiaryapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import sungshin.project.ourdiaryapplication.CommentAdapter;
import sungshin.project.ourdiaryapplication.CommentItem;
import sungshin.project.ourdiaryapplication.R;

public class CommentActivity extends AppCompatActivity {

    private ListView commentList;
    CommentAdapter commentAdapter;
    ArrayList<CommentItem> commentItem;

    EditText commentInput;
    ImageButton imgbtn;
//    public static Context context_comment;
//    public int chat_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        commentList = findViewById(R.id.commentList);
        commentItem = new ArrayList<CommentItem>();

        //댓글 내용
        commentItem.add(new CommentItem("happy_eat",
                "신용순","댓글 내용을 적습니다.","2018.10.25, 11:30"));
        commentItem.add(new CommentItem("silence_99",
                "김범","댓글 내용을 적습니다.","2018.10.25, 11:50"));

        commentAdapter = new CommentAdapter(this,commentItem);
        commentList.setAdapter(commentAdapter);

        commentAdapter.notifyDataSetChanged();

        //리스트 item 개수 변수에 저장, DocumentActivity에서 액티비티 접근
//        context_comment = this;
//        chat_num = commentAdapter.getCount();
//        Log.d("정보","aaa");

        //버튼 클릭시 내용 추가
        imgbtn = findViewById(R.id.imageButton_add);
        commentInput = findViewById(R.id.editText_comment);
        imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = commentInput.getText().toString();
                if(text.length()==0)
                    Toast.makeText(CommentActivity.this,"댓글을 입력하세요",Toast.LENGTH_LONG).show();
                else {
                    commentItem.add(new CommentItem("happy_eat",
                            "신용순", text, "2018.10.26, 14:00"));
                    commentAdapter.notifyDataSetChanged();
                    //성공 메세지
                    Toast.makeText(CommentActivity.this, "댓글이 추가되었습니다", Toast.LENGTH_SHORT).show();
                    commentInput.setText("");
                    //데이터 추가 위치로 이동
                    commentList.setSelection(commentItem.size()-1);
                }
            }
        });
    }
}
