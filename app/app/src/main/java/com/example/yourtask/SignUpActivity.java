package com.example.yourtask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yourtask.model.ApiRequest;
import com.example.yourtask.model.ReceiveDataCallback;
import com.example.yourtask.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getSupportActionBar().hide();

        EditText userEditText = findViewById(R.id.signup_username_edittext);
        EditText emailEditText = findViewById(R.id.signup_email_edittext);
        EditText passwordEditText = findViewById(R.id.signup_password_edittext);
        EditText repasswordEditText = findViewById(R.id.signup_repeat_password_edittext);

        Button registra = findViewById(R.id.signup_button);
        TextView login = findViewById(R.id.signup_arleady_signed_button);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });

        registra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userText = userEditText.getText().toString();
                String emailText = emailEditText.getText().toString();
                String passwordText = passwordEditText.getText().toString();
                String repasswordText = repasswordEditText.getText().toString();

                if (userText.equals("") || emailText.equals("") || passwordText.equals("") || repasswordText.equals(""))
                    Toast.makeText(SignUpActivity.this, "Campo obbligatorio", Toast.LENGTH_LONG).show();
                else if (!passwordText.equals(repasswordText))
                    Toast.makeText(SignUpActivity.this, "Le password non coincidono", Toast.LENGTH_LONG).show();
                else {
                    ApiRequest.postUtente(new User(1, userText, emailText, passwordText), new ReceiveDataCallback<Integer>() {
                        @Override
                        public void receiveData(Integer o) {
                            if (o == 200) {
                                Toast.makeText(SignUpActivity.this, "200", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                intent.putExtra("reg", true);
                                startActivity(intent);
                            }
                            else if (o == 400)
                                Toast.makeText(SignUpActivity.this, "400", Toast.LENGTH_LONG).show();
                            else if (o == 500)
                                Toast.makeText(SignUpActivity.this, "500", Toast.LENGTH_LONG).show();
                        }
                    });
                }

            }
        });



    }
}