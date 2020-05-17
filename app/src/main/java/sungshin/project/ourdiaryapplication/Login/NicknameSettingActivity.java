package sungshin.project.ourdiaryapplication.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import sungshin.project.ourdiaryapplication.R;

public class NicknameSettingActivity extends AppCompatActivity {

    Button settingFinishBtn;
    TextView nicknameAlreadyExistTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nickname_setting);
        settingFinishBtn = findViewById(R.id.nicknameSettingFinishBtn);
        nicknameAlreadyExistTv = findViewById(R.id.nicknameAlreadyExist);
        settingFinishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if() 그런 닉네임이 이미 있으면
                nicknameAlreadyExistTv.setVisibility(View.VISIBLE);
                //else
                nicknameAlreadyExistTv.setVisibility(View.GONE);
            }
        });
    }
}
