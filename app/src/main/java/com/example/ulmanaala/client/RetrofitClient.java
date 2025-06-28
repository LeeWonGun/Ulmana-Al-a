package com.example.ulmanaala.client;

import com.example.ulmanaala.api.ApiService;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit defaultRetrofit = null;
    private static Retrofit chatRetrofit = null;

    // ✅ Django 서버 주소
    private static final String BASE_URL = "http://43.200.172.76:8000/";

    // ✅ OpenAI API 주소
    private static final String CHAT_API_URL = "https://api.openai.com/";

    // ✅ Django 백엔드용 ApiService
    public static ApiService getApiService() {
        if (defaultRetrofit == null) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                    .retryOnConnectionFailure(true)  // ✅ 재시도 설정
                    .connectTimeout(15, TimeUnit.SECONDS)  // ✅ 연결 타임아웃
                    .readTimeout(15, TimeUnit.SECONDS)     // ✅ 응답 수신 타임아웃
                    .writeTimeout(15, TimeUnit.SECONDS)    // ✅ 요청 송신 타임아웃
                    .addInterceptor(loggingInterceptor)
                    .build();

            defaultRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return defaultRetrofit.create(ApiService.class);
    }

    // ✅ ChatGPT(OpenAI)용 ApiService
    public static ApiService getChatApiService() {
        if (chatRetrofit == null) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                    .retryOnConnectionFailure(true)
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .readTimeout(15, TimeUnit.SECONDS)
                    .writeTimeout(15, TimeUnit.SECONDS)
                    .addInterceptor(loggingInterceptor)
                    .build();

            chatRetrofit = new Retrofit.Builder()
                    .baseUrl(CHAT_API_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return chatRetrofit.create(ApiService.class);
    }
}
