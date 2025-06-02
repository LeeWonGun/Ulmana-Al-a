package com.example.ulmanaala.client;

import com.example.ulmanaala.api.ApiService;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit defaultRetrofit = null;
    private static Retrofit chatRetrofit = null;


    // private static final String BASE_URL = "http://10.0.2.2:8000/";
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
