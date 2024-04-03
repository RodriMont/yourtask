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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.yourtask.adapters.CollaboratorsAdapter;
import com.example.yourtask.model.ApiRequest;
import com.example.yourtask.model.Progetto;
import com.example.yourtask.model.ReceiveDataCallback;
import com.example.yourtask.model.Task;
import com.google.android.material.navigation.NavigationBarView;

import java.time.LocalDate;
import java.util.ArrayList;

public class CreateTaskFragment extends Fragment
{
    private static int priorita = 1;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_create_task, container, false);

        Button newTask = view.findViewById(R.id.new_task_create_button);
        EditText nomeTaskEditText = view.findViewById(R.id.new_task_name_edittext);
        EditText dataAvvioEditText = view.findViewById(R.id.new_task_start_date_edittext);
        EditText dataScandenzaEditText = view.findViewById(R.id.new_task_end_date_edittext);

        ImageView start_date_calendar_icon = (ImageView)view.findViewById(R.id.new_task_start_date_calendar_icon);
        ImageView end_date_calendar_icon = (ImageView)view.findViewById(R.id.new_task_end_date_calendar_icon);
        AutoCompleteTextView collaborators_autocomplete = (AutoCompleteTextView)view.findViewById(R.id.new_task_collaborators_autocomplete);
        ListView collaborators_listview = (ListView)view.findViewById(R.id.new_task_collaborators_listview);
        Spinner priority_spinner = (Spinner)view.findViewById(R.id.new_task_priority_spinner);

        priority_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                if (selectedItem.equals("Bassa"))
                    priorita = 1;
                else if(selectedItem.equals("Media"))
                    priorita = 2;
                else if(selectedItem.equals("Alta"))
                    priorita = 3;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        newTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomeTaskText = nomeTaskEditText.getText().toString();
                String dataAvvioText = dataAvvioEditText.getText().toString();
                String dataScadenzaText = dataScandenzaEditText.getText().toString();

                String[] dataAvvioSplit = dataAvvioText.split("/");
                String dataAvvio = String.format("%s-%s-%s", dataAvvioSplit[2], dataAvvioSplit[1], dataAvvioSplit[0]);

                String[] dataScadenzaSplit = dataAvvioText.split("/");
                String dataScadenza = String.format("%s-%s-%s", dataScadenzaSplit[2], dataScadenzaSplit[1], dataScadenzaSplit[0]);

                if (nomeTaskText.equals("") || dataAvvioText.equals("") || dataScadenzaText.equals(""))
                    Toast.makeText(getActivity(), "Campo obbligatorio", Toast.LENGTH_LONG).show();
                else {
                    ApiRequest.postTask(new Task(1, nomeTaskText, dataAvvio, dataScadenza, priorita, 1), new ReceiveDataCallback<Integer>() {
                        @Override
                        public void receiveData(Integer o) {
                            if (o == 200) {
                                Toast.makeText(getActivity(), "200", Toast.LENGTH_LONG).show();
                                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProjectFragment()).commit();
                            }
                            else if (o == 400)
                                Toast.makeText(getActivity(), "400", Toast.LENGTH_LONG).show();
                            else if (o == 500)
                                Toast.makeText(getActivity(), "500", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

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