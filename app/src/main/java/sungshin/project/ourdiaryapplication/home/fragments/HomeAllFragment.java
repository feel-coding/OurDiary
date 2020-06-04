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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sungshin.project.ourdiaryapplication.Network.RetrofitManager;
import sungshin.project.ourdiaryapplication.Network.ServerApi;
import sungshin.project.ourdiaryapplication.R;
import sungshin.project.ourdiaryapplication.home.FilterActivity;


public class HomeAllFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<Diary> diaryList;
    DiaryAdapter adapter;
    ImageButton filterBtn;
    ArrayAdapter<String> yearAndMonthAdapter;
    String[] yearAndMonth;
    Spinner spinner;
    Context mContext;
    Activity activity;
    ServerApi serverApi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home_all, container, false);
        serverApi = RetrofitManager.getInstance().getServerApi(activity);
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
        serverApi.getDiaries("ALL").enqueue(new Callback<List<sungshin.project.ourdiaryapplication.Network.Diary>>() {
            @Override
            public void onResponse(Call<List<sungshin.project.ourdiaryapplication.Network.Diary>> call, Response<List<sungshin.project.ourdiaryapplication.Network.Diary>> response) {
                if (response.isSuccessful()) {
                    for (int i = 0; i < response.body().size(); i++) {
                        diaryList.add(new Diary(response.body().get(i).getUser().getName(), response.body().get(i).getTitle(), "", response.body().get(i).getContent().getText()));
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<sungshin.project.ourdiaryapplication.Network.Diary>> call, Throwable t) {

            }
        });
//        diaryList.add(new Diary("김효은", "어버이날", "2020-05-08","어버이날이라 카네이션과 케이크를 준비했다. 부모님이 좋아하셨다."));
//        diaryList.add(new Diary("김효은", "홍대 간 날", "2020-05-07","오늘은 오랜만에 홍대를 갔다. 패션피플들이 정말 많았다. 맛있는 것도 먹고 정말 재미있게 놀았다."));
//        diaryList.add(new Diary("박수영", "효은이와 홍대 간 날", "2020-05-07","어쩌구 저쩌구"));
//        diaryList.add(new Diary("김효은", "버스 놓친 날", "2020-05-07","집 가다가 버스를 놓쳤는데 그게 막차였다. 그래서 집까지 걸어갔다"));diaryList.add(new Diary("강슬기", "3학년 10반 반창회", "2020-05-05","빨간날이라 오랜만에 ○○고 3학년 10반 친구들이 뭉쳤다. 담임선생님도 와주셨다. 정말 다들 너무 반가웠다", "○○고 3학년 10반"));
//        diaryList.add(new Diary("김혜인", "집들이", "2020-04-30","집 정리가 어느정도 되어서 이사한지 한 달만에 대학교 동기들을 불러 집들이를 했다. 집 앞 마트에서 다 같이 장 보고 마라샹궈도 해먹고 바지락 술찜도 해먹고 술도 엄청나게 많이 마셨다. 그리고 마피아 게임도 하고 라이어 게임도 했다. 새벽 5시 넘어서까지 수다를 떨다가 동이 트는 것을 보고 겨우 잠들었다. 정말 즐거운 하루였다.", "컴공 16학번 동기들"));


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
