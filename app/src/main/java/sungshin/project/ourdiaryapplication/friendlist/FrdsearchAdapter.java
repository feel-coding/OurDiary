package sungshin.project.ourdiaryapplication.friendlist;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sungshin.project.ourdiaryapplication.Network.ReqCreateFriendRequest;
import sungshin.project.ourdiaryapplication.Network.ReqFriendRequestCreate;
import sungshin.project.ourdiaryapplication.Network.RetrofitManager;
import sungshin.project.ourdiaryapplication.Network.ServerApi;
import sungshin.project.ourdiaryapplication.Network.User;
import sungshin.project.ourdiaryapplication.R;

public class FrdsearchAdapter extends BaseAdapter{

    Context mContext = null;
    ArrayList<FrdSearchItem> frdsearch;
    ServerApi serverApi;
    Integer mySeq;

    public FrdsearchAdapter(Context context, ArrayList<FrdSearchItem> data) {
        mContext = context;
        frdsearch = data;
        serverApi = RetrofitManager.getInstance().getServerApi(mContext);
    }

    @Override
    public int getCount() {
        return frdsearch.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return frdsearch.get(position);
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if(view == null) {
            LayoutInflater mInflator = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = mInflator.inflate(R.layout.frdsearch_item, viewGroup,false);
        }
        TextView frdsearch_nick = view.findViewById(R.id.frdsearch_nick);
        TextView frdsearch_name = view.findViewById(R.id.frdsearch_name);
        Button requestBtn = view.findViewById(R.id.request_btn);
        requestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReqCreateFriendRequest req = new ReqCreateFriendRequest();
                serverApi.getUserMe().enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if(response.isSuccessful())
                            mySeq = response.body().getSeq();
                        else {
                            Log.d("getUserError", "error " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }
                });
                req.setUserSeq(mySeq);
                serverApi.createFriendRequest(req).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Log.d("friendRequest", "친구 신청 성공");
                        }
                        else {
                            Log.d("friendRequestError", "친구 신청 실패 에러: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.d("friendRequestFail", t.getMessage());
                    }
                });
            }
        });

        frdsearch_nick.setText(frdsearch.get(position).getNickname());
        frdsearch_name.setText("("+frdsearch.get(position).getName()+")");

        return view;
    }
}


