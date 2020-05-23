package sungshin.project.ourdiaryapplication.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.HashSet;

import sungshin.project.ourdiaryapplication.R;

public class NameAndNicknameSettingActivity extends AppCompatActivity {

    Button settingFinishBtn;
    EditText nameEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_and_nickname_setting);
        settingFinishBtn = findViewById(R.id.nameSettingFinishBtn);
        nameEdit = findViewById(R.id.nameSettingEdit);
        settingFinishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nameEdit.getText().toString().equals("")) {
                    Toast.makeText(NameAndNicknameSettingActivity.this, "이름을 입력해주세요", Toast.LENGTH_SHORT).show();
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(NameAndNicknameSettingActivity.this);
                    builder.setTitle("이름 설정")
                            .setMessage("이름을 " + nameEdit.getText().toString() + "으로 하시겠습니까?")
                            .setIcon(android.R.drawable.ic_dialog_info)
                            .setPositiveButton("예", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(NameAndNicknameSettingActivity.this, "닉네임과 이름 설정이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            })
                            .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }
        });

    }
}
