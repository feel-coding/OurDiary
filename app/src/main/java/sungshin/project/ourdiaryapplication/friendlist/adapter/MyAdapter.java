package sungshin.project.ourdiaryapplication.friendlist.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sungshin.project.ourdiaryapplication.Network.EachUser;
import sungshin.project.ourdiaryapplication.Network.ReqCreateFriendRequest;
import sungshin.project.ourdiaryapplication.Network.RetrofitManager;
import sungshin.project.ourdiaryapplication.Network.ServerApi;
import sungshin.project.ourdiaryapplication.Network.ServerError;
import sungshin.project.ourdiaryapplication.R;
import sungshin.project.ourdiaryapplication.friendlist.FrdrequestActivity;
import sungshin.project.ourdiaryapplication.friendlist.FrdrequestItem;

public class MyAdapter extends BaseAdapter {
    Context mContext = null;
    ArrayList<EachUser> frdrequest;
    private ServerApi serverApi;
    private Gson gson = new Gson();

    public MyAdapter(Context context, ArrayList<EachUser> data) {
        mContext = context;
        frdrequest = data;
    }

    @Override
    public int getCount() {
        return frdrequest.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return frdrequest.get(position);
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if(view==null) {
            LayoutInflater mInflator = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = mInflator.inflate(R.layout.frdrequest_item, viewGroup,false);
        }
        TextView frdrequest_frd = (TextView)view.findViewById(R.id.frdrequest_frd);
        ImageButton frdrequest_btn = (ImageButton) view.findViewById(R.id.frdrequest_btn);

        //이미 신청한 친구인 경우는 버튼 invisible
        if(frdrequest.get(position).getFriend() == true) {
            frdrequest_btn.setVisibility(View.INVISIBLE);
        }

        frdrequest_frd.setText(frdrequest.get(position).getName());
      //  Log.d("정보", "MyAdapter" + Integer.toString(position) + frdrequest.get(position).getName());
        frdrequest_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //데이터 보내기(서버 연결)
                ReqCreateFriendRequest req = new ReqCreateFriendRequest();
                req.setUserSeq(frdrequest.get(position).getSeq());
                serverApi = RetrofitManager.getInstance().getServerApi(mContext);
                serverApi.createFriendRequest(req).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.isSuccessful()) {
                            Toast.makeText(mContext, "친구를 신청했습니다", Toast.LENGTH_SHORT).show();
                            Log.d("정보","frdrequest success");
                            frdrequest_btn.setVisibility(View.INVISIBLE);
                        }
                        else {
                            Log.d("frdrequesterror","error code"+response.code());
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
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.d("정보","frdrequest failure");
                    }
                });
            }
        });
        return view;
    }
}
