package sungshin.project.ourdiaryapplication.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sungshin.project.ourdiaryapplication.Network.ReqUserUpdateMe;
import sungshin.project.ourdiaryapplication.Network.RetrofitManager;
import sungshin.project.ourdiaryapplication.Network.ServerApi;
import sungshin.project.ourdiaryapplication.Network.ServerError;
import sungshin.project.ourdiaryapplication.Network.User;
import sungshin.project.ourdiaryapplication.R;
import sungshin.project.ourdiaryapplication.main.MainActivity;

import static sungshin.project.ourdiaryapplication.Login.SignupActivity.SHARED_PREF_USER_NAME;

public class NicknameSettingActivity extends AppCompatActivity {

    Button settingFinishBtn;
    private Gson gson = new Gson();
    TextView nicknameAlreadyExistTv;
    EditText nickNameEdit;
    private ServerApi serverApi;
    Intent intent;
    String email;
    String password;
    String name;
    static final String SHARED_PREF_EMAIL = "EMAIL";
    static final String SHARED_PREF_LOGIN_PW = "PASSWORD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nickname_setting);
        intent = getIntent();
        email = intent.getStringExtra("email");
        password = intent.getStringExtra("password");
        serverApi = RetrofitManager.getInstance().getServerApi(this);
        settingFinishBtn = findViewById(R.id.nicknameSettingFinishBtn);
        nicknameAlreadyExistTv = findViewById(R.id.nicknameAlreadyExist);
        nickNameEdit = findViewById(R.id.nickname_edit);
        serverApi.getUserMe().enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    name = response.body().getName();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
        settingFinishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("settingnickname", "clicked");
                ReqUserUpdateMe req = new ReqUserUpdateMe();
                req.setNick(nickNameEdit.getText().toString());
                //if() 그런 닉네임이 이미 있으면
                req.setName(name);
                serverApi.updateUserMe(req).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.isSuccessful()) {
                            Log.d("settingnickname", "success");
                            nicknameAlreadyExistTv.setVisibility(View.GONE);
                            Toast.makeText(NicknameSettingActivity.this, "닉네임 설정이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                            SharedPreferences sharedPref = getSharedPreferences("login", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString(SHARED_PREF_EMAIL, email);
                            editor.putString(SHARED_PREF_LOGIN_PW, password);
                            editor.apply();
                            Intent intent = new Intent(NicknameSettingActivity.this, MainActivity.class);
                            startActivity(intent);
                            NicknameSettingActivity.this.finish();
                        } else if (response.code() == 400) {
                            nicknameAlreadyExistTv.setVisibility(View.VISIBLE);
                            Log.d("settingnickname", "exist");
                        } else {
                            Log.d("settingnickname", "" + response.code());
                            String jsonString = "";
                            try {
                                jsonString = response.errorBody().string();
                            } catch (IOException e) {

                            }
                            ServerError serverError = gson.fromJson(jsonString, ServerError.class);
                            Log.d("settingnickname", serverError.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Log.d("settingnickname", "fail");
                    }
                });
            }
        });
    }
}
