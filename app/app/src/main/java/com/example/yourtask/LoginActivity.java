package com.example.yourtask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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


    SharedPreferences sharedPreferences;
    EditText username;
    EditText email;
    EditText password;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        EditText emailEditText = findViewById(R.id.login_email_edittext);
        EditText passwordEditText = findViewById(R.id.login_password_edittext);

        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);

        int id = sharedPreferences.getInt("id", 0);

        if (id != 0) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            return;
        }

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

                SharedPreferences.Editor editor = sharedPreferences.edit();

                if (emailText.equals("") || passwordText.equals(""))
                    Toast.makeText(LoginActivity.this, "Campo obbligatorio", Toast.LENGTH_LONG).show();
                else {
                    ApiRequest.getUtente(emailText, new ReceiveDataCallback<ArrayList<User>>() {
                        @Override
                        public void receiveData(ArrayList<User> utenti) {
                            if (utenti.size() > 0) {
                                User utente = utenti.get(0);
                                if (utente.password.equals(passwordText)) {
                                    editor.putInt("id", utente.id);
                                    editor.putString("username", utente.username);
                                    editor.putString("email", utente.email);
                                    editor.putString("password", utente.password);
                                    editor.apply();

                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                }
                                else {
                                    Toast.makeText(LoginActivity.this, "Password errata", Toast.LENGTH_LONG).show();
                                }
                            }
                            else
                                Toast.makeText(LoginActivity.this, "L'email inserita non esiste", Toast.LENGTH_LONG);
                        }
                    });
                }

            }
        });
    }
}