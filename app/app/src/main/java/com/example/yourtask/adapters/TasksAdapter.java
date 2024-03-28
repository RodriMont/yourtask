package com.example.yourtask.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.yourtask.R;
import com.example.yourtask.model.Task;

import java.util.ArrayList;

public class TasksAdapter extends ArrayAdapter<Task>
{
    private Context context;

    private class ViewHolder
    {
        public TextView task_name_label;
        public ImageView priority_icon;
        public ImageView options_icon;
        public TextView start_date_label;
        public TextView end_date_label;
        public ImageView start_date_calendar_icon;
        public TextView start_date;
        public ImageView end_date_calendar_icon;
        public TextView end_date;
    }

    public TasksAdapter(Context context, ArrayList<Task> tasks)
    {
        super(context, R.layout.tasks_listview, tasks);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder = null;

        if (convertView == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.tasks_listview, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.task_name_label = convertView.findViewById(R.id.tasks_listview_task_name_label);
            viewHolder.priority_icon = convertView.findViewById(R.id.tasks_listview_priority_icon);
            viewHolder.options_icon = convertView.findViewById(R.id.tasks_listview_options_icon);
            viewHolder.start_date_label = convertView.findViewById(R.id.tasks_listview_start_date_label);
            viewHolder.end_date_label = convertView.findViewById(R.id.tasks_listview_end_date_label);
            viewHolder.start_date_calendar_icon = convertView.findViewById(R.id.tasks_listview_start_date_calendar_icon);
            viewHolder.start_date = convertView.findViewById(R.id.tasks_listview_start_date);
            viewHolder.end_date_calendar_icon = convertView.findViewById(R.id.tasks_listview_end_date_calendar_icon);
            viewHolder.end_date = convertView.findViewById(R.id.tasks_listview_end_date);

            convertView.setTag(viewHolder);
        }
        else
            viewHolder = (ViewHolder)convertView.getTag();

        Task item = getItem(position);

        viewHolder.task_name_label.setText(item.nome_task);
        viewHolder.start_date.setText(item.data_avvio);
        viewHolder.end_date.setText(item.data_scadenza);

        return convertView;
    }
}
