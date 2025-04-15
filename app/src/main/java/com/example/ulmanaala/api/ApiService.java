package com.example.ulmanaala.api;

import com.example.ulmanaala.request.FindIdRequest;
import com.example.ulmanaala.response.FindIdResponse;
import com.example.ulmanaala.request.LoginRequest;
import com.example.ulmanaala.response.LoginResponse;
import com.example.ulmanaala.request.RegisterRequest;
import com.example.ulmanaala.response.RegisterResponse;
import com.example.ulmanaala.request.ResetPasswordRequest;
import com.example.ulmanaala.response.ResetPasswordResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    // 회원가입 API
    @POST("register/")
    Call<RegisterResponse> registerUser(@Body RegisterRequest registerRequest);

    // 로그인 API
    @POST("login/")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);

    // 아이디 찾기 API
    @POST("find-id/")
    Call<FindIdResponse> findId(@Body FindIdRequest findIdRequest);

    // 비밀번호 찾기 API
    @POST("reset-password/")
    Call<ResetPasswordResponse> resetPassword(@Body ResetPasswordRequest resetPasswordRequest);
}