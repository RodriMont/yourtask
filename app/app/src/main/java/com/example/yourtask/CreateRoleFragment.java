package com.example.yourtask;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.yourtask.model.ApiRequest;
import com.example.yourtask.model.ReceiveDataCallback;
import com.example.yourtask.model.Ruolo;

import yuku.ambilwarna.AmbilWarnaDialog;

public class CreateRoleFragment extends Fragment
{
    private ImageView color_picker_button;
    private LinearLayout color_layout;
    private GradientDrawable gradient_color_layout_background;
    private int currentColor = 0;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_create_role, container, false);

        EditText role_edittext = view.findViewById(R.id.new_role_edittext);
        color_picker_button = view.findViewById(R.id.new_role_color_picker_button);
        color_layout = view.findViewById(R.id.new_role_color_layout);
        Button create_button = view.findViewById(R.id.new_role_create_button);
        Drawable color_layout_background = ContextCompat.getDrawable(getContext(), R.drawable.rounded_edittext).mutate();

        color_layout.setBackground(color_layout_background);
        gradient_color_layout_background = (GradientDrawable)color_layout_background;

        create_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String role_text = role_edittext.getText().toString();

                if (role_text.trim().equals(""))
                    Toast.makeText(getContext(), "Campo obbligatorio", Toast.LENGTH_LONG).show();
                else
                {
                    ApiRequest.postRuolo(new Ruolo(1, role_text, 1), new ReceiveDataCallback<Integer>()
                    {
                        @Override
                        public void receiveData(Integer o)
                        {
                            if (o == 200)
                            {
                                Toast.makeText(getContext(), "200", Toast.LENGTH_LONG).show();
                                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomepageFragment()).commit();
                            }
                            else if (o == 500)
                                Toast.makeText(getContext(), "500", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

        color_picker_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openColorPicker();
            }
        });

        return view;
    }

    public void openColorPicker()
    {
        AmbilWarnaDialog ambilWarnaDialog = new AmbilWarnaDialog(getContext(), currentColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog)
            {
            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color)
            {
                currentColor = color;
                gradient_color_layout_background.setColor(color);

                int brightness = Color.red(color) + Color.green(color) + Color.blue(color);

                if (brightness <= 512)
                    color_picker_button.setColorFilter(Color.rgb(255, 255, 255), PorterDuff.Mode.SRC_IN);
                else
                    color_picker_button.setColorFilter(Color.rgb(0, 0, 0), PorterDuff.Mode.SRC_IN);
            }
        });

        ambilWarnaDialog.show();
    }
}