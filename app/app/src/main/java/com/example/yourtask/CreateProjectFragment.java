package com.example.yourtask;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.yourtask.model.ApiRequest;
import com.example.yourtask.model.Progetto;
import com.example.yourtask.model.ReceiveDataCallback;

import java.time.LocalDate;

public class CreateProjectFragment extends Fragment
{
    Button creaProgetto;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.create_project_fragment, container, false);

        EditText nomeProgettoEditText = view.findViewById(R.id.new_project_name_textView);
        EditText dataAvvioEditText = view.findViewById(R.id.new_project_start_date_edittext);
        EditText dataScandenzaEditText = view.findViewById(R.id.new_project_end_date_edittext);
        EditText budgetEditText = view.findViewById(R.id.new_project_budget_editText);

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
                String dataAvvioText = dataAvvioEditText.getText().toString();
                String dataScadenzaText = dataScandenzaEditText.getText().toString();
                String budgetText = budgetEditText.getText().toString();

                if (nomeProgettoText.equals("") || dataAvvioText.equals("") || dataScadenzaText.equals("") || budgetText.equals(""))
                    Toast.makeText(getActivity(), "Campo obbligatorio", Toast.LENGTH_LONG).show();
                else {
                    ApiRequest.postProgetto(new Progetto(1, nomeProgettoText, dataAvvioText, dataScadenzaText, Float.parseFloat(budgetText)), new ReceiveDataCallback<Integer>() {
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