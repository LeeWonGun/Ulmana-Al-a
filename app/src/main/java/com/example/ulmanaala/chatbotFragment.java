package com.example.ulmanaala;

import android.graphics.Color;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chatbot, container, false);

        userInput = view.findViewById(R.id.userInput);
        submitButton = view.findViewById(R.id.submitButton);
        chatLayout = view.findViewById(R.id.chatLayout);
        chatScrollView = view.findViewById(R.id.chatScrollView);

        submitButton.setOnClickListener(v -> {
            String question = userInput.getText().toString().trim();
            if (!question.isEmpty()) {
                addChatBubble("나: " + question, true);
                sendToChatGPT(question);
                userInput.setText("");
            }
        });

        return view;
    }

    private void sendToChatGPT(String userMessage) {
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
                    Log.d("GPT_RESPONSE", "답변 수신: " + reply);
                    addChatBubble("GPT: " + reply, false);
                } else {
                    addChatBubble("GPT 응답 실패: " + response.code(), false);
                    Log.e("GPT_ERROR", "코드: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ChatResponse> call, Throwable t) {
                addChatBubble("GPT 요청 오류: " + t.getMessage(), false);
                Log.e("GPT_ERROR", "통신 실패: " + t.getMessage());
            }
        });
    }

    private void addChatBubble(String text, boolean isUser) {
        if (getContext() == null) return;

        TextView textView = new TextView(getContext());
        textView.setText(text);
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(15);
        textView.setPadding(20, 15, 20, 15);

        if (isUser) {
            textView.setBackgroundResource(R.drawable.user_chat_bubble);
        } else {
            textView.setBackgroundResource(R.drawable.gpt_chat_bubble);
        }

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(10, 10, 10, 10);
        textView.setLayoutParams(params);

        chatLayout.addView(textView);
        chatLayout.invalidate();

        chatScrollView.post(() -> chatScrollView.fullScroll(View.FOCUS_DOWN));
    }
}
