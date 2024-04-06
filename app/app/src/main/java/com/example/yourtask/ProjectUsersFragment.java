package com.example.yourtask;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.yourtask.adapters.CollaboratorsAdapter;
import com.example.yourtask.adapters.UsersAdapter;
import com.example.yourtask.model.ApiRequest;
import com.example.yourtask.model.ReceiveDataCallback;
import com.example.yourtask.model.RequestResult;
import com.example.yourtask.model.User;
import com.example.yourtask.model.UtentiProgetto;

import java.util.ArrayList;

public class ProjectUsersFragment extends Fragment
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.project_users_fragment, container, false);

        Bundle bundle = getArguments();

        int id_progetto = bundle.getInt("id");
        String nome_progetto = bundle.getString("nome_progetto");

        TextView projectName = (TextView)view.findViewById(R.id.project_users_name_title);
        projectName.setText(nome_progetto);

        EditText newCollaboratorsEditText = (EditText)view.findViewById(R.id.project_users_add_collaborators_edittext);
        ListView newCollaboratorsListView = (ListView)view.findViewById(R.id.project_users_add_collaborators_listview);
        ListView collaboratorsListView = (ListView)view.findViewById(R.id.project_users_collaborators_listview);
        Button collaboratorsButton = (Button)view.findViewById(R.id.project_users_collaborators_listview_button);

        ArrayList<User> newCollaborators = new ArrayList<User>();
        CollaboratorsAdapter newCollaboratorsAdapter = new CollaboratorsAdapter(getContext(), newCollaborators, newCollaboratorsListView);
        newCollaboratorsListView.setAdapter(newCollaboratorsAdapter);

        ArrayList<User> collaborators = new ArrayList<User>();
        UsersAdapter collaboratorsAdapter = new UsersAdapter(getContext(), collaborators);

        ApiRequest.getUtentiProgetto(id_progetto, new ReceiveDataCallback<ArrayList<User>>()
        {
            @Override
            public void receiveData(ArrayList<User> o)
            {
                collaborators.addAll(o);
                collaboratorsListView.setAdapter(collaboratorsAdapter);
            }
        });

        newCollaboratorsEditText.setOnKeyListener(new View.OnKeyListener()
        {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    String item = newCollaboratorsEditText.getText().toString();

                    if (!item.trim().equals(""))
                    {
                        newCollaboratorsEditText.setText("");

                        ApiRequest.getUtente(item, new ReceiveDataCallback<ArrayList<User>>()
                        {
                            @Override
                            public void receiveData(ArrayList<User> o)
                            {
                                if (o.size() == 0)
                                    newCollaborators.add(new User(-1, "", item, ""));
                                else
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

                        return true;
                    }
                }

                return false;
            }
        });

        collaboratorsButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ArrayList<UtentiProgetto> utentiProgetto = new ArrayList<>();

                for (int i = 0; i < newCollaborators.size(); i++)
                {
                    User user = newCollaborators.get(i);

                    if (user.id != -1)
                        utentiProgetto.add(new UtentiProgetto(user.id, id_progetto));
                }

                ApiRequest.postUtentiProgetto(utentiProgetto, new ReceiveDataCallback<RequestResult>()
                {
                    @Override
                    public void receiveData(RequestResult o)
                    {
                        Toast.makeText(getContext(), "200", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        return view;
    }
}
