package com.example.yourtask.adapters;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.yourtask.R;
import com.example.yourtask.model.ApiRequest;
import com.example.yourtask.model.ReceiveDataCallback;
import com.example.yourtask.model.RequestResult;
import com.example.yourtask.model.User;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProjectUsersAdapter extends ArrayAdapter<User>
{
    private Context context;
    private final int id_progetto;

    private class ViewHolder
    {
        public CircleImageView profile_image;
        public TextView name;
        public TextView email;
        public ImageView options_icon;
    }

    public ProjectUsersAdapter(Context context, ArrayList<User> items, int id_progetto)
    {
        super(context, R.layout.project_users_listview, items);
        this.context = context;
        this.id_progetto = id_progetto;
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
            viewHolder.email = convertView.findViewById(R.id.project_users_listview_email);
            viewHolder.options_icon = convertView.findViewById(R.id.project_users_listview_options_icon);

            convertView.setTag(viewHolder);
        }
        else
            viewHolder = (ViewHolder)convertView.getTag();

        User user = getItem(position);
        viewHolder.name.setText(user.username);
        viewHolder.email.setText(user.email);

        viewHolder.options_icon.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                PopupMenu popupMenu = new PopupMenu(context, v);
                popupMenu.getMenuInflater().inflate(R.menu.user_options_popup_menu, popupMenu.getMenu());

                if (user.id == context.getSharedPreferences("login", Context.MODE_PRIVATE).getInt("id", 0))
                {
                    MenuItem item = popupMenu.getMenu().findItem(R.id.user_options_popup_menu_remove);

                    item.setEnabled(false);
                    SpannableString spannable = new SpannableString(item.getTitle());
                    spannable.setSpan(new ForegroundColorSpan(Color.GRAY), 0, spannable.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    item.setTitle(spannable);
                }

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
                {
                    @Override
                    public boolean onMenuItemClick(MenuItem item)
                    {
                        int id = item.getItemId();

                        if (id == R.id.user_options_popup_menu_remove)
                        {
                            ApiRequest.deleteUtenteProgetto(user.id, id_progetto, new ReceiveDataCallback<RequestResult>() {
                                @Override
                                public void receiveData(RequestResult o)
                                {
                                    remove(user);
                                    notifyDataSetChanged();
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
