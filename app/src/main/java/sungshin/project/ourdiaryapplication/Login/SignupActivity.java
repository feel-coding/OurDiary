package sungshin.project.ourdiaryapplication.Login;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
import com.kakao.auth.KakaoSDK;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sungshin.project.ourdiaryapplication.Network.ReqUserSignUp;
import sungshin.project.ourdiaryapplication.Network.RetrofitManager;
import sungshin.project.ourdiaryapplication.Network.ServerApi;
import sungshin.project.ourdiaryapplication.Network.ServerError;
import sungshin.project.ourdiaryapplication.R;

public class SignupActivity extends AppCompatActivity {

    private ServerApi serverApi = RetrofitManager.getInstance().getServerApi(this);
    private Gson gson = new Gson();
    Button emailCheckBtn;
    TextView alreadyExistTv;
    EditText emailEdit;
    //String email = "";
    Button nextBtn;
    EditText nameEdit, pwEdit, pwConfirmEdit;
    static final String SHARED_PREF_USER_NAME = "6000";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //뒤로가기 버튼 만들기
        Toolbar toolbar = findViewById(R.id.lock_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
        actionBar.setHomeAsUpIndicator(R.drawable.arrow_back);
        nextBtn = findViewById(R.id.nextBtn);
        emailCheckBtn = findViewById(R.id.emailCheckBtn);
        alreadyExistTv = findViewById(R.id.alreadyExistTv);
        emailEdit = findViewById(R.id.emailEdit);
        //이거 뭐에용?
        //email = emailEdit.getText().toString();
        nameEdit = findViewById(R.id.nameEdit);
        pwEdit = findViewById(R.id.pwEdit);
        pwConfirmEdit = findViewById(R.id.pwConfirmEdit);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameEdit.getText().toString();
                String email = emailEdit.getText().toString();
                if (name.equals("")) {
                    Toast.makeText(SignupActivity.this, "이름을 입력해주세요", Toast.LENGTH_SHORT).show();
                    nameEdit.setFocusable(View.FOCUSABLE);
                }
                else {
                    Intent i = new Intent(SignupActivity.this, AuthenticationActivity.class);
                    startActivity(i);
                }
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String변수명 선언
                String name = nameEdit.getText().toString();
                String email = emailEdit.getText().toString();
                String pw = pwEdit.getText().toString();
                String pwConfirm = pwConfirmEdit.getText().toString();

                //여기로 옮김
                //정규식 바꾸세요(구글링 이메일 정규식 찾아보기)
                String regExp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
                if(!email.matches(regExp)) {
                    alreadyExistTv.setText("잘못된 형식의 이메일입니다");
                    alreadyExistTv.setVisibility(View.VISIBLE);
                    return;
                }
                else {
                    alreadyExistTv.setVisibility(View.GONE);
                }

                if(!pw.equals(pwConfirm)){
                    //이렇게 해주세요(위에도)
                    Toast.makeText(getApplicationContext(),"두 비밀번호가 다릅니다.",Toast.LENGTH_LONG).show();
                    return;
                }

                ReqUserSignUp req = new ReqUserSignUp();
                req.setType("EMAIL");//여기 채워주세요
                req.setId(email);
                req.setPw(pw);
                req.setName(name);

                serverApi.signUpUser(req).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        //response.isSuccessful() // 200~399는 성공(true) 400~599는 실패(false)
                        if (response.isSuccessful()) {
                            alreadyExistTv.setVisibility(View.GONE);

                            Intent intent = new Intent(SignupActivity.this, AuthenticationActivity.class);
                            SharedPreferences sharedPref = getSharedPreferences("user", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString(SHARED_PREF_USER_NAME, name);
                            editor.apply();
                            startActivity(intent);
                            SignupActivity.this.finish();
                        }
                        else {
                            Log.d("loginerror", "error code: " + response.code()); //401에러인지 500 에러인지 에러 번호가 뜸
                            try {
                                String jsonString = response.errorBody().string();
                                ServerError serverError = gson.fromJson(jsonString, ServerError.class);

                                switch (serverError.getError()) {
                                    case "INVALID_EMAIL":
                                        Toast.makeText(SignupActivity.this, "이메일을 확인해주세요", Toast.LENGTH_SHORT).show();
                                        break;
                                    case "EXISTS_EMAIL":
                                        alreadyExistTv.setText(R.string.emailAlreadyExist);
                                        alreadyExistTv.setVisibility(View.VISIBLE);
                                        break;
                                    default:
                                        Toast.makeText(SignupActivity.this, serverError.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception ignored) {

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.d("signuperror", "failed");
                    }
                });
            }
        });
    }

}
