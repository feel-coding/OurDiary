package sungshin.project.ourdiaryapplication.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import sungshin.project.ourdiaryapplication.main.MainActivity;

public class SignupActivity extends AppCompatActivity {

    private ServerApi serverApi = RetrofitManager.getInstance().getServerApi();
    private Gson gson = new Gson();
    Button emailCheckBtn;
    TextView alreadyExistTv;
    EditText emailEdit;
    //String email = "";
    Button nextBtn;
    EditText nameEdit, pwEdit, pwConfirmEdit;

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
                Intent i = new Intent(SignupActivity.this, AuthenticationActivity.class);
                startActivity(i);
            }
        });

        emailCheckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String변수명 선언
                String name = nameEdit.getText().toString();
                String email = emailEdit.getText().toString();
                String pw = pwEdit.getText().toString();
                String pwConfirm = pwConfirmEdit.getText().toString();

                //여기로 옮김
                //정규식 바꾸세요(구글링 이메일 정규식 찾아보기)
                if(!email.contains("@") || !email.contains(".")) {
                    alreadyExistTv.setText("잘못된 형식의 이메일입니다");
                    alreadyExistTv.setVisibility(View.VISIBLE);
                    return;
                }

                if(!pw.equals(pwConfirm)){
                    //이렇게 해주세요(위에도)
                    Toast.makeText(getApplicationContext(),"두 비밀번호가 다릅니다.",Toast.LENGTH_LONG).show();
                    return;
                }

                ReqSignInUser req = new ReqSignInUser();
                req.setType("EMAIL");//여기 채워주세요
                req.setId(email);
                req.setPw(pw);
                req.setName(name);

                serverApi.signInUser(req).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        //response.isSuccessful() // 200~399는 성공(true) 400~599는 실패(false)
                        if (response.isSuccessful()) {
                            alreadyExistTv.setVisibility(View.GONE);

                            Intent intent = new Intent(SignupActivity.this, AuthenticationActivity.class);//이렇게 해야 로그인으로 감
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
                    public void onFailure(Call<Void> call, Throwable t) {}
                });
            }
        });
    }
    
}
