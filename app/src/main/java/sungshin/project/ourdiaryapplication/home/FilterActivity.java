package sungshin.project.ourdiaryapplication.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sungshin.project.ourdiaryapplication.Network.Friend;
import sungshin.project.ourdiaryapplication.Network.FriendReq;
import sungshin.project.ourdiaryapplication.Network.ReqUserSignIn;
import sungshin.project.ourdiaryapplication.Network.RetrofitManager;
import sungshin.project.ourdiaryapplication.Network.ServerApi;
import sungshin.project.ourdiaryapplication.Network.User;
import sungshin.project.ourdiaryapplication.R;

public class FilterActivity extends AppCompatActivity {

    ListView listView;
    Toolbar toolbar;
    ArrayAdapter<String> adapter;
    ArrayList<String> friendsList = new ArrayList<>();
    ArrayList<Integer> seqList = new ArrayList<>();
    int count = 0;
    Button filterApplyBtn;
    ServerApi serverApi;
    ArrayList<Integer> selectedPosition = new ArrayList<>();
    public final String SHARED_PREF_EMAIL = "EMAIL";
    public final String SHARED_PREF_LOGIN_PW = "PASSWORD";
    ArrayList<String> selectedNickname = new ArrayList<>();
    ArrayList<String> nicknameList = new ArrayList<>();
    String myName;
    String myNickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        serverApi = RetrofitManager.getInstance().getServerApi(this);
        toolbar = findViewById(R.id.filter_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
        actionBar.setHomeAsUpIndicator(R.drawable.close_black);

        ContextThemeWrapper themeWrapper = new ContextThemeWrapper(this, R.style.AdapterTheme);
        adapter = new ArrayAdapter<>(themeWrapper, android.R.layout.simple_list_item_multiple_choice, friendsList);
        filterApplyBtn = findViewById(R.id.apply_filter_btn);listView = findViewById(R.id.friendSelectLv);
        listView.setAdapter(adapter);
        serverApi.getFriendList().enqueue(new Callback<List<Friend>>() {
            @Override
            public void onResponse(Call<List<Friend>> call, Response<List<Friend>> response) {
                if (response.isSuccessful()) {
                    Log.d("친구 목록 가져오기", "성공");
                    for (int i = 0; i < response.body().size(); i++) {
                        Log.d("친구아름", response.body().get(i).getName());
                        friendsList.add(response.body().get(i).getName() + " (" + response.body().get(i).getNick() + ")");
                        nicknameList.add(response.body().get(i).getNick());
                    }
                    serverApi.getUserMe().enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if (response.isSuccessful()) {
                                Log.d("myname", response.body().getName());
                                myName = response.body().getName();
                                myNickname = response.body().getNick();
                                friendsList.add(myName + " (" + myNickname + ")");
                                nicknameList.add(myNickname);
                                adapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {

                        }
                    });
                    adapter.notifyDataSetChanged();
                }
                else {
                    if (response.code() == 401) {
                        SharedPreferences sharedPref = getSharedPreferences("login", Context.MODE_PRIVATE);
                        String loginEmail = sharedPref.getString(SHARED_PREF_EMAIL, "-1");
                        String loginPassword = sharedPref.getString(SHARED_PREF_LOGIN_PW, "-1");
                        ReqUserSignIn req = new ReqUserSignIn();
                        req.setType("EMAIL");
                        req.setId(loginEmail);
                        req.setPw(loginPassword);
                        serverApi.signInUser(req).enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                serverApi.getFriendList().enqueue(new Callback<List<Friend>>() {
                                    @Override
                                    public void onResponse(Call<List<Friend>> call, Response<List<Friend>> response) {
                                        if (response.isSuccessful()) {
                                            Log.d("친구 목록 가져오기", "성공");
                                            for (int i = 0; i < response.body().size(); i++) {
                                                Log.d("친구아름", response.body().get(i).getName());
                                                friendsList.add(response.body().get(i).getName() + " (" + response.body().get(i).getNick() + ")");
                                            }
                                            adapter.notifyDataSetChanged();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<List<Friend>> call, Throwable t) {

                                    }
                                });
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Friend>> call, Throwable t) {

            }
        });

        adapter.notifyDataSetChanged();
//        friendsList.add("김효은");
//        friendsList.add("박소영");
//        friendsList.add("이주연");
//        friendsList.add("윤여경");
//        friendsList.add("박설");
        adapter.notifyDataSetChanged();
        filterApplyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                ArrayList<Integer> selectedSeq = new ArrayList<>();
                for(int index : selectedPosition) {
                    selectedSeq.add(seqList.get(index));
                }
                intent.putStringArrayListExtra("selectedNickname", selectedNickname);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("selected", "" + i + "번째");
                /*if(selectedPosition.contains(i)) {
                    selectedPosition.remove((Integer)i);
                }
                else {
                    selectedPosition.add(i);
                }*/
                if(selectedNickname.contains(nicknameList.get(i))) {
                    selectedNickname.remove(nicknameList.get(i));
                }
                else {
                    selectedNickname.add(nicknameList.get(i));
                }
                for (String n : selectedNickname) {
                    Log.d("selected nickname", n);
                }
                if(selectedNickname.size() > 0) {
                    filterApplyBtn.setVisibility(View.VISIBLE);
                }
                else {
                    filterApplyBtn.setVisibility(View.GONE);
                }
                Log.d("ctct", selectedPosition.size() + "개");
            }
        });
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {
                if(b)
                    count++;
                else
                    count--;
                if(count > 0) {
                    filterApplyBtn.setVisibility(View.VISIBLE);
                    Log.d("ctct", "0보다 큼");
                }
                else {
                    filterApplyBtn.setVisibility(View.GONE);
                    Log.d("ctct", "0임");
                }
            }

            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                MenuInflater inflater = getMenuInflater();
                inflater.inflate(R.menu.contextual, menu);
                toolbar.setVisibility(View.GONE);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {
                toolbar.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
