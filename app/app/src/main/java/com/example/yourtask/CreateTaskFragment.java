package com.example.yourtask;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
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
import com.example.yourtask.model.RequestResult;
import com.example.yourtask.model.Task;
import com.example.yourtask.model.User;
import com.example.yourtask.model.UtentiTask;
import com.example.yourtask.utility.DateFormatter;
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

        Bundle bundle = getArguments();

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("mypref", Context.MODE_PRIVATE);
        int id_utente = sharedPreferences.getInt("id", 0);

        Button newTask = view.findViewById(R.id.new_task_create_button);
        EditText nomeTaskEditText = view.findViewById(R.id.new_task_name_edittext);
        EditText dataAvvioEditText = view.findViewById(R.id.new_task_start_date_edittext);
        EditText dataScandenzaEditText = view.findViewById(R.id.new_task_end_date_edittext);

        ImageView start_date_calendar_icon = (ImageView)view.findViewById(R.id.new_task_start_date_calendar_icon);
        ImageView end_date_calendar_icon = (ImageView)view.findViewById(R.id.new_task_end_date_calendar_icon);
        Spinner priority_spinner = (Spinner)view.findViewById(R.id.new_task_priority_spinner);

        if (bundle.containsKey("edit"))
        {
            nomeTaskEditText.setText(bundle.getString("nome_task"));
            dataAvvioEditText.setText(DateFormatter.format(DateFormatter.DateFormat.SLASH, bundle.getString("data_avvio")));
            dataScandenzaEditText.setText(DateFormatter.format(DateFormatter.DateFormat.SLASH, bundle.getString("data_scadenza")));

            int priorita = bundle.getInt("priorita");
            priority_spinner.setSelection(Math.min(priorita, 3));
        }

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
                String dataAvvioText = DateFormatter.format(DateFormatter.DateFormat.TICK, dataAvvioEditText.getText().toString());
                String dataScadenzaText = DateFormatter.format(DateFormatter.DateFormat.TICK, dataScandenzaEditText.getText().toString());

                if (nomeTaskText.trim().equals("") || dataAvvioText.trim().equals("") || dataScadenzaText.trim().equals(""))
                    Toast.makeText(getActivity(), "Campo obbligatorio", Toast.LENGTH_LONG).show();
                else if (!bundle.containsKey("edit")) {
                    ApiRequest.postTask(new Task(bundle.getInt("id"), nomeTaskText, dataAvvioText, dataScadenzaText, priorita, bundle.getInt("id_progetto")), new ReceiveDataCallback<RequestResult>() {
                        int id_task;

                        @Override
                        public void receiveData(RequestResult o) {
                            id_task = o.id;

                            if (o.code == 200) {
                                Bundle returnBundle = new Bundle();
                                returnBundle.putInt("id", bundle.getInt("id_progetto"));
                                returnBundle.putString("nome_progetto", bundle.getString("nome_progetto"));

                                ProjectFragment projectFragment = new ProjectFragment();
                                projectFragment.setArguments(returnBundle);

                                ArrayList<UtentiTask> utentiTask = new ArrayList<>();
                                utentiTask.add(new UtentiTask(id_task, id_utente));
                                ApiRequest.postUtentiTask(utentiTask, new ReceiveDataCallback<RequestResult>() {
                                    @Override
                                    public void receiveData(RequestResult o) {
                                        if (o.code == 200)
                                        {
                                            Toast.makeText(getActivity(), "200", Toast.LENGTH_LONG).show();
                                            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, projectFragment).commit();
                                        }
                                    }
                                });
                            }
                            else if (o.code == 400)
                                Toast.makeText(getActivity(), "400", Toast.LENGTH_LONG).show();
                            else if (o.code == 500)
                                Toast.makeText(getActivity(), "500", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                else
                {
                    ApiRequest.putTask(bundle.getInt("id"), new Task(bundle.getInt("id"), bundle.getString("nome_task"), bundle.getString("data_avvio"), bundle.getString("data_scadenza"), bundle.getInt("priorita"), bundle.getInt("id_progetto")), new ReceiveDataCallback<RequestResult>() {
                        @Override
                        public void receiveData(RequestResult o)
                        {
                            if (o.code == 200)
                            {
                                Bundle returnBundle = new Bundle();
                                returnBundle.putInt("id", bundle.getInt("id_progetto"));
                                returnBundle.putString("nome_progetto", bundle.getString("nome_progetto"));

                                ProjectFragment projectFragment = new ProjectFragment();
                                projectFragment.setArguments(returnBundle);

                                Toast.makeText(getActivity(), "200", Toast.LENGTH_LONG).show();
                                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, projectFragment).commit();
                            }
                            else if (o.code == 400)
                                Toast.makeText(getActivity(), "400", Toast.LENGTH_LONG).show();
                            else if (o.code == 500)
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