package sungshin.project.ourdiaryapplication.Network;

import android.content.Context;

import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.JavaNetCookieJar;
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

    public ServerApi getServerApi(Context context){
        final CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        //쿠키설정 안해주면 401 에러 남
        CookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context));
        OkHttpClient client = new OkHttpClient.Builder()
//                .cookieJar(new JavaNetCookieJar(cookieManager))
                .cookieJar(cookieJar)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServerApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create())) //fromJson toJson 안해도 알아서 변환해줘
                .client(client)
                .build();
        return retrofit.create(ServerApi.class);

    }
}
