package com.example.yourtask;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yourtask.adapters.CollaboratorsAdapter;
import com.example.yourtask.model.ApiRequest;
import com.example.yourtask.model.Progetto;
import com.example.yourtask.model.ReceiveDataCallback;

import java.time.LocalDate;
import java.util.ArrayList;

public class CreateProjectFragment extends Fragment
{
    Button creaProgetto;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.create_project_fragment, container, false);

        Bundle bundle = getArguments();

        EditText nomeProgettoEditText = view.findViewById(R.id.new_project_name_textView);
        EditText dataAvvioEditText = view.findViewById(R.id.new_project_start_date_edittext);
        EditText dataScandenzaEditText = view.findViewById(R.id.new_project_end_date_edittext);
        EditText budgetEditText = view.findViewById(R.id.new_project_budget_editText);
        EditText collaboratorsEditText = view.findViewById(R.id.new_project_collaborators_edittext);
        ListView collaboratorsListView = view.findViewById(R.id.new_project_collaborators_listview);

        CollaboratorsAdapter collaboratorsAdapter = new CollaboratorsAdapter(getContext(), new ArrayList<>());
        collaboratorsListView.setAdapter(collaboratorsAdapter);

        collaboratorsEditText.setOnKeyListener(new View.OnKeyListener()
        {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    String item = collaboratorsEditText.getText().toString();

                    if (!item.trim().equals(""))
                    {
                        collaboratorsAdapter.add(item);
                        collaboratorsAdapter.notifyDataSetChanged();
                        return true;
                    }
                }

                return false;
            }
        });

        if (bundle != null) {
            nomeProgettoEditText.setText(bundle.getString("nome_progetto"));

            String[] dataAvvioSplit = bundle.getString("data_avvio").split("-");
            dataAvvioEditText.setText(String.format("%s/%s/%s", dataAvvioSplit[2], dataAvvioSplit[1], dataAvvioSplit[0]));

            String[] dataScadenzaSplit = bundle.getString("data_scadenza").split("-");
            dataScandenzaEditText.setText(String.format("%s/%s/%s", dataScadenzaSplit[2], dataScadenzaSplit[1], dataScadenzaSplit[0]));

            budgetEditText.setText(String.valueOf(bundle.getFloat("budget")));
        }

        ImageView calendarioInizio = view.findViewById(R.id.new_project_start_date_calendar_icon);
        ImageView calendarioScadenza = view.findViewById(R.id.new_project_end_date_calendar_icon);
        calendarioInizio.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openDatePicker((EditText)view.findViewById(R.id.new_project_start_date_edittext));
            }
        });

        calendarioScadenza.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openDatePicker((EditText)view.findViewById(R.id.new_project_end_date_edittext));
            }
        });

        creaProgetto = view.findViewById(R.id.new_project_button);
        creaProgetto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                String nomeProgettoText = nomeProgettoEditText.getText().toString();

                String[] dataAvvioSplit = dataAvvioEditText.getText().toString().split("/");
                String dataAvvioText = String.format("%s-%s-%s", dataAvvioSplit[2], dataAvvioSplit[1], dataAvvioSplit[0]);

                String[] dataScadenzaSplit = dataScandenzaEditText.getText().toString().split("/");
                String dataScadenzaText = String.format("%s-%s-%s", dataScadenzaSplit[2], dataScadenzaSplit[1], dataScadenzaSplit[0]);

                String budgetText = budgetEditText.getText().toString();

                if (nomeProgettoText.equals("") || dataAvvioText.equals("") || dataScadenzaText.equals("") || budgetText.equals(""))
                    Toast.makeText(getActivity(), "Campo obbligatorio", Toast.LENGTH_LONG).show();
                else if (bundle == null) {
                    ApiRequest.postProgetto(new Progetto(1, nomeProgettoText, dataAvvioText, dataScadenzaText, Float.parseFloat(budgetText)), new ReceiveDataCallback<Integer>() {
                        @Override
                        public void receiveData(Integer o) {
                            if (o == 200) {
                                Toast.makeText(getActivity(), "200", Toast.LENGTH_LONG).show();
                                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomepageFragment()).commit();
                            }
                            else if (o == 400)
                                Toast.makeText(getActivity(), "400", Toast.LENGTH_LONG).show();
                            else if (o == 500)
                                Toast.makeText(getActivity(), "500", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                else {
                    Progetto progetto = new Progetto(bundle.getInt("id"), nomeProgettoText, dataAvvioText, dataScadenzaText, Float.parseFloat(budgetText));
                    ApiRequest.putProgetto(bundle.getInt("id"), progetto, new ReceiveDataCallback<Integer>() {
                        @Override
                        public void receiveData(Integer o) {
                            if (o == 200) {
                                Toast.makeText(getActivity(), "200", Toast.LENGTH_LONG).show();
                                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomepageFragment()).commit();
                            }
                            else if (o == 500)
                                Toast.makeText(getActivity(), "500", Toast.LENGTH_LONG).show();
                        }
                    });
                }
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