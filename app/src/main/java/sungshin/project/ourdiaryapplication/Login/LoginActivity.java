package sungshin.project.ourdiaryapplication.Login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;
import sungshin.project.ourdiaryapplication.Network.ReqUserSignIn;
import sungshin.project.ourdiaryapplication.Network.RetrofitManager;
import sungshin.project.ourdiaryapplication.Network.ServerApi;
import sungshin.project.ourdiaryapplication.Network.ServerError;
import sungshin.project.ourdiaryapplication.R;
import sungshin.project.ourdiaryapplication.friendlist.FrdSearchActivity;
import sungshin.project.ourdiaryapplication.main.MainActivity;

import com.kakao.auth.ApprovalType;
import com.kakao.auth.AuthType;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.ISessionConfig;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.ApiErrorCode;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.callback.UnLinkResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.util.exception.KakaoException;




public class LoginActivity extends AppCompatActivity {

    private ServerApi serverApi;
    private Gson gson = new Gson();
    Button loginBtn;
    EditText idEditText;
    EditText pwEditText;
    TextView signUpTv;
    LinearLayout kakaoTalkSignInBtn;
    static final String SHARED_PREF_USER_NAME = "6000";
    public final String SHARED_PREF_EMAIL = "EMAIL";
    public final String SHARED_PREF_LOGIN_PW = "PASSWORD";
    private SessionCallback sessionCallback;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        serverApi = RetrofitManager.getInstance().getServerApi(this);
        SharedPreferences sharedPref = getSharedPreferences("login", Context.MODE_PRIVATE);
        String loginEmail = sharedPref.getString(SHARED_PREF_EMAIL, "-1");
        String loginPassword = sharedPref.getString(SHARED_PREF_LOGIN_PW, "-1");
        if(!loginEmail.equals("-1") && !loginPassword.equals("-1")) {
            loading();
            ReqUserSignIn req = new ReqUserSignIn();
            req.setId(loginEmail);
            req.setPw(loginPassword);
            req.setType("EMAIL");
            serverApi.signInUser(req).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        //로그인을 하면 메인화면으로 간다.
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);//이렇게 해야 로그인으로 감
                        startActivity(intent);
                        finish();
                        loadingEnd();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });
        }
        sessionCallback = new SessionCallback();
        Session.getCurrentSession().addCallback(sessionCallback);
        Session.getCurrentSession().checkAndImplicitOpen();

        loginBtn = findViewById(R.id.loginBtn);
        idEditText = findViewById(R.id.idEditText);
        pwEditText = findViewById(R.id.pwEditText);
        signUpTv = findViewById(R.id.signUpTv);
        kakaoTalkSignInBtn = findViewById(R.id.kakaoTalkSignInBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("lgin", "clicked");
                String id = idEditText.getText().toString();
                String pw = pwEditText.getText().toString();

                //ID,PW 정규식체크하는 부분->팝업띄우기
                ReqUserSignIn req = new ReqUserSignIn();
                req.setType("EMAIL");
                req.setId(id);
                req.setPw(pw);
                serverApi.signInUser(req).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call,Response<Void> response) {
                        //response.isSuccessful() // 200~399는 성공(true) 400~599는 실패(false)
                        if (response.isSuccessful()) {
                            //로그인을 하면 메인화면으로 간다.
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);//이렇게 해야 로그인으로 감
                            startActivity(intent);
                            SharedPreferences sharedPref = getSharedPreferences("login", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString(SHARED_PREF_EMAIL, id);
                            editor.putString(SHARED_PREF_LOGIN_PW, pw);
                            editor.apply();
                            LoginActivity.this.finish();
                        }
                        else {
                            Log.d("loginerror", "error code: " + response.code()); //401에러인지 500 에러인지 에러 번호가 뜸
                            if(response.code() == 400) {
                                Toast.makeText(LoginActivity.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                            }
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
                                        //Toast.makeText(LoginActivity.this, serverError.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception ignored) {

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.d("loginerror", t.getMessage());
                    }
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
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.ourdiary.site:8081/test-kakao.html"));
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
            return;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(sessionCallback);
    }

    private class SessionCallback implements ISessionCallback {
        @Override
        public void onSessionOpened() {
            UserManagement.getInstance().me(new MeV2ResponseCallback() {
                @Override
                public void onFailure(ErrorResult errorResult) {
                    int result = errorResult.getErrorCode();

                    if(result == ApiErrorCode.CLIENT_ERROR_CODE) {
                        Toast.makeText(getApplicationContext(), "네트워크 연결이 불안정합니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(),"로그인 도중 오류가 발생했습니다: "+errorResult.getErrorMessage(),Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onSessionClosed(ErrorResult errorResult) {
                    Toast.makeText(getApplicationContext(),"세션이 닫혔습니다. 다시 시도해 주세요: "+errorResult.getErrorMessage(),Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSuccess(MeV2Response result) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("name", result.getNickname());
                    intent.putExtra("profile", result.getProfileImagePath());
                    startActivity(intent);
                    finish();
                }
            });
        }

        @Override
        public void onSessionOpenFailed(KakaoException e) {
            Toast.makeText(getApplicationContext(), "로그인 도중 오류가 발생했습니다. 인터넷 연결을 확인해주세요: "+e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void loading() {
        //로딩
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        progressDialog = new ProgressDialog(LoginActivity.this);
                        progressDialog.setIndeterminate(true);
                        progressDialog.setMessage("자동 로그인 중입니다.");
                        progressDialog.show();
                    }
                }, 100);
    }

    public void loadingEnd() {
        new android.os.Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                    }
                }, 0);
    }
}
