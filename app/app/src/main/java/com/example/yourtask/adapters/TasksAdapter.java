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

import androidx.appcompat.app.AppCompatActivity;

import com.example.yourtask.CreateTaskFragment;
import com.example.yourtask.R;
import com.example.yourtask.TaskUsersFragment;
import com.example.yourtask.model.ApiRequest;
import com.example.yourtask.model.ReceiveDataCallback;
import com.example.yourtask.model.RequestResult;
import com.example.yourtask.model.Task;

import java.util.ArrayList;

public class TasksAdapter extends ArrayAdapter<Task>
{
    private Context context;
    private String nome_progetto;

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

    public TasksAdapter(Context context, ArrayList<Task> tasks, String nome_progetto)
    {
        super(context, R.layout.tasks_listview, tasks);
        this.context = context;
        this.nome_progetto = nome_progetto;
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

        Task task = getItem(position);

        viewHolder.task_name_label.setText(task.nome_task);
        viewHolder.start_date.setText(task.data_avvio);
        viewHolder.end_date.setText(task.data_scadenza);
        viewHolder.options_icon.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                PopupMenu popupMenu = new PopupMenu(context, v);
                popupMenu.getMenuInflater().inflate(R.menu.task_options_popup_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
                {
                    @Override
                    public boolean onMenuItemClick(MenuItem item)
                    {
                        int id = item.getItemId();

                        if (id == R.id.task_options_popup_menu_delete)
                        {
                            ApiRequest.deleteTask(task.id, new ReceiveDataCallback<RequestResult>()
                            {
                                @Override
                                public void receiveData(RequestResult o)
                                {
                                    remove(task);
                                    notifyDataSetChanged();
                                }
                            });
                        }
                        else if (id == R.id.task_options_popup_menu_edit)
                        {
                            Bundle bundle = new Bundle();
                            bundle.putBoolean("edit", true);
                            bundle.putInt("id", task.id);
                            bundle.putString("nome_task", task.nome_task);
                            bundle.putString("data_avvio", task.data_avvio);
                            bundle.putString("data_scadenza", task.data_scadenza);
                            bundle.putInt("priorita", task.priorita);
                            bundle.putInt("id_progetto", task.id_progetto);
                            bundle.putString("nome_progetto", nome_progetto);

                            CreateTaskFragment createTask = new CreateTaskFragment();
                            createTask.setArguments(bundle);

                            ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, createTask).commit();
                        }
                        else if (id == R.id.task_options_popup_menu_show_users)
                        {
                            Bundle bundle = new Bundle();
                            bundle.putInt("id", task.id);
                            bundle.putString("nome_task", task.nome_task);
                            bundle.putInt("id_progetto", task.id_progetto);

                            TaskUsersFragment taskUsers = new TaskUsersFragment();
                            taskUsers.setArguments(bundle);

                            ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, taskUsers).commit();
                        }

                        return true;
                    }
                });

                popupMenu.show();
            }
        });

        return convertView;
    }
}
