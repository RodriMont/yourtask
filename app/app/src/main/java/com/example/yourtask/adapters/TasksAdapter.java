package com.example.yourtask.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.yourtask.CreateTaskFragment;
import com.example.yourtask.R;
import com.example.yourtask.TaskUsersFragment;
import com.example.yourtask.model.ApiRequest;
import com.example.yourtask.model.Lavoro;
import com.example.yourtask.model.ReceiveDataCallback;
import com.example.yourtask.model.RequestResult;
import com.example.yourtask.model.Task;
import com.example.yourtask.utility.DateFormatter;

import java.util.ArrayList;

public class TasksAdapter extends ArrayAdapter<Task>
{
    private Context context;
    private String nome_progetto;
    private ArrayList<Lavoro> completati;

    private class ViewHolder
    {
        public GridLayout layout;
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

    public TasksAdapter(Context context, ArrayList<Task> tasks, ArrayList<Lavoro> completati, String nome_progetto)
    {
        super(context, R.layout.tasks_listview, tasks);
        this.context = context;
        this.nome_progetto = nome_progetto;
        this.completati = completati;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder;

        if (convertView == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.tasks_listview, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.layout = convertView.findViewById(R.id.tasks_listview_layout);
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

        for (Lavoro lavoro : completati)
        {
            if (lavoro.id_task == task.id)
            {
                Drawable collaborators_listview_drawable = ContextCompat.getDrawable(context, R.drawable.rounded_frame).mutate();
                GradientDrawable collaborators_listview_gradient = (GradientDrawable)collaborators_listview_drawable;

                collaborators_listview_gradient.setColor(Color.rgb(175, 225, 175));

                viewHolder.layout.setBackground(collaborators_listview_gradient);
            }
        }

        viewHolder.task_name_label.setText(task.nome_task);
        viewHolder.start_date.setText(DateFormatter.format(DateFormatter.DateFormat.SLASH, task.data_avvio));
        viewHolder.end_date.setText(DateFormatter.format(DateFormatter.DateFormat.SLASH, task.data_scadenza));

        if (task.priorita == 1)
            viewHolder.priority_icon.setImageResource(R.drawable.prioritity_low);
        else if (task.priorita == 2)
            viewHolder.priority_icon.setImageResource(R.drawable.priority_medium);
        else if (task.priorita == 3)
            viewHolder.priority_icon.setImageResource(R.drawable.priority_high);

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
                        else if (id == R.id.task_options_popup_menu_complete)
                        {
                            ApiRequest.postLavoro(new Lavoro(
                                    context.getSharedPreferences("login", Context.MODE_PRIVATE).getInt("id", 0),
                                    task.id_progetto,
                                    task.id,
                                    1), new ReceiveDataCallback<RequestResult>() {
                                @Override
                                public void receiveData(RequestResult o)
                                {
                                    Drawable collaborators_listview_drawable = ContextCompat.getDrawable(context, R.drawable.rounded_frame).mutate();
                                    GradientDrawable collaborators_listview_gradient = (GradientDrawable)collaborators_listview_drawable;

                                    collaborators_listview_gradient.setColor(Color.rgb(175, 225, 175));

                                    viewHolder.layout.setBackground(collaborators_listview_gradient);
                                }
                            });
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
