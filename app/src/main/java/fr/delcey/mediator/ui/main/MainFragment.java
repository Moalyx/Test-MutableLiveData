package fr.delcey.mediator.ui.main;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.Calendar;


import fr.delcey.mediator.R;

public class MainFragment extends Fragment {

    private MainViewModel mViewModel;
    private DatePickerDialog datePickerDialog;
    private Spinner spinnner;


    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        Calendar cal = Calendar.getInstance();
        int y = cal.get(Calendar.YEAR);
        int m = cal.get(Calendar.MONTH);
        int d = cal.get(Calendar.DAY_OF_MONTH);



        EditText nameEdittext = view.findViewById(R.id.edittext_name);
        Button ageButton = view.findViewById(R.id.button_age);
        TextView messageTextView = view.findViewById(R.id.message_textview);
        CheckBox sexCheckbox = view.findViewById(R.id.checkbox_sex);
        Button dateButton = view.findViewById(R.id.datePickerButton);
        Spinner spinner = view.findViewById(R.id.spinner);

        //configureResolutionSpinner();

        spinnner = new Spinner(getContext());
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.resolution, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


//

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String selectedRoom = adapterView.getItemAtPosition(position).toString();
                mViewModel.onResolutionSelected(selectedRoom);

                Toast.makeText(adapterView.getContext(), selectedRoom, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month + 1;
                        String date = day + "/" + month + "/" + year;
                        int age = y - year;
                                //datePickerDialog.getDatePicker().getYear();
                        mViewModel.onAgeChanged(age);

                        mViewModel.onDateChanged(date);
                        dateButton.setText(date);


                    }

                }, y, m, d);
                datePickerDialog.show();

            }
        });


        nameEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mViewModel.onNameChanged(s.toString());
            }
        });

        ageButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                mViewModel.onIncreaseButtonClicked();
            }
        });

        sexCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mViewModel.onSexChanged(isChecked);
            }
        });

        mViewModel.getMessageLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String message) {
                messageTextView.setText(message);
            }
        });







    }


//    private void configureResolutionSpinner() {
//        spinnner = new Spinner(getContext());
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.resolution, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnner.setAdapter(adapter);
//        spinnner.setOnItemSelectedListener(this);
//    }




//    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
//        String selectedRoom = parent.getItemAtPosition(position).toString();
//        Toast.makeText(parent.getContext(), selectedRoom, Toast.LENGTH_SHORT).show();
//    }

//    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        String selectedRoom = parent.getItemAtPosition(position).toString();
//        Toast.makeText(parent.getContext(), selectedRoom, Toast.LENGTH_SHORT).show();
//    }






//    private void configureDatePicker() {
//        Calendar cal = Calendar.getInstance();
//        int year = cal.get(Calendar.YEAR);
//        int month = cal.get(Calendar.MONTH);
//        int day = cal.get(Calendar.DAY_OF_MONTH);
//
//        dateButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker view, int year, int month, int day) {
//                        month = month + 1;
//                        String date = day + "/" + month + "/" + year;
//                        mViewModel.onDateChanged(date);
//                        dateButton.setText(date);
//
//                    }
//                }, year, month, day);
//                datePickerDialog.show();
//            }
//        });
//    }



}
