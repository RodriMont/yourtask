package com.example.yourtask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yourtask.R;
import com.example.yourtask.model.ApiRequest;
import com.example.yourtask.model.ReceiveDataCallback;
import com.example.yourtask.model.User;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    EditText username;
    EditText password;
    Button loginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        EditText emailEditText = findViewById(R.id.login_email_edittext);
        EditText passwordEditText = findViewById(R.id.login_password_edittext);

        TextView regist = findViewById(R.id.login_not_signed_button);
        Button login = findViewById(R.id.login_button);

        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailText = emailEditText.getText().toString();
                String passwordText = passwordEditText.getText().toString();

                if (emailText.equals("") || passwordText.equals(""))
                    Toast.makeText(LoginActivity.this, "Campo obbligatorio", Toast.LENGTH_LONG).show();
                else {
                    ApiRequest.postLogin(new User(0, null, emailText, passwordText), new ReceiveDataCallback<Integer>() {
                        @Override
                        public void receiveData(Integer o) {
                            if (o == 200) {
                                Toast.makeText(LoginActivity.this, "200", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("reg", true);
                                startActivity(intent);
                            }
                            else if (o == 400)
                                Toast.makeText(LoginActivity.this, "400", Toast.LENGTH_LONG).show();
                            else if (o == 500)
                                Toast.makeText(LoginActivity.this, "500", Toast.LENGTH_LONG).show();
                        }
                    });
                }

            }
        });
    }

}