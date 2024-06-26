package com.example.yourtask;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yourtask.adapters.CollaboratorsAdapter;
import com.example.yourtask.model.ApiRequest;
import com.example.yourtask.model.Progetto;
import com.example.yourtask.model.ReceiveDataCallback;
import com.example.yourtask.model.RequestResult;
import com.example.yourtask.model.User;
import com.example.yourtask.model.UtentiProgetto;
import com.example.yourtask.utility.DateFormatter;

import java.time.LocalDate;
import java.util.ArrayList;

public class CreateProjectFragment extends Fragment
{
    Button creaProgetto;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.create_project_fragment, container, false);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        int id_utente = sharedPreferences.getInt("id", 0);

        Bundle bundle = getArguments();

        EditText nomeProgettoEditText = view.findViewById(R.id.new_project_name_textView);
        EditText dataAvvioEditText = view.findViewById(R.id.new_project_start_date_edittext);
        EditText dataScandenzaEditText = view.findViewById(R.id.new_project_end_date_edittext);
        EditText budgetEditText = view.findViewById(R.id.new_project_budget_editText);

        if (bundle != null) {
            TextView title = view.findViewById(R.id.title_new_project_textView);
            title.setText("Modifica progetto");

            nomeProgettoEditText.setText(bundle.getString("nome_progetto"));
            dataAvvioEditText.setText(DateFormatter.format(DateFormatter.DateFormat.SLASH, bundle.getString("data_avvio")));
            dataScandenzaEditText.setText(DateFormatter.format(DateFormatter.DateFormat.SLASH, bundle.getString("data_scadenza")));

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

        if (bundle != null)
            creaProgetto.setText("Modifica");

        creaProgetto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String nomeProgettoText = nomeProgettoEditText.getText().toString();
                String dataAvvioText = DateFormatter.format(DateFormatter.DateFormat.TICK, dataAvvioEditText.getText().toString());
                String dataScadenzaText = DateFormatter.format(DateFormatter.DateFormat.TICK, dataScandenzaEditText.getText().toString());
                String budgetText = budgetEditText.getText().toString();

                if (nomeProgettoText.trim().equals("") || dataAvvioText.trim().equals("") || dataScadenzaText.trim().equals("") || budgetText.trim().equals(""))
                    Toast.makeText(getActivity(), "Campo obbligatorio", Toast.LENGTH_LONG).show();
                else if (bundle == null) {
                    ApiRequest.postProgetto(new Progetto(1, nomeProgettoText, dataAvvioText, dataScadenzaText, Float.parseFloat(budgetText)), new ReceiveDataCallback<RequestResult>() {
                        public int id_progetto;

                        @Override
                        public void receiveData(RequestResult o) {
                            id_progetto = o.id;

                            if (o.code == 200)
                            {
                                ArrayList<UtentiProgetto> creatore = new ArrayList<>();
                                creatore.add(new UtentiProgetto(id_utente, id_progetto));

                                ApiRequest.postUtentiProgetto(creatore, new ReceiveDataCallback<RequestResult>()
                                {
                                    @Override
                                    public void receiveData(RequestResult o)
                                    {
                                        if (o.code == 200)
                                        {
                                            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomepageFragment()).commit();
                                        }
                                    }
                                });
                            }
                        }
                    });

                }
                else {
                    Progetto progetto = new Progetto(bundle.getInt("id"), nomeProgettoText, dataAvvioText, dataScadenzaText, Float.parseFloat(budgetText));
                    ApiRequest.putProgetto(bundle.getInt("id"), progetto, new ReceiveDataCallback<RequestResult>() {
                        @Override
                        public void receiveData(RequestResult o) {
                            if (o.code == 200) {
                                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomepageFragment()).commit();
                            }
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