package com.example.yourtask;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.yourtask.model.ApiRequest;
import com.example.yourtask.model.ReceiveDataCallback;
import com.example.yourtask.model.RequestResult;
import com.example.yourtask.model.User;

public class ProfileFragment extends Fragment
{

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("login", Context.MODE_PRIVATE);

        Button modificaProfilo = view.findViewById(R.id.apply_button);

        EditText modificaUsernameEditText = view.findViewById(R.id.profile_username_edittext);
        modificaUsernameEditText.setText(sharedPreferences.getString("username", ""));

        modificaProfilo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String modificaUsername = modificaUsernameEditText.getText().toString();

                ApiRequest.putUtente(sharedPreferences.getInt("id", 0), new User(1, modificaUsername, "", ""), new ReceiveDataCallback<RequestResult>() {
                    @Override
                    public void receiveData(RequestResult o) {
                        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomepageFragment()).commit();
                        sharedPreferences.edit().putString("username", modificaUsername).commit();
                    }
                });
            }
        });

        return view;
    }

}