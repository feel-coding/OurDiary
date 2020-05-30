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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sungshin.project.ourdiaryapplication.Network.FriendRequest;
import sungshin.project.ourdiaryapplication.Network.RetrofitManager;
import sungshin.project.ourdiaryapplication.Network.ServerApi;
import sungshin.project.ourdiaryapplication.Network.ServerError;
import sungshin.project.ourdiaryapplication.R;
import sungshin.project.ourdiaryapplication.friendlist.adapter.FrdlistAcceptAdapter;

public class FrdlistActivity extends AppCompatActivity {

    private ListView frdrequest_list;
    FrdlistRequestAdapter frdlistRequestAdapter;
    ArrayList<FrdlistRequestItem> frdlist_requestItem;
    private ServerApi serverApi = RetrofitManager.getInstance().getServerApi();
    private Gson gson = new Gson();

    private ListView frdaccept_list;
    FrdlistAcceptAdapter frdlistAcceptAdapter;
    ArrayList<FrdlistAcceptItem> frdlist_acceptItem;

    ImageButton back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frdlist);

        //친구 신청 listview
        frdrequest_list = (ListView)findViewById(R.id.frdrequest_list);
        frdlist_requestItem = new ArrayList<FrdlistRequestItem>();

        frdlist_requestItem.add(new FrdlistRequestItem("every1","박기범"));
        frdlist_requestItem.add(new FrdlistRequestItem("every2","원빈"));

        //서버에서 데이터 가져오기
        serverApi.getFriendRequestList().enqueue(new Callback<FriendRequest>() {
            @Override
            public void onResponse(Call<FriendRequest> call, Response<FriendRequest> response) {
                if(response.isSuccessful()) {
                    Log.d("frdlistrequest","success");
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
            public void onFailure(Call<FriendRequest> call, Throwable t) {
                Log.d("정보","frdrequest failure");
            }
        });

        frdlistRequestAdapter = new FrdlistRequestAdapter(this,frdlist_requestItem);
        frdrequest_list.setAdapter(frdlistRequestAdapter);

        frdlistRequestAdapter.notifyDataSetChanged();

        //친구 요청 listview
        frdaccept_list = (ListView)findViewById(R.id.frdaccpet_list);
        frdlist_acceptItem = new ArrayList<FrdlistAcceptItem>();

        frdlist_acceptItem.add(new FrdlistAcceptItem("happy_eat","신용순"));
        frdlist_acceptItem.add(new FrdlistAcceptItem("silence99","김범"));

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
