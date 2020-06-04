package sungshin.project.ourdiaryapplication.Network;

import java.math.BigInteger;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ServerApi {

    String BASE_URL = "http://www.ourdiary.site:8081"; //서버 url

    @PUT("/api/v1/users/fcmtoken") //저 base url 뒤에 붙을 url
    Call<Void> updateUserFcm();

    @GET("/api/v1/users/me")
    Call<User> getUserMe();

    @PUT("/api/v1/users/me")
    Call<User> updateUser();

    @POST("/api/v1/users/signin")
    Call<Void> signInUser(@Body ReqUserSignIn reqUserSignIn);

    @POST("/api/v1/users/signup")
    Call<Void> signUpUser(@Body ReqUserSignUp reqUserSignUp);

    @POST("/api/v1/users/signout")
    Call<Void> signOut();

    @POST("/api/v1/friends/requests") //POST의 경우 Body 사용
    Call<Void> createFriendRequest(@Body ReqCreateFriendRequest reqCreateFriendRequest);

    @PUT("/api/v1/friends/requests/{seq}")
    Call<Void> updateFriendRequest(@Body ReqFriendRequestUpdate reqFriendRequestUpdate, @Path("seq")Integer seq);

    @GET("/api/v1/friends/requests")
    Call<List<FriendReq>> getFriendRequestList(@Query("direction") String direction, @Query("page") Integer page, @Query("page_size") Integer pageSize);

    @GET("/api/v1/friends")
    Call<List<Friend>> getFriendList(@Query("page") Integer page, @Query("page_size") Integer pageSize, @Query("query") String query);

    @PUT("/api/v1/users/me")
    Call<User> updateUserMe(@Body ReqUserUpdateMe reqUserUpdateMe);

    @DELETE("/api/v1/friends")
    Call<Void> deleteFriend();

    @GET("/api/v1/users")
    Call<List<EachUser>> getAllUsers(@Query("page") Integer page, @Query("page_size") Integer pageSize, @Query("query") String query);

    @GET("/api/v1/diaries")
    Call<List<Diary>> getDiaries(@Query("filterType") String filterType);

    @DELETE("/api/v1/users/me")
    Call<Void> deleteMyAccount();
}
