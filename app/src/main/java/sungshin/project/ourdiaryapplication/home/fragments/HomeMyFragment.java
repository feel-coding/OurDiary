package sungshin.project.ourdiaryapplication.home.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import sungshin.project.ourdiaryapplication.R;


public class HomeMyFragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<Diary> diaryList;
    DiaryAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home_all, container, false);
        recyclerView = v.findViewById(R.id.diaryBook);
        diaryList = new ArrayList<>();
        diaryList.add(new Diary("김효은", "어버이날", "2020-05-08","어버이날이라 카네이션과 케이크를 준비했다. 부모님이 좋아하셨다."));
        diaryList.add(new Diary("김효은", "홍대 간 날", "2020-05-07","오늘은 오랜만에 홍대를 갔다. 패션피플들이 정말 많았다. 맛있는 것도 먹고 정말 재미있게 놀았다."));
        diaryList.add(new Diary("김효은", "버스 놓친 날", "2020-05-07","집 가다가 버스를 놓쳤는데 그게 막차였다. 그래서 집까지 걸어갔다"));

        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
        adapter = new DiaryAdapter(diaryList);
        recyclerView.setAdapter(adapter);

        recyclerView.addItemDecoration(new MyItemDecoration());
        return v;
    }

}
