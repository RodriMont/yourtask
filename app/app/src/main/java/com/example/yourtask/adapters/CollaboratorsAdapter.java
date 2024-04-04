package com.example.yourtask.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.yourtask.R;
import com.example.yourtask.model.User;

import java.util.ArrayList;

public class CollaboratorsAdapter extends ArrayAdapter<User>
{
    private Context context;

    private class ViewHolder
    {
        public FrameLayout layout;
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
            viewHolder.layout = convertView.findViewById(R.id.collaborators_listview_layout);

            convertView.setTag(viewHolder);
        }
        else
            viewHolder = (ViewHolder)convertView.getTag();

        User item = getItem(position);
        viewHolder.item_name_label.setText(item.email);

        if (item.id == -1)
        {
            Drawable collaborators_listview_drawable = ContextCompat.getDrawable(context, R.drawable.rounded_collaborator_container).mutate();
            GradientDrawable collaborators_listview_gradient = (GradientDrawable)collaborators_listview_drawable;

            collaborators_listview_gradient.setStroke(5, Color.rgb(255, 130, 130));

            viewHolder.layout.setBackground(collaborators_listview_gradient);
        }
        else
        {
            Drawable collaborators_listview_drawable = ContextCompat.getDrawable(context, R.drawable.rounded_collaborator_container).mutate();
            GradientDrawable collaborators_listview_gradient = (GradientDrawable)collaborators_listview_drawable;

            collaborators_listview_gradient.setStroke(2, Color.rgb(0, 0, 0));

            viewHolder.layout.setBackground(collaborators_listview_gradient);
        }

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
