package com.example.yourtask.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

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

        viewHolder.options_icon.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                PopupMenu popupMenu = new PopupMenu(context, v);
                popupMenu.getMenuInflater().inflate(R.menu.user_options_popup_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
                {
                    @Override
                    public boolean onMenuItemClick(MenuItem item)
                    {
                        int id = item.getItemId();

                        if (id == R.id.user_options_popup_menu_remove)
                        {

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
