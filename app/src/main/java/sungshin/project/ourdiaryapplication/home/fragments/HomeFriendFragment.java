package sungshin.project.ourdiaryapplication.home.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import java.util.ArrayList;

import sungshin.project.ourdiaryapplication.R;
import sungshin.project.ourdiaryapplication.home.FilterActivity;

public class HomeFriendFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<Diary> diaryList;
    DiaryAdapter adapter;
    Context mContext;
    Activity activity;
    ImageButton filterBtn;
    ArrayAdapter<String> yearAndMonthAdapter;
    String[] yearAndMonth;
    Spinner spinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home_all, container, false);
        recyclerView = v.findViewById(R.id.diaryBook);
        filterBtn = v.findViewById(R.id.filterBtn);
        spinner = v.findViewById(R.id.yearAndMonthSpinner);
        yearAndMonth = new String[]{"2020년 3월", "2020년 4월", "2020년 5월"};
        yearAndMonthAdapter = new ArrayAdapter<>(activity, android.R.layout.simple_spinner_dropdown_item, yearAndMonth);
        spinner.setAdapter(yearAndMonthAdapter);
        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), FilterActivity.class);
                startActivity(intent);
            }
        });
        diaryList = new ArrayList<>();
        diaryList.add(new Diary("김효은", "홍대 간 날", "2020-05-07","오늘은 오랜만에 홍대를 갔다. 패션피플들이 정말 많았다. 맛있는 것도 먹고 정말 재미있게 놀았다."));
        diaryList.add(new Diary("박수영", "효은이와 홍대 간 날", "2020-05-07","어쩌구 저쩌구"));
        diaryList.add(new Diary("김효은", "버스 놓친 날", "2020-05-07","집 가다가 버스를 놓쳤는데 그게 막차였다. 그래서 집까지 걸어갔다"));

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
