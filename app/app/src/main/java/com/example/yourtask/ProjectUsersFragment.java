package com.example.yourtask;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.yourtask.adapters.CollaboratorsAdapter;
import com.example.yourtask.adapters.UsersAdapter;
import com.example.yourtask.model.ApiRequest;
import com.example.yourtask.model.ReceiveDataCallback;
import com.example.yourtask.model.User;

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

        EditText newCollaboratorsEditText = (EditText)view.findViewById(R.id.project_users_add_collaborators_edittext);
        ListView newCollaboratorsListView = (ListView)view.findViewById(R.id.project_users_add_collaborators_listview);
        ListView collaboratorsListView = (ListView)view.findViewById(R.id.project_users_collaborators_listview);

        ArrayList<User> newCollaborators = new ArrayList<User>();
        CollaboratorsAdapter newCollaboratorsAdapter = new CollaboratorsAdapter(getContext(), newCollaborators);
        newCollaboratorsListView.setAdapter(newCollaboratorsAdapter);

        ArrayList<User> collaborators = new ArrayList<User>();
        UsersAdapter collaboratorsAdapter = new UsersAdapter(getContext(), collaborators);

        ApiRequest.getUtentiProgetto(1, new ReceiveDataCallback<ArrayList<User>>()
        {
            @Override
            public void receiveData(ArrayList<User> o)
            {
                Toast.makeText(getContext(), String.valueOf(o.size()), Toast.LENGTH_LONG).show();
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

                                newCollaboratorsAdapter.notifyDataSetChanged();
                            }
                        });

                        return true;
                    }
                }

                return false;
            }
        });

        return view;
    }
}
