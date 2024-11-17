package com.example.securepark;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.securepark.api.ApiService;
import com.example.securepark.Retrofit.RetrofitClient;
import com.example.securepark.models.ApiResponse;
import com.example.securepark.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText email, password, confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirm_password);
        Button signupButton = findViewById(R.id.signup_button);

        ApiService apiService = RetrofitClient.getClient("http://10.0.2.2:8000/").create(ApiService.class);

        signupButton.setOnClickListener(view -> {
            String emailInput = email.getText().toString();
            String passwordInput = password.getText().toString();
            String confirmPasswordInput = confirmPassword.getText().toString();

            if (!passwordInput.equals(confirmPasswordInput)) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            User user = new User();
            user.setEmail(emailInput);
            user.setPassword(passwordInput);
            user.setConfirm_password(confirmPasswordInput);
            apiService.signup(user).enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "Signup successful", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Signup failed", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
