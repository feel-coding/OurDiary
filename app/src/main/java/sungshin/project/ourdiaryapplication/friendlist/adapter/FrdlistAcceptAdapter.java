package sungshin.project.ourdiaryapplication.friendlist.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.math.BigInteger;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sungshin.project.ourdiaryapplication.Network.FriendReq;
import sungshin.project.ourdiaryapplication.Network.ReqFriendRequestUpdate;
import sungshin.project.ourdiaryapplication.Network.RetrofitManager;
import sungshin.project.ourdiaryapplication.Network.ServerApi;
import sungshin.project.ourdiaryapplication.Network.ServerError;
import sungshin.project.ourdiaryapplication.R;

public class FrdlistAcceptAdapter extends BaseAdapter {
    Context mContext = null;
    ArrayList<FriendReq> frdlist_accept;
    private ServerApi serverApi;
    private Gson gson = new Gson();

    public FrdlistAcceptAdapter(Context context, ArrayList<FriendReq> data) {
        mContext = context;
        frdlist_accept = data;
    }

    @Override
    public int getCount() {
        return frdlist_accept.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return frdlist_accept.get(position);
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if(view==null) {
            LayoutInflater mInflator = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = mInflator.inflate(R.layout.frdlist_accept_item, viewGroup,false);
        }
        TextView frdlist_accept_nick = (TextView)view.findViewById(R.id.frdlist_accept_nick);
        TextView frdlist_accept_name = view.findViewById(R.id.frdlist_accept_name);
        Button frdlist_accept_btn1 = view.findViewById(R.id.frdlist_accept_btn1);
        Button frdlist_accept_btn2 = view.findViewById(R.id.frdlist_accept_btn2);

        if (frdlist_accept.get(position).getUser().getNick() == null)
            frdlist_accept_nick.setText("닉네임이 없는 사용자");
        else
            frdlist_accept_nick.setText(frdlist_accept.get(position).getUser().getNick());


        frdlist_accept_name.setText("("+frdlist_accept.get(position).getUser().getName()+")");

        //수락 클릭
        frdlist_accept_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serverApi = RetrofitManager.getInstance().getServerApi(mContext);
                ReqFriendRequestUpdate req = new ReqFriendRequestUpdate();
                req.setResponse("ACCEPT");
                serverApi.updateFriendRequest(req, frdlist_accept.get(position).getSeq())
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.isSuccessful()) {
                            Log.d("frdlistaccept","success");
                            Toast.makeText(mContext,"수락되었습니다",Toast.LENGTH_SHORT).show();

                            //친구 수락시 요청 받음 목록에서 삭제
                            frdlist_accept.remove(position);

                        }
                        else {
                            Log.d("frdlistaccepterror","error code"+response.code());
                            try {
                                String jsonString = response.errorBody().string();
                                ServerError serverError = gson.fromJson(jsonString, ServerError.class);

                                if(response.code() == 400) {
                                    Toast.makeText(mContext,"이미 수락된 친구입니다",Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(mContext, serverError.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception ignored) {

                            }
                        }
                    }


                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
            }

        });

        //거절 클릭
        frdlist_accept_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReqFriendRequestUpdate req = new ReqFriendRequestUpdate();
                req.setResponse("DENY");

                serverApi.updateFriendRequest(req, frdlist_accept.get(position).getSeq())
                        .enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.isSuccessful()) {
                                    Log.d("frdlistaccept", "success");
                                    Toast.makeText(mContext, "거절되었습니다", Toast.LENGTH_SHORT).show();

                                } else {
                                    Log.d("frdlistaccpeterror", "error code" + response.code());
                                    try {
                                        String jsonString = response.errorBody().string();
                                        ServerError serverError = gson.fromJson(jsonString, ServerError.class);
                                        if(response.code() == 400) {
                                            Toast.makeText(mContext,"이미 수락된 친구입니다",Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(mContext, serverError.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (Exception ignored) {

                                    }
                                }
                            }


                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {

                            }

                        });
            }

        });

        return view;
    }
}