package com.example.yourtask;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.time.LocalDate;

public class CreateTaskFragment extends Fragment
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_create_task, container, false);

        ImageView start_date_calendar_icon = (ImageView)view.findViewById(R.id.new_task_start_date_calendar_icon);
        ImageView end_date_calendar_icon = (ImageView)view.findViewById(R.id.new_task_end_date_calendar_icon);
        AutoCompleteTextView users_autocomplete = (AutoCompleteTextView)view.findViewById(R.id.new_task_users_autocomplete);
        ListView users_listview = (ListView)view.findViewById(R.id.new_task_users_listview);

        users_autocomplete.setThreshold(1);

        String[] test = {"abc", "cde", "fgh", "ijk", "lmn", "opq", "rst", "uvw", "xyz"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, test);

        users_autocomplete.setAdapter(adapter);

        users_autocomplete.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {

            }
        });

        start_date_calendar_icon.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openDatePicker((EditText)view.findViewById(R.id.new_task_start_date_edittext));
            }
        });

        end_date_calendar_icon.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openDatePicker((EditText)view.findViewById(R.id.new_task_end_date_edittext));
            }
        });

        return view;
    }

    private void openDatePicker(EditText dateViewer)
    {
        DatePickerDialog datePicker = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
            {
                dateViewer.setText(String.format("%s/%s/%s", dayOfMonth, month + 1, year));
            }
        }, LocalDate.now().getYear(), LocalDate.now().getMonthValue() - 1, LocalDate.now().getDayOfMonth());

        datePicker.show();
    }
}