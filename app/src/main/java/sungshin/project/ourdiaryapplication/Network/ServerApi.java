package sungshin.project.ourdiaryapplication.Network;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

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

    @GET("/api/v1/friends/requests")
    Call<FriendRequest> getFriendRequestList();

    @GET("/api/v1/friends")
    Call<Friend> getFriendList();

    @PUT("/api/v1/users/me")
    Call<User> updateUserMe(@Body ReqUserUpdateMe reqUserUpdateMe);

    @DELETE("/api/v1/friends")
    Call<Void> deleteFriend();
}
