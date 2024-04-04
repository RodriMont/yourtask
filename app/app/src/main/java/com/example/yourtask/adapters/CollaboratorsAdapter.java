package com.example.yourtask.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.yourtask.R;
import com.example.yourtask.model.User;

import java.util.ArrayList;

public class CollaboratorsAdapter extends ArrayAdapter<User>
{
    private Context context;

    private class ViewHolder
    {
        public TextView item_name_label;
        public ImageView remove_item_button;
    }

    public CollaboratorsAdapter(Context context, ArrayList<User> items)
    {
        super(context, R.layout.collaborators_listview, items);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder = null;

        if (convertView == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.collaborators_listview, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.item_name_label = convertView.findViewById(R.id.collaborators_listview_item_name_label);
            viewHolder.remove_item_button = convertView.findViewById(R.id.collaborators_listview_remove_item_button);

            convertView.setTag(viewHolder);
        }
        else
            viewHolder = (ViewHolder)convertView.getTag();

        User item = getItem(position);
        viewHolder.item_name_label.setText(item.email);

        viewHolder.remove_item_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                remove(item);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }
}
