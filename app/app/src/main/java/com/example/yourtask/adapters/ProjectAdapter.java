package com.example.yourtask.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.yourtask.CreateProjectFragment;
import com.example.yourtask.ProjectUsersFragment;
import com.example.yourtask.model.ApiRequest;
import com.example.yourtask.model.Progetto;
import com.example.yourtask.R;
import com.example.yourtask.model.ReceiveDataCallback;
import com.example.yourtask.model.RequestResult;

import java.util.ArrayList;

public class ProjectAdapter extends ArrayAdapter<Progetto> {
    private Context context;

    public ProjectAdapter(Context context, int resource, ArrayList<Progetto> objects) {
        super(context, resource, objects);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.project_item, parent, false);
        }

        Progetto project = getItem(position);
        TextView nomeTextView = (TextView) convertView.findViewById(R.id.project_listview_project_name_label);
        TextView startTextView = (TextView) convertView.findViewById(R.id.project_listview_start_date);
        TextView endTextView = (TextView) convertView.findViewById(R.id.project_listview_end_date);
        ImageView optionsIcon = (ImageView) convertView.findViewById(R.id.project_listview_options_icon);

        optionsIcon.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                PopupMenu popupMenu = new PopupMenu(context, v);
                popupMenu.getMenuInflater().inflate(R.menu.project_options_popup_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
                {
                    @Override
                    public boolean onMenuItemClick(MenuItem item)
                    {
                        int id = item.getItemId();

                        if (id == R.id.project_options_popup_menu_delete) {
                            ApiRequest.deleteProgetto(project.id, new ReceiveDataCallback<RequestResult>() {
                                @Override
                                public void receiveData(RequestResult o) {
                                    remove(project);
                                    notifyDataSetChanged();
                                }
                            });
                        }
                        else if (id == R.id.project_options_popup_menu_edit) {
                            Bundle bundle = new Bundle();
                            bundle.putBoolean("edit", true);
                            bundle.putInt("id", project.id);
                            bundle.putString("nome_progetto", project.nome_progetto);
                            bundle.putString("data_avvio", project.data_avvio);
                            bundle.putString("data_scadenza", project.data_scadenza);
                            bundle.putFloat("budget", project.budget);

                            CreateProjectFragment createProject = new CreateProjectFragment();
                            createProject.setArguments(bundle);

                            ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, createProject).commit();
                        }
                        else if (id == R.id.project_options_popup_menu_show_users)
                        {
                            Bundle bundle = new Bundle();
                            bundle.putInt("id", project.id);
                            bundle.putString("nome_progetto", project.nome_progetto);

                            ProjectUsersFragment projectUsers = new ProjectUsersFragment();
                            projectUsers.setArguments(bundle);

                            ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, projectUsers).commit();
                        }

                        return true;
                    }
                });

                popupMenu.show();
            }
        });

        assert project != null;

        nomeTextView.setText(project.nome_progetto);
        startTextView.setText(project.data_avvio);
        endTextView.setText(project.data_scadenza);

        return convertView;
    }
}

