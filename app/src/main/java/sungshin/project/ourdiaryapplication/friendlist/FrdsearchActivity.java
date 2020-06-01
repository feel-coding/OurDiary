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
import sungshin.project.ourdiaryapplication.R;

public class FrdsearchActivity extends AppCompatActivity {

    private ListView frdsearchList;
    FrdsearchAdapter frdsearchAdapter;
    ArrayList<FrdsearchItem> frdsearchItem;
    EditText editText_frdsearch;
    private ArrayList<FrdsearchItem> arrayList;
    private ServerApi serverApi;
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frdsearch);

        frdsearchList = findViewById(R.id.frdsearchList);
        frdsearchItem = new ArrayList<FrdsearchItem>();

        frdsearchItem.add(new FrdsearchItem("happy_eat","신용순"));
        frdsearchItem.add(new FrdsearchItem("silence99","김범"));

        //서버에서 데이터 가져오기
        serverApi = RetrofitManager.getInstance().getServerApi(this);
        serverApi.getFriendList().enqueue(new Callback<List<Friend>>() {
            @Override
            public void onResponse(Call<List<Friend>> call, Response<List<Friend>> response) {
                if(response.isSuccessful()) {
                    Log.d("frdlist","success");
                    //todo:print response body in listview
                    List<Friend> body = response.body();
                    arrayList = new ArrayList<FrdsearchItem>();

                }
                else {
                    Log.d("frdlisterror","error code"+response.code());
                    try {
                        String jsonString = response.errorBody().string();
                        Log.d("jsonString",jsonString);
                        ServerError serverError = gson.fromJson(jsonString, ServerError.class);

                        switch (serverError.getError()) {
                            case "INVALID_PAGE":
                                Toast.makeText(FrdsearchActivity.this, "Invalid page", Toast.LENGTH_SHORT).show();
                                break;
                            case "INVALID_PAGE_SIZE":
                                Toast.makeText(FrdsearchActivity.this, "Invalid page size", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                Toast.makeText(FrdsearchActivity.this, serverError.getMessage(), Toast.LENGTH_SHORT).show();
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
        arrayList = new ArrayList<FrdsearchItem>();
        arrayList.addAll(frdsearchItem);

        frdsearchAdapter = new FrdsearchAdapter(this,frdsearchItem);
        frdsearchList.setAdapter(frdsearchAdapter);


        //내용 입력시 이벤트
        editText_frdsearch = findViewById(R.id.editText_frdsearch);
        editText_frdsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = editText_frdsearch.getText().toString();
                search(text);
            }
        });
    }

    //검색 수행
    public void search(String text) {
        //입력시 리스트 지우기
        frdsearchItem.clear();

        //입력 없을경우 데이터 모두 보여주기
        if(text.length()==0) {
            frdsearchItem.addAll(arrayList);
        }
        else {
            for(int i=0; i<arrayList.size(); i++) {
                //닉네임 찾아 일치하면 리스트에 추가
                if(arrayList.get(i).getNickname().toLowerCase().contains(text.toLowerCase())) {
                    frdsearchItem.add(arrayList.get(i));
                }
            }
        }
        frdsearchAdapter.notifyDataSetChanged();
    }
}
