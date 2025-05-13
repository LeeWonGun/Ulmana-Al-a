package com.example.ulmanaala.api;

import com.example.ulmanaala.request.ChatRequest;
import com.example.ulmanaala.request.FindIdRequest;
import com.example.ulmanaala.request.LoginRequest;
import com.example.ulmanaala.request.QuizResultRequest;
import com.example.ulmanaala.request.RegisterRequest;
import com.example.ulmanaala.request.ResetPasswordRequest;
import com.example.ulmanaala.response.ChatResponse;
import com.example.ulmanaala.response.DailyFactResponse;
import com.example.ulmanaala.response.FindIdResponse;
import com.example.ulmanaala.response.LoginResponse;
import com.example.ulmanaala.response.QuestionResponse;
import com.example.ulmanaala.response.QuizResultResponse;
import com.example.ulmanaala.response.RegisterResponse;
import com.example.ulmanaala.response.ResetPasswordResponse;
import com.example.ulmanaala.response.SpeedQuizResponse;
import com.example.ulmanaala.response.UserProfileResponse;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Query;

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

    // 유저 프로필
    @GET("profile/")
    Call<UserProfileResponse> getUserProfile(@Header("Authorization") String authHeader);

    // 닉네임 변경 API
    @PATCH("profile/update-nickname/")
    Call<ResponseBody> updateNickname(
            @Header("Authorization") String authToken,
            @Body Map<String, String> body
    );

    // 데일리 상식
    @GET("/daily-facts/")
    Call<DailyFactResponse> getDailyFacts(@Query("email") String email);

    // 25문제 시험
    @GET("questions/genre/25/")
    Call<List<QuestionResponse>> get25Questions(@Query("genre_id") int genreId);

    // 50문제 시험
    @GET("questions/genre/50/")
    Call<List<QuestionResponse>> get50Questions(@Query("genre_id") int genreId);

    // 스피드 퀴즈
    @GET("questions/speed/")
    Call<SpeedQuizResponse> getSpeedQuiz(@Query("genre_id") int genreId);

    // 퀴즈 제출
    @POST("quiz/submit/")
    Call<QuizResultResponse> submitQuizResult(
            @Header("Authorization") String authToken,
            @Body QuizResultRequest request
    );

    // 🔵 최근 퀴즈 결과 가져오기 추가
    @GET("quiz/result/")
    Call<List<QuizResultResponse>> getQuizResults(
            @Header("Authorization") String authHeader
    );

    // ChatGPT API
    @POST("https://api.openai.com/v1/chat/completions")
    Call<ChatResponse> sendChat(
            @Header("Authorization") String authHeader,
            @Header("Content-Type") String contentType,
            @Body ChatRequest request
    );

    @POST("reset-password/")
    Call<ResponseBody> resetPassword(@Body Map<String, String> body);

    @PATCH("profile/update-interests/")
    Call<ResponseBody> updateInterests(
            @Header("Authorization") String authToken,
            @Body Map<String, String> body
    );

}
