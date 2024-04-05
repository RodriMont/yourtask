package com.example.yourtask;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.yourtask.adapters.ProjectAdapter;
import com.example.yourtask.model.ApiRequest;
import com.example.yourtask.model.Progetto;
import com.example.yourtask.model.Project;
import com.example.yourtask.model.ReceiveDataCallback;

import java.util.ArrayList;

public class HomepageFragment extends Fragment
{
    SharedPreferences sharedPreferences;
    private static final String KEY_EMAIL = "email";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_ID = "id";
    private static final String SHARED_PREF_NAME = "mypref";


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.homepage_fragment, container, false);

        TextView nomeUtente = view.findViewById(R.id.homepage_user_name);

        sharedPreferences = getActivity().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String username = sharedPreferences.getString(KEY_USERNAME,null);
        String email = sharedPreferences.getString(KEY_EMAIL, null);
        if (username != null) {
            nomeUtente.setText(username);
        }

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
        int id = sharedPreferences.getInt(KEY_ID, 0);

        if (id > 0) {
            ApiRequest.getProgettiUtente(id, new ReceiveDataCallback<ArrayList<Progetto>>() {
                @Override
                public void receiveData(ArrayList<Progetto> o) {
                    ProjectAdapter projectArrayAdapter = new ProjectAdapter(getContext(), R.layout.project_item, o);

                    listView.setAdapter(projectArrayAdapter);
                }
            });
        }

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