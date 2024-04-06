package com.example.yourtask;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.yourtask.adapters.TasksAdapter;
import com.example.yourtask.model.ApiRequest;
import com.example.yourtask.model.Progetto;
import com.example.yourtask.model.ReceiveDataCallback;
import com.example.yourtask.model.Task;

import java.util.ArrayList;

public class ProjectFragment extends Fragment
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.fragment_project, container, false);
        LinearLayout addTask = view.findViewById(R.id.project_new_task_layout);
        LinearLayout addRole = view.findViewById(R.id.project_new_role_layout);
        ListView tasks_listview = (ListView)view.findViewById(R.id.project_tasks_listview);

        Bundle bundle = getArguments();

        SharedPreferences sharedPreferences;
        final String SHARED_PREF_NAME = "mypref";
        sharedPreferences = getActivity().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String KEY_ID = "id";
        int id = sharedPreferences.getInt(KEY_ID, 0);

        TextView progetto = view.findViewById(R.id.project_title);

        if (id > 0) {
            progetto.setText(bundle.getString("nome_progetto", "nome_progetto"));
            ApiRequest.getTaskUtente(id, bundle.getInt("id"), new ReceiveDataCallback<ArrayList<Task>>()
            {
                @Override
                public void receiveData(ArrayList<Task> o)
                {
                    TasksAdapter tasks_adapter = new TasksAdapter(getContext(), o, bundle.getString("nome_progetto"));
                    tasks_listview.setAdapter(tasks_adapter);
                }
            });
        }

        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("id_progetto", bundle.getInt("id"));
                bundle.putString("nome_progetto", bundle.getString("nome_progetto"));

                CreateTaskFragment createTaskFragment = new CreateTaskFragment();
                createTaskFragment.setArguments(bundle);

                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, createTaskFragment).commit();
            }
        });

        addRole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CreateRoleFragment()).commit();
            }
        });

        return view;
    }
}