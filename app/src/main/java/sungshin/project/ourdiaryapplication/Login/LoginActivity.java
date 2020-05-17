package sungshin.project.ourdiaryapplication.Login;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sungshin.project.ourdiaryapplication.Network.ReqSignInUser;
import sungshin.project.ourdiaryapplication.Network.RetrofitManager;
import sungshin.project.ourdiaryapplication.Network.ServerApi;
import sungshin.project.ourdiaryapplication.Network.ServerError;
import sungshin.project.ourdiaryapplication.R;
import sungshin.project.ourdiaryapplication.SplashActivity;
import sungshin.project.ourdiaryapplication.main.MainActivity;

public class LoginActivity extends AppCompatActivity {

    private ServerApi serverApi = RetrofitManager.getInstance().getServerApi();
    private Gson gson = new Gson();
    Button loginBtn;
    EditText idEditText;
    EditText pwEditText;
    TextView signUpTv;
    LinearLayout kakaoTalkSignInBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //변수선언함(ID,PW)
        loginBtn = findViewById(R.id.loginBtn);
        idEditText = findViewById(R.id.idEditText);
        pwEditText = findViewById(R.id.pwEditText);
        signUpTv = findViewById(R.id.signUpTv);
        kakaoTalkSignInBtn = findViewById(R.id.kakaoTalkSignInBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = idEditText.getText().toString();
                String pw = pwEditText.getText().toString();

                //ID,PW 정규식체크하는 부분->팝업띄우기
                ReqSignInUser req = new ReqSignInUser();
                req.setType("EMAIL");
                req.setId(id);
                req.setPw(pw);
                serverApi.signInUser(req).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        //response.isSuccessful() // 200~399는 성공(true) 400~599는 실패(false)
                        if (response.isSuccessful()) {
                            //로그인을 하면 메인화면으로 간다.
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);//이렇게 해야 로그인으로 감
                            startActivity(intent);
                            LoginActivity.this.finish();
                        }
                        else {
                            Log.d("loginerror", "error code: " + response.code()); //401에러인지 500 에러인지 에러 번호가 뜸
                            try {
                                String jsonString = response.errorBody().string();
                                ServerError serverError = gson.fromJson(jsonString, ServerError.class);

                                switch (serverError.getError()) {
                                    case "INVALID_EMAIL":
                                        Toast.makeText(LoginActivity.this, "이메일을 확인해주세요", Toast.LENGTH_SHORT).show();
                                        break;
                                    case "EXISTS_EMAIL":
                                        Toast.makeText(LoginActivity.this, "이미 가입된 이메일입니다.", Toast.LENGTH_SHORT).show();
                                        break;
                                    default:
                                        Toast.makeText(LoginActivity.this, serverError.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception ignored) {

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {}
                });
            }
        });

        signUpTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);//이렇게 해야 로그인으로 감
                startActivity(intent);
                LoginActivity.this.finish();
            }
        });
        kakaoTalkSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, NameAndNicknameSettingActivity.class);
                startActivity(i);
            }
        });
    }
}
