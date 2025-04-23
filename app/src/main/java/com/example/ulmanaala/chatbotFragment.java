package com.example.ulmanaala;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.ulmanaala.api.ApiService;
import com.example.ulmanaala.client.RetrofitClient;
import com.example.ulmanaala.request.ChatRequest;
import com.example.ulmanaala.response.ChatResponse;
import com.example.ulmanaala.BuildConfig;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class chatbotFragment extends Fragment {

    private EditText userInput;
    private ImageButton submitButton;
    private LinearLayout chatLayout;
    private ScrollView chatScrollView;

    public chatbotFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chatbot, container, false);

        // 바인딩
        userInput = view.findViewById(R.id.userInput);
        submitButton = view.findViewById(R.id.submitButton);
        chatLayout = view.findViewById(R.id.chatLayout);
        chatScrollView = view.findViewById(R.id.chatScrollView);

        // 버튼 클릭 시 실행
        submitButton.setOnClickListener(v -> {
            String question = userInput.getText().toString().trim();
            if (!question.isEmpty()) {
                addChatBubble("나: " + question, true); // 내 질문 화면에 표시

                sendToChatGPT(question); // GPT에게 질문 보내기
                userInput.setText(""); // 입력창 비우기
            }
        });

        return view;
    }

    // 질문 전송 함수
    private void sendToChatGPT(String userMessage) {

        Log.d("DEBUG_KEY", "Authorization Header: Bearer " + BuildConfig.OPENAI_API_KEY);



        List<ChatRequest.Message> messages = new ArrayList<>();
        messages.add(new ChatRequest.Message("user", userMessage));

        ChatRequest request = new ChatRequest(messages);

        ApiService apiService = RetrofitClient.getChatApiService();
        apiService.sendChat(
                "Bearer " + BuildConfig.OPENAI_API_KEY,
                "application/json",
                request
        ).enqueue(new Callback<ChatResponse>() {
            @Override
            public void onResponse(Call<ChatResponse> call, Response<ChatResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String reply = response.body().getChoices().get(0).getMessage().getContent();
                    addChatBubble("GPT: " + reply, false);
                } else {
                    addChatBubble("GPT 응답 실패: " + response.code(), false);
                }
            }

            @Override
            public void onFailure(Call<ChatResponse> call, Throwable t) {
                addChatBubble("GPT 요청 오류: " + t.getMessage(), false);
            }
        });
    }

    // 채팅 말풍선 추가 함수
    private void addChatBubble(String text, boolean isUser) {
        TextView textView = new TextView(getContext());
        textView.setText(text);
        textView.setPadding(20, 15, 20, 15);
        textView.setTextSize(15);

        if (isUser) {
            textView.setBackgroundResource(R.drawable.user_chat_bubble); // 사용자의 말풍선 배경
        } else {
            textView.setBackgroundResource(R.drawable.gpt_chat_bubble); // GPT의 말풍선 배경
        }

        chatLayout.addView(textView);
        chatScrollView.post(() -> chatScrollView.fullScroll(View.FOCUS_DOWN)); // 아래로 스크롤
    }
}
