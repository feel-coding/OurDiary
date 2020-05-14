package sungshin.project.ourdiaryapplication.Network;

import com.google.gson.Gson;

import java.net.CookieManager;
import java.net.CookiePolicy;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {

    private static RetrofitManager instance = null; //자기자신을 변수로 가짐
    private RetrofitManager(){ //생성자를 이렇게 만들어서 아무도 new 못하게

    }

    public static RetrofitManager getInstance() {
        if(instance == null) {
            instance = new RetrofitManager(); //이게 싱글톤패턴
        }
        return instance;
    }

    public ServerApi getServerApi(){
        final CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL); //쿠키설정은 반드시 해줘야함
        //쿠키설정 안해주면 401 에러 남
        OkHttpClient client = new OkHttpClient.Builder()
//                .cookieJar(new JavaNetCookieJar(cookieManager))
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServerApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new Gson())) //fromJson toJson 안해도 알아서 변환해줘
                .client(client)
                .build();
        return retrofit.create(ServerApi.class);

    }
}
