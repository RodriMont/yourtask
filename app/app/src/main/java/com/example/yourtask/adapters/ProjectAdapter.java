package com.example.yourtask.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.yourtask.model.Progetto;
import com.example.yourtask.model.Project;
import com.example.yourtask.R;

import java.util.ArrayList;

public class ProjectAdapter extends ArrayAdapter<Progetto> {
    public ProjectAdapter(Context context, int resource, ArrayList<Progetto> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.project_item, parent, false);
        }

        Progetto project = getItem(position);
        TextView nomeTextView = (TextView) convertView.findViewById(R.id.project_name_textView);
        TextView startTextView = (TextView) convertView.findViewById(R.id.start_project_textView);
        TextView endTextView = (TextView) convertView.findViewById(R.id.end_project_textView);

        assert project != null;

        nomeTextView.setText(project.nome_progetto);
        startTextView.setText(project.data_avvio);
        endTextView.setText(project.data_scadenza);

        return convertView;
    }
}

