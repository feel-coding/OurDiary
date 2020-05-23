package sungshin.project.ourdiaryapplication.mypage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.concurrent.locks.Lock;

import sungshin.project.ourdiaryapplication.R;

public class LockSettingActivity extends AppCompatActivity {

    private RadioGroup radioGroup;
    public final String SHARED_PREF_PASSWORD = "2000";
    TextView changePasswordTv;
    final int REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_setting);

        //뒤로가기 버튼 만들기
        Toolbar toolbar = findViewById(R.id.lock_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
        actionBar.setHomeAsUpIndicator(R.drawable.arrow_back);

        changePasswordTv = findViewById(R.id.changePwTv);

        SharedPreferences sharedPref = getSharedPreferences("pw", Context.MODE_PRIVATE);
        String p = sharedPref.getString(SHARED_PREF_PASSWORD, "-1");
        if(p.equals("-1")) {
            RadioButton rb = findViewById(R.id.noPassword);
            rb.setChecked(true);
            changePasswordTv.setTextColor(getResources().getColor(R.color.gray));
            changePasswordTv.setClickable(false);
        }
        else{
            RadioButton rb = findViewById(R.id.password);
            rb.setChecked(true);
        }

        radioGroup = findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.noPassword){
                    SharedPreferences sharedPref = getSharedPreferences("pw", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString(SHARED_PREF_PASSWORD, "-1");
                    editor.apply();
                    changePasswordTv.setTextColor(getResources().getColor(R.color.gray));
                    changePasswordTv.setClickable(false);
                }
                else{
                    SharedPreferences sharedPref = getSharedPreferences("pw", Context.MODE_PRIVATE);
                    String p = sharedPref.getString(SHARED_PREF_PASSWORD, "-1");
                    if(p.equals("-1")) {
                        Intent intent = new Intent(LockSettingActivity.this, PasswordSettingActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                    }
                }
            }
        });
    }

    public void passwordCheck(View v) {
        RadioButton rb;
        switch (v.getId()) {
            case R.id.noPwTv:
                rb = findViewById(R.id.noPassword);
                rb.setChecked(true);
                changePasswordTv.setTextColor(getResources().getColor(R.color.gray));
                changePasswordTv.setClickable(false);
                break;
            case R.id.yesPwTv:
                rb = findViewById(R.id.password);
                rb.setChecked(true);
                changePasswordTv.setTextColor(getResources().getColor(R.color.black));
                changePasswordTv.setClickable(true);
        }
    }

    public void changePassword(View v) {
        Intent intent = new Intent(this, PasswordSettingActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("testtest", "들어옴");
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            changePasswordTv.setTextColor(getResources().getColor(R.color.black));
            changePasswordTv.setClickable(true);
        }
    }
}
