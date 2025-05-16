package com.example.ulmanaala;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ulmanaala.api.ApiService;
import com.example.ulmanaala.client.RetrofitClient;
import com.example.ulmanaala.model.QuizResult;
import com.example.ulmanaala.QuizResultAdapter;
import com.example.ulmanaala.utils.FileUtils;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class myinfoFragment extends Fragment {

    private ImageView profileImageView;
    private TextView tvNickname, tvEmail, tvInterest;
    private ImageButton btnSettings;
    private Button btnEditInterest;
    private RecyclerView recyclerQuizResults;
    private QuizResultAdapter adapter;
    private List<QuizResult> quizResultList = new ArrayList<>();
    private String accessToken;
    private String username, email, interest1, interest2, interest3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myinfo, container, false);

        profileImageView = view.findViewById(R.id.profile_image);
        tvNickname = view.findViewById(R.id.tv_nickname);
        tvEmail = view.findViewById(R.id.tv_email);
        tvInterest = view.findViewById(R.id.tv_interest);
        btnSettings = view.findViewById(R.id.btn_settings);
        btnEditInterest = view.findViewById(R.id.btn_edit_interest);
        recyclerQuizResults = view.findViewById(R.id.recycler_quiz_results);

        SharedPreferences prefs = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        accessToken = prefs.getString("token", null);
        username = prefs.getString("username", "");
        email = prefs.getString("email", "");
        interest1 = prefs.getString("interest1", "");
        interest2 = prefs.getString("interest2", "");
        interest3 = prefs.getString("interest3", "");

        tvNickname.setText(username);
        tvEmail.setText(email);
        tvInterest.setText("관심 분야: " + joinInterests(interest1, interest2, interest3));

        recyclerQuizResults.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new QuizResultAdapter(quizResultList);
        recyclerQuizResults.setAdapter(adapter);

        btnSettings.setOnClickListener(v -> showSettingsDialog());
        btnEditInterest.setOnClickListener(v -> showInterestDialog());

        fetchQuizResults();

        return view;
    }

    private void fetchQuizResults() {
        ApiService apiService = RetrofitClient.getApiService();
        apiService.getQuizResults("Bearer " + accessToken).enqueue(new Callback<List<QuizResult>>() {
            @Override
            public void onResponse(Call<List<QuizResult>> call, Response<List<QuizResult>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    quizResultList.clear();
                    quizResultList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<QuizResult>> call, Throwable t) {
                Log.e("QUIZ_RESULT", "서버 오류: " + t.getMessage());
            }
        });
    }

    private void showSettingsDialog() {
        final String[] options = {"프로필 이미지 변경", "닉네임 변경", "비밀번호 변경"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("프로필 설정")
                .setItems(options, (dialog, which) -> {
                    if (which == 0) selectProfileImage();
                    else if (which == 1) showNicknameDialog();
                    else if (which == 2) showPasswordDialog();
                })
                .setNegativeButton("취소", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void selectProfileImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "프로필 이미지 선택"), 1001);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001 && resultCode == getActivity().RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();
            String imagePath = FileUtils.getPath(getContext(), selectedImageUri); // 아래 참고

            if (imagePath != null) {
                uploadProfileImage(imagePath);
            } else {
                Toast.makeText(getContext(), "이미지 경로를 찾을 수 없습니다", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void uploadProfileImage(String imagePath) {
        File file = new File(imagePath);
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("profile_image", file.getName(), requestFile);

        ApiService apiService = RetrofitClient.getApiService();
        apiService.uploadProfileImage("Bearer " + accessToken, body).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        // 응답 JSON 파싱
                        String responseBody = response.body().string();
                        JSONObject jsonObject = new JSONObject(responseBody);
                        String imageUrl = jsonObject.getString("profile_image_url");

                        // 전체 URL로 만들기
                        String fullImageUrl = "http://43.200.172.76:8000" + imageUrl;

                        // Glide로 이미지 로딩
                        Glide.with(getContext())
                                .load(fullImageUrl)
                                .placeholder(R.drawable.ic_profile_placeholder)
                                .into(profileImageView);

                        Toast.makeText(getContext(), "이미지 업로드 완료!", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getContext(), "업로드 실패", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), "서버 오류: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String joinInterests(String... interests) {
        StringBuilder builder = new StringBuilder();
        for (String interest : interests) {
            if (interest != null && !interest.isEmpty()) {
                if (builder.length() > 0) builder.append(", ");
                builder.append(getGenreName(interest));
            }
        }
        return builder.toString();
    }

    private String getGenreName(String genreId) {
        switch (genreId) {
            case "1": return "과학";
            case "2": return "역사";
            case "3": return "맞춤법";
            case "4": return "사자성어";
            case "5": return "수도";
            case "6": return "예술";
            case "7": return "기타";
            default: return "알 수 없음";
        }
    }

    private void showNicknameDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("닉네임 변경");

        final EditText input = new EditText(getContext());
        input.setHint("새 닉네임 입력");
        builder.setView(input);

        builder.setPositiveButton("변경", (dialog, which) -> {
            String newNickname = input.getText().toString().trim();

            if (newNickname.isEmpty()) {
                Toast.makeText(getContext(), "닉네임을 입력하세요", Toast.LENGTH_SHORT).show();
            } else if (newNickname.length() > 12) {
                Toast.makeText(getContext(), "닉네임은 12자 이하로 입력해주세요", Toast.LENGTH_SHORT).show();
            } else {
                updateNickname(newNickname);
            }
        });

        builder.setNegativeButton("취소", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    private void updateNickname(String newNickname) {
        ApiService apiService = RetrofitClient.getApiService();
        Map<String, String> body = new HashMap<>();
        body.put("username", newNickname);

        apiService.updateNickname("Bearer " + accessToken, body).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    tvNickname.setText(newNickname);
                    SharedPreferences prefs = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("username", newNickname);
                    editor.apply();
                    Toast.makeText(getContext(), "닉네임이 변경되었습니다", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "닉네임 변경 실패", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), "서버 오류: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showPasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("비밀번호 변경");
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_change_password, null);
        EditText etNewPassword = dialogView.findViewById(R.id.et_new_password);
        EditText etConfirmPassword = dialogView.findViewById(R.id.et_confirm_password);
        builder.setView(dialogView);

        builder.setPositiveButton("변경", (dialog, which) -> {
            String newPw = etNewPassword.getText().toString().trim();
            String confirmPw = etConfirmPassword.getText().toString().trim();

            if (newPw.isEmpty() || confirmPw.isEmpty()) {
                Toast.makeText(getContext(), "모든 항목을 입력해주세요", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!newPw.equals(confirmPw)) {
                Toast.makeText(getContext(), "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                return;
            }
            if (newPw.length() < 8) {
                Toast.makeText(getContext(), "비밀번호는 최소 8자 이상이어야 합니다", Toast.LENGTH_SHORT).show();
                return;
            }
            updatePassword(newPw);
        });

        builder.setNegativeButton("취소", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    private void updatePassword(String newPassword) {
        ApiService apiService = RetrofitClient.getApiService();
        Map<String, String> body = new HashMap<>();
        body.put("email", email);
        body.put("new_password", newPassword);

        apiService.resetPassword(body).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "비밀번호가 성공적으로 변경되었습니다", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        String errorMsg = response.errorBody().string();
                        Log.e("PW_CHANGE", "서버 응답 오류: " + errorMsg);
                        JSONObject obj = new JSONObject(errorMsg);
                        if (obj.has("new_password")) {
                            String msg = obj.getJSONArray("new_password").getString(0);
                            Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getContext(), "비밀번호 변경 실패", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(getContext(), "비밀번호 변경 실패", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), "서버 오류: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showInterestDialog() {
        String[] genreNames = {"과학", "역사", "맞춤법", "사자성어", "수도", "예술", "기타"};
        String[] genreIds = {"1", "2", "3", "4", "5", "6", "7"};
        boolean[] checkedItems = new boolean[genreNames.length];
        List<String> selectedGenres = new ArrayList<>();

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("관심 분야 선택 (최대 3개)");
        builder.setMultiChoiceItems(genreNames, checkedItems, (dialog, indexSelected, isChecked) -> {
            String selectedId = genreIds[indexSelected];
            if (isChecked) {
                if (selectedGenres.size() >= 3) {
                    Toast.makeText(getContext(), "최대 3개까지 선택할 수 있습니다", Toast.LENGTH_SHORT).show();
                    ((AlertDialog) dialog).getListView().setItemChecked(indexSelected, false);
                } else {
                    selectedGenres.add(selectedId);
                }
            } else {
                selectedGenres.remove(selectedId);
            }
        });

        builder.setPositiveButton("확인", (dialog, id) -> updateInterests(selectedGenres));
        builder.setNegativeButton("취소", (dialog, id) -> dialog.dismiss());
        builder.show();
    }

    private void updateInterests(List<String> selected) {
        ApiService apiService = RetrofitClient.getApiService();
        Map<String, String> body = new HashMap<>();
        if (selected.size() > 0) body.put("interest_1", selected.get(0));
        if (selected.size() > 1) body.put("interest_2", selected.get(1));
        if (selected.size() > 2) body.put("interest_3", selected.get(2));

        apiService.updateInterests("Bearer " + accessToken, body).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    SharedPreferences prefs = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    if (selected.size() > 0) editor.putString("interest1", selected.get(0));
                    if (selected.size() > 1) editor.putString("interest2", selected.get(1));
                    if (selected.size() > 2) editor.putString("interest3", selected.get(2));
                    editor.apply();

                    interest1 = selected.size() > 0 ? selected.get(0) : "";
                    interest2 = selected.size() > 1 ? selected.get(1) : "";
                    interest3 = selected.size() > 2 ? selected.get(2) : "";
                    tvInterest.setText("관심 분야: " + joinInterests(interest1, interest2, interest3));

                    Toast.makeText(getContext(), "관심 분야가 변경되었습니다", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "관심 분야 변경 실패", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), "서버 오류: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}