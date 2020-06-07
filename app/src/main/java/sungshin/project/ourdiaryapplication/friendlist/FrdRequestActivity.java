package sungshin.project.ourdiaryapplication.friendlist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sungshin.project.ourdiaryapplication.Network.EachUser;
import sungshin.project.ourdiaryapplication.Network.ReqUserSignIn;
import sungshin.project.ourdiaryapplication.Network.RetrofitManager;
import sungshin.project.ourdiaryapplication.Network.ServerApi;
import sungshin.project.ourdiaryapplication.Network.ServerError;
import sungshin.project.ourdiaryapplication.friendlist.adapter.MyAdapter;
import sungshin.project.ourdiaryapplication.R;

public class FrdRequestActivity extends AppCompatActivity {

    private ListView frdList;
    MyAdapter myAdapter;
    ArrayList<EachUser> frdItem;
    EditText frdSearch;
    private ServerApi serverApi;
    private Gson gson = new Gson();
    ImageButton backbtn;
    final String SHARED_PREF_EMAIL = "EMAIL";
    final String SHARED_PREF_LOGIN_PW = "PASSWORD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frdrequest);

        serverApi = RetrofitManager.getInstance().getServerApi(this);

        frdList = findViewById(R.id.frdList);
        frdItem = new ArrayList<EachUser>();

        myAdapter = new MyAdapter(this,frdItem);
        frdList.setAdapter(myAdapter);

        //backbtn 클릭
        backbtn = findViewById(R.id.back_btn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        //내용 입력시 이벤트
        frdSearch = findViewById(R.id.editText);
        frdSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = frdSearch.getText().toString();
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
                        frdItem.clear();
                        Log.d("정보", "search clear : " + text);


                        Log.d("정보", "GetAllUsers success");
                        //todo:print response body in listview
                        List<EachUser> resultList = response.body();
                        Log.d("정보",  Integer.toString(resultList.size()));
                        for (int i = 0; i < resultList.size(); i++) {
                            frdItem.add(resultList.get(i));
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
                                    Toast.makeText(FrdRequestActivity.this, "Invalid page", Toast.LENGTH_SHORT).show();
                                    break;
                                case "INVALID_PAGE_SIZE":
                                    Toast.makeText(FrdRequestActivity.this, "Invalid page size", Toast.LENGTH_SHORT).show();
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
