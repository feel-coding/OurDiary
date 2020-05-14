package sungshin.project.ourdiaryapplication.Network;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ServerApi {

    String BASE_URL = "http://www.ourdiary.site:8081";

    @PUT("/api/v1/users/fcmtoken")
    Call<Void> updateUserFcm();

    @GET("/api/v1/users/me")
    Call<User> getUser();

    @PUT("/api/v1/users/me")
    Call<User> updateUser();

    @POST("/api/v1/users/signin")
    Call<Void> signInUser(@Body ReqSignInUser reqSignInUser);

    @POST("/api/v1/users/signup")
    Call<Void> signUpUser();
}
