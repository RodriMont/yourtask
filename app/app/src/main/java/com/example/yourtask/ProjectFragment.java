package com.example.yourtask;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.yourtask.adapters.TasksAdapter;
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

        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Task(1, "ciao", "01-01-1970", "01-01-1970", 1, 1));
        tasks.add(new Task(1, "ciao", "01-01-1970", "01-01-1970", 1, 1));

        TasksAdapter ta = new TasksAdapter(getContext(), tasks);
        tasks_listview.setAdapter(ta);



        return view;
    }
}