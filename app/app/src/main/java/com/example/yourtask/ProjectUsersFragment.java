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

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.yourtask.adapters.CollaboratorsAdapter;
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

        ArrayList<User> collaborators = new ArrayList<User>();
        CollaboratorsAdapter collaboratorsAdapter = new CollaboratorsAdapter(getContext(), collaborators);

        EditText collaboratorsEditText = (EditText)view.findViewById(R.id.project_users_add_collaborators_edittext);
        ListView collaboratorsListView = (ListView)view.findViewById(R.id.project_users_add_collaborators_listview);

        collaboratorsListView.setAdapter(collaboratorsAdapter);

        collaboratorsEditText.setOnKeyListener(new View.OnKeyListener()
        {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    String item = collaboratorsEditText.getText().toString();

                    if (!item.trim().equals(""))
                    {
                        collaboratorsEditText.setText("");

                        ApiRequest.getUtente(item, new ReceiveDataCallback<ArrayList<User>>()
                        {
                            @Override
                            public void receiveData(ArrayList<User> o)
                            {
                                if (o.size() == 0)
                                    collaborators.add(new User(-1, "", item, ""));
                                else
                                    collaborators.add(o.get(0));

                                collaboratorsAdapter.notifyDataSetChanged();
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
