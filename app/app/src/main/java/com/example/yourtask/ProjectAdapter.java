package com.example.yourtask;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class ProjectAdapter extends ArrayAdapter<Project> {
    public ProjectAdapter(Context context, int resource, ArrayList<Project> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.project_item, parent, false);
        }

        Project project = getItem(position);
        TextView nomeTextView = (TextView) convertView.findViewById(R.id.project_name_textView);
        TextView startTextView = (TextView) convertView.findViewById(R.id.start_project_textView);
        TextView endTextView = (TextView) convertView.findViewById(R.id.end_project_textView);

        assert project != null;

        nomeTextView.setText(project.nomeProgetto);
        startTextView.setText(project.dataAvvio);
        endTextView.setText(project.dataScadenza);

        return convertView;
    }
}

