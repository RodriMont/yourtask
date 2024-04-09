package com.example.yourtask;

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

        Button modificaProfilo = view.findViewById(R.id.apply_button);

        EditText modificaUsernameEditText = view.findViewById(R.id.profile_username_edittext);

        modificaProfilo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String modificaUsername = modificaUsernameEditText.getText().toString();

                ApiRequest.putUtente(1, new User(1, modificaUsername, "", ""), new ReceiveDataCallback<RequestResult>() {
                    @Override
                    public void receiveData(RequestResult o) {
                        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomepageFragment()).commit();

                    }
                });
            }
        });

        return view;
    }

}