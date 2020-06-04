package sungshin.project.ourdiaryapplication.mypage;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.locks.Lock;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sungshin.project.ourdiaryapplication.DocwriteActivity;
import sungshin.project.ourdiaryapplication.Login.LoginActivity;
import sungshin.project.ourdiaryapplication.Network.ReqUserUpdateMe;
import sungshin.project.ourdiaryapplication.Network.RetrofitManager;
import sungshin.project.ourdiaryapplication.Network.ServerApi;
import sungshin.project.ourdiaryapplication.Network.ServerError;
import sungshin.project.ourdiaryapplication.Network.User;
import sungshin.project.ourdiaryapplication.R;
import sungshin.project.ourdiaryapplication.main.MainActivity;


public class MyPageFragment extends Fragment {
    Activity activity;
    private Context mContext;
    Button toPasswordSetting;
    Button changeNicknameBtn;
    ImageView writeButton;
    TextView myEmailTv;
    TextView myNickTv;
    Button logoutBtn;
    private ServerApi serverApi;
    private Gson gson = new Gson();
    String name;
    String nickname;
    static final String SHARED_PREF_EMAIL = "EMAIL";
    static final String SHARED_PREF_LOGIN_PW = "PASSWORD";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_my_page, container, false);
        serverApi = RetrofitManager.getInstance().getServerApi(activity);
        toPasswordSetting = v.findViewById(R.id.btn_mypage_app_lock);
        changeNicknameBtn = v.findViewById(R.id.btn_mypage_change_nick);
        myEmailTv = v.findViewById(R.id.mypage_real_tv);
        myNickTv = v.findViewById(R.id.mypage_nick_tv);
        logoutBtn = v.findViewById(R.id.btn_mypage_logout);
        writeButton = v.findViewById(R.id.btn_mypage_newpost);
        serverApi.getUserMe().enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d("response", "onResponse안에는 들어옴");
                if(response.isSuccessful()) {
                    name = response.body().getName();
                    myEmailTv.setText(name);
                    nickname = response.body().getNick();
                    myNickTv.setText(nickname);
                }
                else {
                    Log.d("response", "" + response.code());
                    String jsonString = "";
                    try {
                        jsonString = response.errorBody().string();
                    }
                    catch (IOException e) {

                    }
                    ServerError serverError = gson.fromJson(jsonString, ServerError.class);
                    Log.d("myinfoerror", serverError.getMessage());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("myinfoerror", t.getMessage());
            }
        });
        writeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, DocwriteActivity.class);
                startActivity(intent);
            }
        });
        toPasswordSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, LockSettingActivity.class);
                startActivity(i);
            }
        });
        changeNicknameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText nicknameEdit = new EditText(v.getContext());
                nicknameEdit.setBackground(getResources().getDrawable(R.drawable.black_square));
                final AlertDialog.Builder nicknameEditDialog = new AlertDialog.Builder(v.getContext());

                nicknameEditDialog.setTitle("닉네임 변경")
                        .setMessage("변경할 닉네임을 입력하세요").setView(nicknameEdit)
                        .setPositiveButton("변경", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String nickname = nicknameEdit.getText().toString();
                                if (nickname.equals("")) {
                                    Toast.makeText(activity, "닉네임을 입력해주세요", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    ReqUserUpdateMe req = new ReqUserUpdateMe();
                                    req.setName(name);
                                    req.setNick(nickname);
                                    serverApi.updateUserMe(req).enqueue(new Callback<User>() {
                                        @Override
                                        public void onResponse(Call<User> call, Response<User> response) {
                                            if(response.isSuccessful()) {
                                                Toast.makeText(activity, "닉네임 변경이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                                myNickTv.setText(response.body().getNick());

                                            }
                                            else if (response.code() == 400){
                                                Toast.makeText(activity, "이미 존재하는 닉네임입니다.", Toast.LENGTH_SHORT).show();
                                            }
                                            else {
                                                Toast.makeText(activity, "error " + response.code(), Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<User> call, Throwable t) {

                                        }
                                    });
                                }
                            }
                        }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog alertDialog = nicknameEditDialog.create();
                alertDialog.show();
            }
        });
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serverApi.signOut().enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Log.d("logout", "onResponse");
                        if(response.isSuccessful()) {
                            Log.d("logoutsuccess", "success");
                            SharedPreferences sharedPref = activity.getSharedPreferences("login", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString(SHARED_PREF_EMAIL, "-1");
                            editor.putString(SHARED_PREF_LOGIN_PW, "-1");
                            editor.apply();
                            activity.finish();
                            Intent intent = new Intent(activity, LoginActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Log.d("logouterror", "" + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.d("logouterror", t.getMessage());
                    }
                });
            }
        });
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
        if (context instanceof Activity)
            activity = (Activity) context;
    }
}
