package sungshin.project.ourdiaryapplication.friendlist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sungshin.project.ourdiaryapplication.Login.SignupActivity;
import sungshin.project.ourdiaryapplication.Network.Friend;
import sungshin.project.ourdiaryapplication.Network.FriendRequest;
import sungshin.project.ourdiaryapplication.Network.RetrofitManager;
import sungshin.project.ourdiaryapplication.Network.ServerApi;
import sungshin.project.ourdiaryapplication.Network.ServerError;
import sungshin.project.ourdiaryapplication.Network.EachUser;
import sungshin.project.ourdiaryapplication.Network.RetrofitManager;
import sungshin.project.ourdiaryapplication.Network.ServerApi;
import sungshin.project.ourdiaryapplication.R;

public class FrdSearchActivity extends AppCompatActivity {

    private ListView frdsearchList;
    FrdsearchAdapter frdsearchAdapter;
    ArrayList<FrdSearchItem> frdsearchItem;
    EditText editText_frdsearch;
    private ArrayList<FrdSearchItem> arrayList;
    private ServerApi serverApi;
    private Gson gson = new Gson();
    ArrayList<FrdSearchItem> searchResultList;
    EditText friendSearchEdit;
    String nickname;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frdsearch);
        serverApi = RetrofitManager.getInstance().getServerApi(this);
        frdsearchList = findViewById(R.id.frdsearchList);
        searchResultList = new ArrayList<FrdSearchItem>();

        //서버에서 데이터 가져오기
        serverApi = RetrofitManager.getInstance().getServerApi(this);
        serverApi.getFriendList().enqueue(new Callback<List<Friend>>() {
            @Override
            public void onResponse(Call<List<Friend>> call, Response<List<Friend>> response) {
                if(response.isSuccessful()) {
                    Log.d("frdlist","success");
                    //todo:print response body in listview
                    List<Friend> body = response.body();
                    arrayList = new ArrayList<FrdSearchItem>();

                }
                else {
                    Log.d("frdlisterror","error code"+response.code());
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

        //frdItem 데이터 arrayList에 복사
        arrayList = new ArrayList<FrdSearchItem>();
        arrayList.addAll(searchResultList);

        frdsearchAdapter = new FrdsearchAdapter(this, searchResultList);
        frdsearchList.setAdapter(frdsearchAdapter);


        //내용 입력시 이벤트
        friendSearchEdit = findViewById(R.id.editText_frdsearch);
        friendSearchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                serverApi.getAllUsers(1, 15, charSequence.toString()).enqueue(new Callback<List<EachUser>>() {
                    @Override
                    public void onResponse(Call<List<EachUser>> call, Response<List<EachUser>> response) {
                        if(response.isSuccessful()) {
                            Log.d("searchsuccess", "성공");
                            for (int i = 0; i < response.body().size(); i++) {
                                nickname = response.body().get(i).getNick();
                                name = response.body().get(i).getName();
                                Integer sequenceNumber = response.body().get(i).getSeq();
                                if(nickname == null) {
                                    searchResultList.add(new FrdSearchItem("닉네임 없는 사용자", name, sequenceNumber));
                                }
                                else
                                    searchResultList.add(new FrdSearchItem(nickname, name, sequenceNumber));
                            }
                            frdsearchAdapter.notifyDataSetChanged();
                        }
                        else {
                            Log.d("searcherror", "onResponse까지는 성공했지만 " + response.code() + " error");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<EachUser>> call, Throwable t) {
                        Log.d("searcherror", t.getMessage());
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = friendSearchEdit.getText().toString();
                search(text);
            }
        });
    }

    //검색 수행
    public void search(String text) {
        //입력시 리스트 지우기
        searchResultList.clear();

        //입력 없을경우 데이터 모두 보여주기
        if(text.length()==0) {
            searchResultList.addAll(arrayList);
        }
        else {
            for(int i=0; i<arrayList.size(); i++) {
                //닉네임 찾아 일치하면 리스트에 추가
                if(arrayList.get(i).getNickname().toLowerCase().contains(text.toLowerCase())) {
                    searchResultList.add(arrayList.get(i));
                }
            }
        }
        frdsearchAdapter.notifyDataSetChanged();
    }
}