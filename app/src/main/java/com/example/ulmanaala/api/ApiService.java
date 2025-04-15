package com.example.ulmanaala.api;

import com.example.ulmanaala.request.FindIdRequest;
import com.example.ulmanaala.request.LoginRequest;
import com.example.ulmanaala.request.RegisterRequest;
import com.example.ulmanaala.request.ResetPasswordRequest;
import com.example.ulmanaala.response.DailyFactResponse;
import com.example.ulmanaala.response.FindIdResponse;
import com.example.ulmanaala.response.LoginResponse;
import com.example.ulmanaala.response.RegisterResponse;
import com.example.ulmanaala.response.ResetPasswordResponse;
import com.example.ulmanaala.response.UserProfileResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @POST("register/")
    Call<RegisterResponse> registerUser(@Body RegisterRequest registerRequest);

    @POST("login/")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);

    @POST("find-id/")
    Call<FindIdResponse> findId(@Body FindIdRequest findIdRequest);

    @POST("reset-password/")
    Call<ResetPasswordResponse> resetPassword(@Body ResetPasswordRequest resetPasswordRequest);

    @GET("profile/")
    Call<UserProfileResponse> getUserProfile(@Header("Authorization") String authHeader);

    @GET("/daily-facts/")
    Call<DailyFactResponse> getDailyFacts(@Query("email") String email);


}
