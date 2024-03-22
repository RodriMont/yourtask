package com.example.yourtask;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yourtask.R;

public class LoginActivity extends AppCompatActivity {

    EditText username;
    EditText password;
    Button loginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        username = findViewById(R.id.signup_username_edittext);
        password = findViewById(R.id.signup_password_edittext);
        loginButton = findViewById(R.id.signup_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().equals("Admin")&&password.getText().toString().equals("Admin")){
                    Toast.makeText(LoginActivity.this, "Login Successo!!", Toast.LENGTH_SHORT).show();
                } else  {
                    Toast.makeText(LoginActivity.this, "Login Fallito!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}