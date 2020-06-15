package sungshin.project.ourdiaryapplication.friendlist.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.math.BigInteger;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sungshin.project.ourdiaryapplication.DocumentActivity;
import sungshin.project.ourdiaryapplication.Network.ReqFriendDelete;
import sungshin.project.ourdiaryapplication.Network.RetrofitManager;
import sungshin.project.ourdiaryapplication.Network.ServerApi;
import sungshin.project.ourdiaryapplication.R;
import sungshin.project.ourdiaryapplication.friendlist.data.FriendItem;
import sungshin.project.ourdiaryapplication.friendlist.viewholder.FriendListViewHolder;

public class FriendListAdapter extends RecyclerView.Adapter<FriendListViewHolder> {

    ArrayList<FriendItem> fData = null;
    ImageView friend_item_indicator;
    ServerApi serverApi;

    public FriendListAdapter(ArrayList<FriendItem> list) {
        fData = list;
    }

    @NonNull
    @Override
    public FriendListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_friend_list_item, parent, false);

        FriendListViewHolder viewHolder = new FriendListViewHolder(view);
        friend_item_indicator = view.findViewById(R.id.friend_item_indicator);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FriendListViewHolder viewHolder, int position) {
        viewHolder.profileImg.setImageDrawable(fData.get(position).getfProfileImg());
        viewHolder.friendNickname.setText(fData.get(position).getfNickname());
        viewHolder.friendRealname.setText(fData.get(position).getfRealname());

        //친구 삭제
        friend_item_indicator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
                //팝업 xml inflate
                popupMenu.getMenuInflater().inflate(R.menu.friendlist_popup, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.delete:
                                //삭제 클릭시 이벤트 작성
                                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                                builder.setMessage("글을 삭제할까요?");
                                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //친구 삭제 서버연동
                                        serverApi = RetrofitManager.getInstance().getServerApi(view.getContext());
                                        ReqFriendDelete seq = new ReqFriendDelete();
                                        seq.setFriend_seq(fData.get(position).getfSeq());
                                        serverApi.deleteFriend(seq).enqueue(new Callback<Void>() {
                                            @Override
                                            public void onResponse(Call<Void> call, Response<Void> response) {
                                                if(response.isSuccessful()) {
                                                    Toast.makeText(view.getContext(),"삭제되었습니다",Toast.LENGTH_SHORT).show();
                                                } else
                                                {
                                                    Log.d("정보","friendlistadapter error:"+response.errorBody().toString());
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<Void> call, Throwable t) {
                                                Log.d("정보","friendlistadapter failure");
                                            }
                                        });
                                    }
                                });
                                builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Toast.makeText(view.getContext(),"삭제를 취소합니다",Toast.LENGTH_LONG).show();
                                    }
                                });
                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();
                                break;
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != fData ? fData.size() : 0);
    }

}
