package sungshin.project.ourdiaryapplication.mypage;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.concurrent.locks.Lock;

import sungshin.project.ourdiaryapplication.R;
import sungshin.project.ourdiaryapplication.main.MainActivity;


public class MyPageFragment extends Fragment {
    Activity activity;
    private Context mContext;
    Button toPasswordSetting;
    Button changeNicknameBtn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_my_page, container, false);
        toPasswordSetting = v.findViewById(R.id.btn_mypage_app_lock);
        changeNicknameBtn = v.findViewById(R.id.btn_mypage_change_nick);
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
