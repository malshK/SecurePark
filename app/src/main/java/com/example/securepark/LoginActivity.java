package com.example.securepark;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.securepark.Retrofit.RetrofitClient;
import com.example.securepark.api.ApiService;
import com.example.securepark.models.ApiResponse;
import com.example.securepark.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText emailField = findViewById(R.id.email);
        EditText passwordField = findViewById(R.id.password);
        Button loginButton = findViewById(R.id.login_button); // Updated to reflect a login button

        // Create an instance of the API service
        ApiService apiService = RetrofitClient.getClient("http://10.0.2.2:8000/").create(ApiService.class);

        loginButton.setOnClickListener(v -> {
            String email = emailField.getText().toString();
            String password = passwordField.getText().toString();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
                return;
            }

            // Create a User object with the email and password
            User user = new User();
            user.setEmail(email);
            user.setPassword(password);

            // Call the login API
            apiService.login(user).enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                    } else {
                        // Handle unsuccessful responses
                        String errorMessage = "Error: " + response.code() + " - " + response.message();
                        try {
                            if (response.errorBody() != null) {
                                errorMessage += "\n" + response.errorBody().string();
                            }
                        } catch (Exception e) {
                            errorMessage += "\nError reading response body";
                        }
                        Log.e("LoginError", errorMessage);
                        Toast.makeText(LoginActivity.this, "Login failed: " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    // Handle network or other errors
                    Toast.makeText(LoginActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("LoginFailure", "Request failed", t);
                }
            });
            TextView signupLink = findViewById(R.id.sign_up);

            signupLink.setOnClickListener(V -> {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class); // Replace SignupActivity with your actual signup activity class
                startActivity(intent);
            });
        });
    }
}
