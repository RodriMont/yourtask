package com.example.yourtask;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
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

        LinearLayout createProjectButton = (LinearLayout) view.findViewById(R.id.homepage_create_project_button);
        createProjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) getActivity();
                assert mainActivity != null;
                mainActivity.changeFragment(new CreateProjectFragment());
            }
        });

        ListView listView = view.findViewById(R.id.homepage_projects_listview);
        ApiRequest.getProgettiUtente(1, new ReceiveDataCallback<ArrayList<Progetto>>()
        {
            @Override
            public void receiveData(ArrayList<Progetto> o)
            {
                ProjectAdapter projectArrayAdapter = new ProjectAdapter(getContext() , R.layout.project_item, o);

                listView.setAdapter(projectArrayAdapter);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Progetto progetto = (Progetto) parent.getItemAtPosition(position);

                Bundle bundle = new Bundle();
                bundle.putInt("id", progetto.id);

                ProjectFragment project = new ProjectFragment();
                project.setArguments(bundle);

                getFragmentManager().beginTransaction().replace(R.id.fragment_container, project).commit();
            }
        });

        return view;
    }
}