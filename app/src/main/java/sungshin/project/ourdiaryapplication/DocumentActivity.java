package sungshin.project.ourdiaryapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;

public class DocumentActivity extends AppCompatActivity {

    ImageButton settingBtn;
    ImageView chat_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document);
        settingBtn = findViewById(R.id.setting_btn);
        chat_img = findViewById(R.id.chat_img);

        //말풍선 누르면 댓글창으로 이동
        chat_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DocumentActivity.this, CommentActivity.class);
                startActivity(i);
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
