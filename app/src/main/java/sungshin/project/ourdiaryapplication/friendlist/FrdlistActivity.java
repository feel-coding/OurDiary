package sungshin.project.ourdiaryapplication.friendlist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sungshin.project.ourdiaryapplication.Network.FriendReq;
import sungshin.project.ourdiaryapplication.Network.RetrofitManager;
import sungshin.project.ourdiaryapplication.Network.ServerApi;
import sungshin.project.ourdiaryapplication.Network.ServerError;
import sungshin.project.ourdiaryapplication.R;
import sungshin.project.ourdiaryapplication.friendlist.adapter.FrdlistAcceptAdapter;

public class FrdlistActivity extends AppCompatActivity {

    private ListView frdrequest_list;
    FrdlistRequestAdapter frdlistRequestAdapter;
    ArrayList<FriendReq> frdlist_requestItem;
    private ServerApi serverApi;
    private Gson gson = new Gson();

    private ListView frdaccept_list;
    FrdlistAcceptAdapter frdlistAcceptAdapter;
    ArrayList<FriendReq> frdlist_acceptItem;

    ImageButton back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frdlist);

        //친구 신청 listview
        frdrequest_list = (ListView)findViewById(R.id.frdrequest_list);
        frdlist_requestItem = new ArrayList<FriendReq>();

        //서버에서 데이터 가져오기
        serverApi = RetrofitManager.getInstance().getServerApi(this);
        serverApi.getFriendRequestList("MY_SEND", 1,  15).enqueue(new Callback<List<FriendReq>>() {
            @Override
            public void onResponse(Call<List<FriendReq>> call, Response<List<FriendReq>> response) {
                if(response.isSuccessful()) {
                    List<FriendReq> body = response.body();
                    Log.d("frdlistrequest","success"+body.size());
                    for(int i = 0; i < body.size(); i++) {
                        //친구 수락시 신청 현황에서 삭제
                        if(!body.get(i).getState().equals("ACCEPTED"))
                            frdlist_requestItem.add(body.get(i));
                        Log.d("정보", Integer.toString(i) + body.get(i).getUser().getName());
                    }
                    frdlistRequestAdapter.notifyDataSetChanged();

                }
                else {
                    Log.d("frdlistrequesterror","error code"+response.code());
                    try {
                        String jsonString = response.errorBody().string();
                        ServerError serverError = gson.fromJson(jsonString, ServerError.class);

                        Toast.makeText(FrdlistActivity.this, serverError.getMessage(), Toast.LENGTH_SHORT).show();

                    } catch (Exception ignored) {

                    }
                }
            }

            @Override
            public void onFailure(Call<List<FriendReq>> call, Throwable t) {
                Log.d("정보","frdrequest failure");
            }
        });

        frdlistRequestAdapter = new FrdlistRequestAdapter(this,frdlist_requestItem);
        frdrequest_list.setAdapter(frdlistRequestAdapter);


        //친구 요청 listview
        frdaccept_list = (ListView)findViewById(R.id.frdaccpet_list);
        frdlist_acceptItem = new ArrayList<FriendReq>();

        serverApi.getFriendRequestList("FRIEND_SEND", 1,  15).enqueue(new Callback<List<FriendReq>>() {
            @Override
            public void onResponse(Call<List<FriendReq>> call, Response<List<FriendReq>> response) {
                if(response.isSuccessful()) {
                    List<FriendReq> body = response.body();
                    Log.d("정보","success"+body.size());
                    for(int i = 0; i < body.size(); i++) {
                        Log.d("정보","getState:"+body.get(i).getState());
                        if(!body.get(i).getState().equals("ACCEPTED")) {
                            frdlist_acceptItem.add(body.get(i));
                            Log.d("정보","getState: TRUE "+body.get(i).getState());

                        }
                        Log.d("정보", Integer.toString(i) + body.get(i).getUser().getName());
                    }
                    frdlistAcceptAdapter.notifyDataSetChanged();

                }
                else {
                    Log.d("frdlistrequesterror","error code"+response.code());
                    try {
                        String jsonString = response.errorBody().string();
                        ServerError serverError = gson.fromJson(jsonString, ServerError.class);

                        switch (serverError.getError()) {

                        }
                    } catch (Exception ignored) {

                    }
                }
            }

            @Override
            public void onFailure(Call<List<FriendReq>> call, Throwable t) {
                Log.d("정보","frdrequest failure");
            }
        });

        frdlistAcceptAdapter = new FrdlistAcceptAdapter(this,frdlist_acceptItem);
        frdaccept_list.setAdapter(frdlistAcceptAdapter);

        frdlistAcceptAdapter.notifyDataSetChanged();

        back_btn = findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
