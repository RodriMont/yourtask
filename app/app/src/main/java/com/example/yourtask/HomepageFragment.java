package com.example.yourtask;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.yourtask.adapters.ProjectAdapter;
import com.example.yourtask.model.ApiRequest;
import com.example.yourtask.model.Progetto;
import com.example.yourtask.model.Project;
import com.example.yourtask.model.ReceiveDataCallback;

import java.util.ArrayList;

public class HomepageFragment extends Fragment
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.homepage_fragment, container, false);

        Button createProjectButton = (Button) view.findViewById(R.id.create_project_button);
        createProjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) getActivity();
                assert mainActivity != null;
                mainActivity.changeFragment(new CreateProjectFragment());
            }
        });

        ApiRequest.getProgettiUtente(1, new ReceiveDataCallback<ArrayList<Progetto>>()
        {
            @Override
            public void receiveData(ArrayList<Progetto> o)
            {
                ProjectAdapter projectArrayAdapter = new ProjectAdapter(getContext() , R.layout.project_item, o);

                ListView listView = view.findViewById(R.id.task_list);
                listView.setAdapter(projectArrayAdapter);
            }
        });

        return view;
    }
}