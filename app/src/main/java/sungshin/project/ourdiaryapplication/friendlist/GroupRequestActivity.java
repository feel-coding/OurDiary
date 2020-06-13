package sungshin.project.ourdiaryapplication.friendlist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sungshin.project.ourdiaryapplication.Network.EachUser;
import sungshin.project.ourdiaryapplication.Network.Group;
import sungshin.project.ourdiaryapplication.Network.ReqUserSignIn;
import sungshin.project.ourdiaryapplication.Network.RetrofitManager;
import sungshin.project.ourdiaryapplication.Network.ServerApi;
import sungshin.project.ourdiaryapplication.Network.ServerError;
import sungshin.project.ourdiaryapplication.R;
import sungshin.project.ourdiaryapplication.friendlist.adapter.MyAdapter;

public class GroupRequestActivity extends AppCompatActivity {

    EditText groupSearchEdit;
    private ServerApi serverApi;
    ArrayList<EachUser> friendsList = new ArrayList<>();
    MyAdapter myAdapter;
    private Gson gson = new Gson();
    ListView groupFriendListView;
    final String SHARED_PREF_EMAIL = "EMAIL";
    final String SHARED_PREF_LOGIN_PW = "PASSWORD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_request);
        serverApi = RetrofitManager.getInstance().getServerApi(this);
        groupFriendListView = findViewById(R.id.groupFriendList);
        groupSearchEdit = findViewById(R.id.friend_search_edit);
//        myAdapter = new MyAdapter(this,friendsList); 다른 어댑터로 해야 함. +버튼 눌렀을 때 친구 신청이 요청되는 것이 아니라 친구 정보가 리스트 들어가야 함. 그리고 나중에 그 리스트에 담겨있는
//        친구들을 가지고 그룹 신청하기 버튼을 눌러야 함
        groupFriendListView.setAdapter(myAdapter);
        groupSearchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = groupSearchEdit.getText().toString();
                search(text);
            }
        });
    }

    //검색 수행
    public void search(String text) {

        //입력 없을경우 데이터 모두 보여주기
        if(text.length()==0) {
            //frdItem.addAll(arrayList);
        }
        else {

            //서버에서 데이터 가져오기
            serverApi.getAllUsers(1, 15, text).enqueue(new Callback<List<EachUser>>() {
                @Override
                public void onResponse(Call<List<EachUser>> call, Response<List<EachUser>> response) {
                    if (response.isSuccessful()) {
                        //입력시 리스트 지우기
                        friendsList.clear();
                        Log.d("정보", "search clear : " + text);


                        Log.d("정보", "GetAllUsers success");
                        //todo:print response body in listview
                        List<EachUser> resultList = response.body();
                        Log.d("정보",  Integer.toString(resultList.size()));
                        for (int i = 0; i < resultList.size(); i++) {
                            friendsList.add(resultList.get(i));
                            Log.d("정보", Integer.toString(i) + resultList.get(i).getName());
                        }
                        myAdapter.notifyDataSetChanged();


                    } else {
                        Log.d("정보", "GetAllUsers error code" + response.code());
                        try {
                            String jsonString = response.errorBody().string();
                            Log.d("jsonString", jsonString);
                            ServerError serverError = gson.fromJson(jsonString, ServerError.class);

                            switch (serverError.getError()) {
                                case "INVALID_PAGE":
                                    Toast.makeText(GroupRequestActivity.this, "Invalid page", Toast.LENGTH_SHORT).show();
                                    break;
                                case "INVALID_PAGE_SIZE":
                                    Toast.makeText(GroupRequestActivity.this, "Invalid page size", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    //Toast.makeText(FrdRequestActivity.this, serverError.getMessage(), Toast.LENGTH_SHORT).show();
                                    if ( response.code() == 401 || response.code() == 500) {
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

                                            }

                                            @Override
                                            public void onFailure(Call<Void> call, Throwable t) {

                                            }
                                        });
                                    }
                            }
                        } catch (Exception ignored) {

                        }
                    }

                }
                @Override
                public void onFailure(Call<List<EachUser>> call, Throwable t) {
                    Log.d("정보","GetAllusers failure"+t.getMessage());
                }
            });
        }
    }
}
