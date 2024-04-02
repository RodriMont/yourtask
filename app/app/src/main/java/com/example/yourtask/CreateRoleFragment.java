package com.example.yourtask;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import yuku.ambilwarna.AmbilWarnaDialog;


public class CreateRoleFragment extends Fragment {
    ImageView image;
    RelativeLayout relativeLayout;

    LinearLayout linearLayout;
    int defaultColor;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_create_role, container, false);

        image = view.findViewById(R.id.btn);
        linearLayout = view.findViewById(R.id.linear);


        defaultColor = ContextCompat.getColor(getContext(), R.color.orange);

        image.setOnClickListener(new View.OnClickListener(){
            @Override
                    public  void onClick(View v){
                openColorPicker();

            }
        });

        return view;
    }

    public  void  openColorPicker()
    {
        AmbilWarnaDialog ambilWarnaDialog = new AmbilWarnaDialog(getContext(), defaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                defaultColor = color;
                linearLayout.setBackgroundColor(defaultColor);
            }
        });

        ambilWarnaDialog.show();
    }
}