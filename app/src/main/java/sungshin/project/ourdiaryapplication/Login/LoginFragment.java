package sungshin.project.ourdiaryapplication.Login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.auth.authorization.accesstoken.AccessToken;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.ApiErrorCode;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.exception.KakaoException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sungshin.project.ourdiaryapplication.Network.ReqUserSignIn;
import sungshin.project.ourdiaryapplication.Network.ReqUserSignUp;
import sungshin.project.ourdiaryapplication.Network.RetrofitManager;
import sungshin.project.ourdiaryapplication.Network.ServerApi;
import sungshin.project.ourdiaryapplication.Network.ServerError;
import sungshin.project.ourdiaryapplication.Network.User;
import sungshin.project.ourdiaryapplication.R;
import sungshin.project.ourdiaryapplication.main.MainActivity;

public class LoginFragment extends Fragment {
    private ServerApi serverApi;
    private Gson gson = new Gson();
    Button loginBtn;
    EditText idEditText;
    EditText pwEditText;
    TextView signUpTv;
    //    LinearLayout kakaoTalkSignInBtn;
    static final String SHARED_PREF_USER_NAME = "6000";
    public final String SHARED_PREF_EMAIL = "EMAIL";
    public final String SHARED_PREF_LOGIN_PW = "PASSWORD";
    public final String SHARED_PREF_LOGIN_TYPE = "TYPE";
    private SessionCallback sessionCallback;
    ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //        SharedPreferences sharedPre = getSharedPreferences("login", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPre.edit();
//        editor.putString(SHARED_PREF_EMAIL, "-1");
//        editor.putString(SHARED_PREF_LOGIN_PW, "-1");
//        editor.apply();
        serverApi = RetrofitManager.getInstance().getServerApi(getContext());
        SharedPreferences sharedPref = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        String loginEmail = sharedPref.getString(SHARED_PREF_EMAIL, "-1");
        String loginPassword = sharedPref.getString(SHARED_PREF_LOGIN_PW, "-1");
        if (!loginEmail.equals("-1") && !loginPassword.equals("-1")) {
            loading("자동 로그인 중입니다.");
            ReqUserSignIn req = new ReqUserSignIn();
            req.setId(loginEmail);
            req.setPw(loginPassword);
            req.setType("EMAIL");
            serverApi.signInUser(req).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        //로그인을 하면 메인화면으로 간다.
                        serverApi.getUserMe().enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                if (response.isSuccessful()) {
                                    if (response.body().getNick() == null) { //닉네임이 없는 사용자의 경우
//                                        SharedPreferences sharedPref = getSharedPreferences("login", Context.MODE_PRIVATE);
//                                        SharedPreferences.Editor editor = sharedPref.edit();
//                                        editor.putString(SHARED_PREF_EMAIL, loginEmail);
//                                        editor.putString(SHARED_PREF_LOGIN_PW, loginPassword);
//                                        editor.apply();
                                        Intent intent = new Intent(getActivity(), NicknameSettingActivity.class);
                                        Log.d("자동로그인", "92번째 줄");
                                        startActivity(intent);
                                        getActivity().finish();
                                    } else { //자동 로그인
                                        Intent intent = new Intent(getActivity(), MainActivity.class);
                                        Log.d("자동로그인", "96번째 줄");
                                        startActivity(intent);
                                        SharedPreferences sharedPref = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPref.edit();
                                        Log.d("자동로그인 이메일", loginEmail);
                                        Log.d("자동로그인 비밀번호", loginPassword);
                                        editor.putString(SHARED_PREF_EMAIL, loginEmail);
                                        editor.putString(SHARED_PREF_LOGIN_PW, loginPassword);
                                        editor.apply();
                                        getActivity().finish();
                                        loadingEnd();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<User> call, Throwable t) {

                            }
                        });
//                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);//이렇게 해야 로그인으로 감
//                        startActivity(intent);
//                        finish();
//                        loadingEnd();
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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        loginBtn = view.findViewById(R.id.loginBtn);
        idEditText = view.findViewById(R.id.idEditText);
        pwEditText = view.findViewById(R.id.pwEditText);
        signUpTv = view.findViewById(R.id.signUpTv);
//        kakaoTalkSignInBtn = view.findViewById(R.id.kakaoTalkSignInBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loading("로그인 중입니다.");
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
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        //response.isSuccessful() // 200~399는 성공(true) 400~599는 실패(false)
                        if (response.isSuccessful()) {
                            //로그인을 하면 메인화면으로 간다.
                            serverApi.getUserMe().enqueue(new Callback<User>() {
                                @Override
                                public void onResponse(Call<User> call, Response<User> response) {
                                    if (response.isSuccessful()) {
                                        if (response.body().getNick() == null) { //닉네임 설정이 안 된 사용자는 닉네임 설정창으로
//                                            SharedPreferences sharedPref = getSharedPreferences("login", Context.MODE_PRIVATE);
//                                            SharedPreferences.Editor editor = sharedPref.edit();
//                                            editor.putString(SHARED_PREF_EMAIL, id);
//                                            editor.putString(SHARED_PREF_LOGIN_PW, pw);
//                                            editor.apply();
                                            Intent intent = new Intent(getActivity(), NicknameSettingActivity.class);
                                            intent.putExtra("email", idEditText.getText().toString());
                                            intent.putExtra("password", pwEditText.getText().toString());
                                            Log.d("자동로그인", "176번째 줄");
                                            startActivity(intent);
                                            getActivity().finish();
                                            loadingEnd();
                                        } else {
                                            Intent intent = new Intent(getActivity(), MainActivity.class);//이렇게 해야 로그인으로 감
                                            Log.d("자동로그인", "181번째 줄");
                                            startActivity(intent);
                                            SharedPreferences sharedPref = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedPref.edit();
                                            editor.putString(SHARED_PREF_EMAIL, id);
                                            editor.putString(SHARED_PREF_LOGIN_PW, pw);
                                            editor.apply();
                                            getActivity().finish();
                                            loadingEnd();
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<User> call, Throwable t) {

                                }
                            });
                        } else {
                            Log.d("loginerror", "error code: " + response.code()); //401에러인지 500 에러인지 에러 번호가 뜸
                            if (response.code() == 400) {
                                Toast.makeText(getActivity(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                            }
                            try {
                                String jsonString = response.errorBody().string();
                                ServerError serverError = gson.fromJson(jsonString, ServerError.class);

                                switch (serverError.getError()) {
                                    case "INVALID_EMAIL":
                                        Toast.makeText(getActivity(), "이메일을 확인해주세요", Toast.LENGTH_SHORT).show();
                                        break;
                                    case "EXISTS_EMAIL":
                                        Toast.makeText(getActivity(), "이미 가입된 이메일입니다.", Toast.LENGTH_SHORT).show();
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
                Intent intent = new Intent(getActivity(), SignupActivity.class);//이렇게 해야 로그인으로 감
                Log.d("자동로그인", "235번째 줄");
                startActivity(intent);
                getActivity().finish();
            }
        });
//        kakaoTalkSignInBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(LoginActivity.this, NameAndNicknameSettingActivity.class);
//                startActivity(i);
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.ourdiary.site:8081/test-kakao.html"));
//                startActivity(intent);
//            }
//        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
            return;
        }
    }

    @Override
    public void onDestroy() {
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

                    if (result == ApiErrorCode.CLIENT_ERROR_CODE) {
                        Toast.makeText(getContext(), "네트워크 연결이 불안정합니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                        getActivity().finish();
                    } else {
                        Toast.makeText(getContext(), "로그인 도중 오류가 발생했습니다: " + errorResult.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onSessionClosed(ErrorResult errorResult) {
                    Toast.makeText(getContext(), "세션이 닫혔습니다. 다시 시도해 주세요: " + errorResult.getErrorMessage(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSuccess(MeV2Response result) {
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    Log.d("카카오 id:", " " + result.getId());
                    AccessToken accessToken = Session.getCurrentSession().getTokenInfo();
                    ReqUserSignIn reqUserSignIn = new ReqUserSignIn();
                    reqUserSignIn.setId(accessToken.getAccessToken());
                    reqUserSignIn.setType("KAKAO");
                    reqUserSignIn.setPw("1234");
                    serverApi.signInUser(reqUserSignIn).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                serverApi.getUserMe().enqueue(new Callback<User>() {
                                    @Override
                                    public void onResponse(Call<User> call, Response<User> response) {
                                        if (response.isSuccessful()) {
                                            if(response.body().getNick() == null) {
                                                Intent i = new Intent(getContext(), NicknameSettingActivity.class);
                                                i.putExtra("name", result.getNickname());
                                                Log.d("자동로그인", "307번째 줄");
                                                startActivity(i);
                                                getActivity().finish();
                                            }
                                            else {
                                                Intent i = new Intent(getContext(), MainActivity.class);
//                                                i.putExtra("name", result.getNickname());
                                                SharedPreferences sharedPref = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
                                                SharedPreferences.Editor editor = sharedPref.edit();
                                                editor.putString(SHARED_PREF_LOGIN_TYPE, "KAKAO");
                                                editor.apply();
                                                Log.d("자동로그인", "313번째 줄");
                                                startActivity(i);
                                                getActivity().finish();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<User> call, Throwable t) {

                                    }
                                });
                            }

                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {

                        }
                    });
//                    intent.putExtra("name", result.getNickname());
//                    intent.putExtra("profile", result.getProfileImagePath());
//                    startActivity(intent);
//                    getActivity().finish();
                }
            });
        }

        @Override
        public void onSessionOpenFailed(KakaoException e) {
            Toast.makeText(getContext(), "로그인 도중 오류가 발생했습니다. 인터넷 연결을 확인해주세요: " + e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void loading(String message) {
        //로딩
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        progressDialog = new ProgressDialog(getActivity());
                        progressDialog.setIndeterminate(true);
                        progressDialog.setMessage(message);
                        progressDialog.show();
                    }
                }, 0);
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