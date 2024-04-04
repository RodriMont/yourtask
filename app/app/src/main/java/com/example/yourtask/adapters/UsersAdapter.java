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
import com.example.yourtask.model.User;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UsersAdapter extends ArrayAdapter<User>
{
    private Context context;

    private class ViewHolder
    {
        public CircleImageView profile_image;
        public TextView name;
        public ImageView options_icon;
    }

    public UsersAdapter(Context context, ArrayList<User> items)
    {
        super(context, R.layout.project_users_listview, items);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder = null;

        if (convertView == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.project_users_listview, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.profile_image = convertView.findViewById(R.id.project_users_listview_profile_image);
            viewHolder.name = convertView.findViewById(R.id.project_users_listview_name);
            viewHolder.options_icon = convertView.findViewById(R.id.project_users_listview_options_icon);

            convertView.setTag(viewHolder);
        }
        else
            viewHolder = (ViewHolder)convertView.getTag();

        User item = getItem(position);
        viewHolder.name.setText(item.username);

        return convertView;
    }
}
