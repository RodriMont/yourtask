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
import android.widget.Spinner;
import android.widget.Toast;

import com.example.yourtask.adapters.CollaboratorsAdapter;

import java.time.LocalDate;
import java.util.ArrayList;

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
        AutoCompleteTextView collaborators_autocomplete = (AutoCompleteTextView)view.findViewById(R.id.new_task_collaborators_autocomplete);
        ListView collaborators_listview = (ListView)view.findViewById(R.id.new_task_collaborators_listview);
        Spinner priority_spinner = (Spinner)view.findViewById(R.id.new_task_priority_spinner);

        ArrayAdapter<String> priority_spinner_arrayAdapter = new ArrayAdapter<String>(
                getContext(),
                android.R.layout.simple_spinner_item,
                new String[] { "Bassa", "Media", "Alta"});

        priority_spinner.setAdapter(priority_spinner_arrayAdapter);

        CollaboratorsAdapter collaborators_autocomplete_adapter = new CollaboratorsAdapter(
                getContext(),
                new ArrayList<String>());
        collaborators_listview.setAdapter(collaborators_autocomplete_adapter);

        collaborators_autocomplete.setThreshold(1);

        /*
        String[] test = {"example1@mail.dom", "example2@mail.dom", "example3@mail.dom", "example4@mail.dom", "example5@mail.dom"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, test);
        collaborators_autocomplete.setAdapter(adapter);
        */

        collaborators_autocomplete.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                collaborators_autocomplete_adapter.add(parent.getItemAtPosition(position).toString());
                collaborators_autocomplete_adapter.notifyDataSetChanged();
                collaborators_autocomplete.setText("");
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