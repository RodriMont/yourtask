package com.example.yourtask;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.yourtask.adapters.TasksAdapter;
import com.example.yourtask.model.ApiRequest;
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

        ListView tasks_listview = (ListView)view.findViewById(R.id.project_in_tasks_listview);

        ApiRequest.getTaskUtente(1, 1, new ReceiveDataCallback<ArrayList<Task>>()
        {
            @Override
            public void receiveData(ArrayList<Task> o)
            {
                TasksAdapter tasks_adapter = new TasksAdapter(getContext(), o);
                tasks_listview.setAdapter(tasks_adapter);
            }
        });

        tasks_listview.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Task item = (Task)parent.getItemAtPosition(position);

                Bundle bundle = new Bundle();
                bundle.putBoolean("#EDIT", true);
                bundle.putInt("id", item.id);
                bundle.putString("nome_task", item.nome_task);
                bundle.putString("data_avvio", item.data_avvio);
                bundle.putString("data_scadenza", item.data_scadenza);
                bundle.putInt("priorita", item.priorita);
                bundle.putInt("id_progetto", item.id_progetto);

                CreateTaskFragment createTaskFragment = new CreateTaskFragment();
                createTaskFragment.setArguments(bundle);

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, createTaskFragment)
                        .commit();
            }
        });

        return view;
    }
}