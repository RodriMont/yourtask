package com.example.yourtask;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.yourtask.R;
import com.example.yourtask.adapters.CollaboratorsAdapter;
import com.example.yourtask.adapters.ProjectUsersAdapter;
import com.example.yourtask.adapters.TaskUsersAdapter;
import com.example.yourtask.model.ApiRequest;
import com.example.yourtask.model.ReceiveDataCallback;
import com.example.yourtask.model.RequestResult;
import com.example.yourtask.model.User;
import com.example.yourtask.model.UtentiProgetto;
import com.example.yourtask.model.UtentiTask;

import java.util.ArrayList;

public class TaskUsersFragment extends Fragment
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.task_users_fragment, container, false);

        Bundle bundle = getArguments();

        int id_task = bundle.getInt("id");
        int id_progetto = bundle.getInt("id_progetto");
        String nome_task = bundle.getString("nome_task");

        TextView projectName = (TextView)view.findViewById(R.id.task_users_name_title);
        projectName.setText(nome_task);

        AutoCompleteTextView newCollaboratorsAutocomplete = (AutoCompleteTextView)view.findViewById(R.id.task_users_add_collaborators_autocomplete);
        ListView newCollaboratorsListView = (ListView)view.findViewById(R.id.task_users_add_collaborators_listview);
        ListView collaboratorsListView = (ListView)view.findViewById(R.id.task_users_collaborators_listview);
        Button collaboratorsButton = (Button)view.findViewById(R.id.task_users_collaborators_listview_button);

        ArrayList<User> newCollaborators = new ArrayList<User>();
        CollaboratorsAdapter newCollaboratorsAdapter = new CollaboratorsAdapter(getContext(), newCollaborators, newCollaboratorsListView);
        newCollaboratorsListView.setAdapter(newCollaboratorsAdapter);

        ArrayList<User> collaborators = new ArrayList<User>();
        TaskUsersAdapter collaboratorsAdapter = new TaskUsersAdapter(getContext(), collaborators, id_task);

        ApiRequest.getUtentiTask(id_task, new ReceiveDataCallback<ArrayList<User>>()
        {
            @Override
            public void receiveData(ArrayList<User> o)
            {
                collaborators.addAll(o);
                collaboratorsListView.setAdapter(collaboratorsAdapter);

                ApiRequest.getUtentiProgetto(id_progetto, new ReceiveDataCallback<ArrayList<User>>() {
                    @Override
                    public void receiveData(ArrayList<User> o)
                    {
                        ArrayList<String> userEmails = new ArrayList<>();

                        for (User user : o)
                            userEmails.add(user.email);

                        newCollaboratorsAutocomplete.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, userEmails));
                    }
                });
            }
        });

        newCollaboratorsAutocomplete.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                String item = newCollaboratorsAutocomplete.getText().toString();

                if (!item.trim().equals(""))
                {
                    newCollaboratorsAutocomplete.setText("");

                    ApiRequest.getUtente(item, new ReceiveDataCallback<ArrayList<User>>()
                    {
                        @Override
                        public void receiveData(ArrayList<User> o)
                        {
                            newCollaborators.add(o.get(0));

                            if (newCollaborators.size() > 3)
                            {
                                ViewGroup.LayoutParams dimension = newCollaboratorsListView.getLayoutParams();
                                dimension.height = newCollaboratorsListView.getHeight();
                                newCollaboratorsListView.setLayoutParams(dimension);
                            }

                            newCollaboratorsAdapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        });

        collaboratorsButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ArrayList<UtentiTask> utentiTask = new ArrayList<>();

                for (int i = 0; i < newCollaborators.size(); i++)
                {
                    User user = newCollaborators.get(i);

                    if (user.id != -1)
                    {
                        utentiTask.add(new UtentiTask(id_task, user.id));
                        collaborators.add(user);
                    }
                }

                ApiRequest.postUtentiTask(utentiTask, new ReceiveDataCallback<RequestResult>()
                {
                    @Override
                    public void receiveData(RequestResult o)
                    {
                        if (o.code == 500)
                            Toast.makeText(getContext(), "Uno o più utenti è già inserito in questo task", Toast.LENGTH_LONG).show();
                        else
                        {
                            collaboratorsAdapter.notifyDataSetChanged();
                            newCollaboratorsAdapter.clear();
                            newCollaboratorsAdapter.notifyDataSetChanged();

                            ViewGroup.LayoutParams dimension = newCollaboratorsListView.getLayoutParams();
                            dimension.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                            newCollaboratorsListView.setLayoutParams(dimension);
                        }
                    }
                });
            }
        });

        return view;
    }
}
