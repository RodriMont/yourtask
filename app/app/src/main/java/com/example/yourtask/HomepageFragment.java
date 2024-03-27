package com.example.yourtask;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.yourtask.adapters.ProjectAdapter;
import com.example.yourtask.model.Project;

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

        ArrayList< Project > arrPorject = createProjectArray();

        ProjectAdapter projectArrayAdapter = new ProjectAdapter(getContext() , R.layout.project_item, arrPorject );

        ListView listView = view.findViewById(R.id.task_list);
        listView.setAdapter(projectArrayAdapter);



        return view;
    }

    public ArrayList< Project> createProjectArray(){
        ArrayList<Project> arr = new ArrayList<>();

        Project p1 = new Project(1, "Task 1", "2023-01-10", "2023-01-15", 1360.2);
        Project p2 = new Project(2, "Task 2", "2023-01-12", "2023-01-20", 13500);
        Project p3 = new Project(3, "Task 3", "2023-01-14", "2023-01-18", 1800.3);

        arr.add(p1);
        arr.add(p2);
        arr.add(p3);

        return arr;



    }
}