package sungshin.project.ourdiaryapplication.home.fragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import sungshin.project.ourdiaryapplication.R;


public class HomeGroupFragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<Diary> diaryList;
    DiaryAdapter adapter;
    Context mContext;
    Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home_all, container, false);
        recyclerView = v.findViewById(R.id.diaryBook);
        diaryList = new ArrayList<>();
        diaryList.add(new Diary("강슬기", "3학년 10반 반창회", "2020-05-05","빨간날이라 오랜만에 ○○고 3학년 10반 친구들이 뭉쳤다. 담임선생님도 와주셨다. 정말 다들 너무 반가웠다", "○○고 3학년 10반"));
        diaryList.add(new Diary("김혜인", "집들이", "2020-04-30","집 정리가 어느정도 되어서 이사한지 한 달만에 대학교 동기들을 불러 집들이를 했다. 집 앞 마트에서 다 같이 장 보고 마라샹궈도 해먹고 바지락 술찜도 해먹고 술도 엄청나게 많이 마셨다. 그리고 마피아 게임도 하고 라이어 게임도 했다. 새벽 5시 넘어서까지 수다를 떨다가 동이 트는 것을 보고 겨우 잠들었다. 정말 즐거운 하루였다.", "컴공 16학번 동기들"));

        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
        adapter = new DiaryAdapter(diaryList, activity);
        recyclerView.setAdapter(adapter);

        recyclerView.addItemDecoration(new MyItemDecoration());
        return v;
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
        if (context instanceof Activity)
            activity = (Activity) context;
    }

}
