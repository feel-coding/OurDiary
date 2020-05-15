package sungshin.project.ourdiaryapplication.Login;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class LoginActivity extends AppCompatActivity {

    private ServerApi serverApi = RetrofitManager.getInstance().getServerApi();
    private Gson gson = new Gson();
    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReqSignInUser req = new ReqSignInUser();
                req.setType("");
                req.setId("");
                serverApi.signInUser(req).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        //response.isSuccessful() // 200~399는 성공(true) 400~599는 실패(false)
                        if (response.isSuccessful()) {
                            //body부분 없음
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
    }
}
