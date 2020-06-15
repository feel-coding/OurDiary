package sungshin.project.ourdiaryapplication.friendlist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sungshin.project.ourdiaryapplication.Network.Friend;
import sungshin.project.ourdiaryapplication.Network.FriendReq;
import sungshin.project.ourdiaryapplication.Network.RetrofitManager;
import sungshin.project.ourdiaryapplication.Network.ServerApi;
import sungshin.project.ourdiaryapplication.Network.ServerError;
import sungshin.project.ourdiaryapplication.Network.EachUser;
import sungshin.project.ourdiaryapplication.R;

public class FrdSearchActivity extends AppCompatActivity {

    private ListView frdsearchList;
    FrdsearchAdapter frdsearchAdapter;
    EditText editText_frdsearch;
    private ArrayList<Friend> arrayList;
    private ServerApi serverApi;
    private Gson gson = new Gson();
    EditText friendSearchEdit;
    String nickname;
    String name;
    Button button_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frdsearch);
        frdsearchList = findViewById(R.id.frdsearchList);
        arrayList = new ArrayList<Friend>();

        //서버에서 데이터 가져오기(친구 리스트 모두)
        serverApi = RetrofitManager.getInstance().getServerApi(this);
        serverApi.getFriendList(1,15,"").enqueue(new Callback<List<Friend>>() {
            @Override
            public void onResponse(Call<List<Friend>> call, Response<List<Friend>> response) {
                if(response.isSuccessful()) {
                    //todo:print response body in listview
                    List<Friend> body = response.body();
                    Log.d("정보","friendsearch_success:"+body.size());
                    for(int i = 0; i < body.size(); i++) {
                        arrayList.add(body.get(i));
                        Log.d("정보","friend :"+body.get(i).getName());
                  }
//                    Friend frd = new Friend();
//                    frd.setName("김");
//                    frd.setNick("박");
//                    arrayList.add(frd);
                    frdsearchAdapter.notifyDataSetChanged();
                }
                else {
                    Log.d("정보","friendsearch_error code"+response.code());
                    try {
                        String jsonString = response.errorBody().string();
                        Log.d("jsonString",jsonString);
                        ServerError serverError = gson.fromJson(jsonString, ServerError.class);

                        switch (serverError.getError()) {
                            case "INVALID_PAGE":
                                Toast.makeText(FrdSearchActivity.this, "Invalid page", Toast.LENGTH_SHORT).show();
                                break;
                            case "INVALID_PAGE_SIZE":
                                Toast.makeText(FrdSearchActivity.this, "Invalid page size", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                Toast.makeText(FrdSearchActivity.this, serverError.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception ignored) {

                    }
                }
            }

            @Override
            public void onFailure(Call<List<Friend>> call, Throwable t) {
                Log.d("정보","frdrequest failure"+t.getMessage());
            }
        });
        arrayList.clear();


        frdsearchAdapter = new FrdsearchAdapter(this, arrayList);
        frdsearchList.setAdapter(frdsearchAdapter);


        //내용 입력시 이벤트(query에 맞는 친구만)
        friendSearchEdit = findViewById(R.id.editText_frdsearch);
        friendSearchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //입력한 경우에만 clear하기
                arrayList.clear();
                serverApi.getFriendList(1, 15, charSequence.toString()).enqueue(new Callback<List<Friend>>() {
                    @Override
                    public void onResponse(Call<List<Friend>> call, Response<List<Friend>> response) {
                        if(response.isSuccessful()) {
                            arrayList.clear();

                            Log.d("searchsuccess", "성공");
                            List<Friend> body = response.body();
                            for (int i = 0; i < body.size(); i++) {
                                arrayList.add(body.get(i));
                            }
                            frdsearchAdapter.notifyDataSetChanged();
                        }
                        else {
                            Log.d("searcherror", "onResponse까지는 성공했지만 " + response.code() + " error");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Friend>> call, Throwable t) {
                        Log.d("searcherror", t.getMessage());
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //취소 버튼 클릭
        button_cancel = findViewById(R.id.button_cancel);
        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    //임시로 함수 생성
//    public void getFriendList(ArrayList<Friend> arrayList, String query) {
//        serverApi.getFriendRequestList("MY_SEND", 1,15).enqueue(new Callback<List<FriendReq>>() {
//            @Override
//            public void onResponse(Call<List<FriendReq>> call, Response<List<FriendReq>> response) {
//                if(response.isSuccessful()) {
//                    //todo:print response body in listview
//                    List<FriendReq> body = response.body();
//                    Log.d("정보","friendsearch_success:"+body.size());
//                    for(int i = 0; i < body.size(); i++) {
//                        if (body.get(i).getState().equals("ACCEPTED")) {
//                            Friend fr = new Friend();
//                            fr.setName(body.get(i).getUser().getName());
//                            fr.setNick(body.get(i).getUser().getNick());
//                            if (query != null) {
//                                if (fr.getName().contains(query) || fr.getNick().contains(query))
//                                    arrayList.add(fr);
//                            }
//                            else
//                                arrayList.add(fr);
//                        }
//                    }
//                    frdsearchAdapter.notifyDataSetChanged();
//                }
//                else {
//                    Log.d("정보","friendsearch_error code"+response.code());
//                    try {
//                        String jsonString = response.errorBody().string();
//                        Log.d("jsonString",jsonString);
//                        ServerError serverError = gson.fromJson(jsonString, ServerError.class);
//
//                        switch (serverError.getError()) {
//                            case "INVALID_PAGE":
//                                Toast.makeText(FrdSearchActivity.this, "Invalid page", Toast.LENGTH_SHORT).show();
//                                break;
//                            case "INVALID_PAGE_SIZE":
//                                Toast.makeText(FrdSearchActivity.this, "Invalid page size", Toast.LENGTH_SHORT).show();
//                                break;
//                            default:
//                                Toast.makeText(FrdSearchActivity.this, serverError.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    } catch (Exception ignored) {
//
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<FriendReq>> call, Throwable t) {
//                Log.d("정보","frdrequest failure"+t.getMessage());
//            }
//
//        });
//
//        serverApi.getFriendRequestList("FRIEND_SEND", 1,15).enqueue(new Callback<List<FriendReq>>() {
//            @Override
//            public void onResponse(Call<List<FriendReq>> call, Response<List<FriendReq>> response) {
//                if(response.isSuccessful()) {
//                    //todo:print response body in listview
//                    List<FriendReq> body = response.body();
//                    Log.d("정보","friendsearch_success:"+body.size());
//                    for(int i = 0; i < body.size(); i++) {
//                        if (body.get(i).getState().equals("ACCEPTED")) {
//                            Friend fr = new Friend();
//                            fr.setName(body.get(i).getUser().getName());
//                            fr.setNick(body.get(i).getUser().getNick());
//                            if (query != null) {
//                                if (fr.getName().contains(query) || fr.getNick().contains(query))
//                                    arrayList.add(fr);
//                            }
//                            else
//                                arrayList.add(fr);
//                        }
//                    }
//                    frdsearchAdapter.notifyDataSetChanged();
//                }
//                else {
//                    Log.d("정보","friendsearch_error code"+response.code());
//                    try {
//                        String jsonString = response.errorBody().string();
//                        Log.d("jsonString",jsonString);
//                        ServerError serverError = gson.fromJson(jsonString, ServerError.class);
//
//                        switch (serverError.getError()) {
//                            case "INVALID_PAGE":
//                                Toast.makeText(FrdSearchActivity.this, "Invalid page", Toast.LENGTH_SHORT).show();
//                                break;
//                            case "INVALID_PAGE_SIZE":
//                                Toast.makeText(FrdSearchActivity.this, "Invalid page size", Toast.LENGTH_SHORT).show();
//                                break;
//                            default:
//                                Toast.makeText(FrdSearchActivity.this, serverError.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    } catch (Exception ignored) {
//
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<FriendReq>> call, Throwable t) {
//                Log.d("정보","frdrequest failure"+t.getMessage());
//            }
//
//        });
//
//    }
}
