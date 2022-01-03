package fr.delcey.mediator.ui.main;

import android.app.DatePickerDialog;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import java.util.Calendar;

import fr.delcey.mediator.MainActivity;
import fr.delcey.mediator.R;

public class MainViewModel extends ViewModel {

    private final MutableLiveData<String> nameMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<Integer> ageMutableLiveData = new MutableLiveData<>(0);
    private final MutableLiveData<Boolean> sexMutableLiveData = new MutableLiveData<>(false);
    private final MutableLiveData<String> dateMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<Integer> ageDeducMutableLiveData = new MutableLiveData<>(0);
    private final MutableLiveData<String> resolutionMutableLiveData = new MutableLiveData<>();


    private final MediatorLiveData<String> messageMediatorLiveData = new MediatorLiveData<>();


    public MainViewModel() {
        messageMediatorLiveData.addSource(nameMutableLiveData, new Observer<String>() {
            @Override
            public void onChanged(String newName) {
                combine(newName, ageMutableLiveData.getValue(), sexMutableLiveData.getValue(), dateMutableLiveData.getValue(),ageDeducMutableLiveData.getValue(), resolutionMutableLiveData.getValue());
            }
        });

        messageMediatorLiveData.addSource(ageMutableLiveData, new Observer<Integer>() {
            @Override
            public void onChanged(Integer newAge) {
//                if (newAge < ageDeducMutableLiveData.getValue()){
//                Toast.makeText(MainFragment.newInstance().getActivity(), "tu as eu la flemme de cliquer hein",Toast.LENGTH_LONG).show();
//                }
                combine(nameMutableLiveData.getValue(), newAge, sexMutableLiveData.getValue(), dateMutableLiveData.getValue(),ageDeducMutableLiveData.getValue(), resolutionMutableLiveData.getValue());

            }
        });

        messageMediatorLiveData.addSource(sexMutableLiveData, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean sex) {
                combine(nameMutableLiveData.getValue(), ageMutableLiveData.getValue(), sex, dateMutableLiveData.getValue(),ageDeducMutableLiveData.getValue(), resolutionMutableLiveData.getValue());
            }
        });

        messageMediatorLiveData.addSource(dateMutableLiveData, new Observer<String>() {
            @Override
            public void onChanged(String newDate) {
                combine(nameMutableLiveData.getValue(), ageMutableLiveData.getValue(), sexMutableLiveData.getValue(), newDate,ageDeducMutableLiveData.getValue(), resolutionMutableLiveData.getValue());
            }
        });

        messageMediatorLiveData.addSource(ageDeducMutableLiveData, new Observer<Integer>() {
            @Override
            public void onChanged(Integer ageDeduc) {
                combine(nameMutableLiveData.getValue(), ageMutableLiveData.getValue(), sexMutableLiveData.getValue(), dateMutableLiveData.getValue(), ageDeduc, resolutionMutableLiveData.getValue());
            }
        });

        messageMediatorLiveData.addSource(resolutionMutableLiveData, new Observer<String>() {
            @Override
            public void onChanged(String resolution) {
                combine(nameMutableLiveData.getValue(), ageMutableLiveData.getValue(), sexMutableLiveData.getValue(), dateMutableLiveData.getValue(), ageDeducMutableLiveData.getValue(), resolution);
            }
        });
    }

    private void combine(String newName, Integer newAge, Boolean sex, String newDdate, Integer ageDeduc, String resolution) {
       if (sex == true)
        messageMediatorLiveData.setValue("Salut " + newName + ", tu as " + newAge + " ans, et tu es une femme née le " +newDdate + " et d'après ta date de naissance tu as en réalité " + ageDeduc + " ans. Pour 2022 tu as pris une nouvelle résolution : " + resolution);
        else
            messageMediatorLiveData.setValue("Salut " + newName + ", tu as " + newAge + " ans, et tu es un homme né le " + newDdate + " et d'après ta date de naissance tu as en réalité " + ageDeduc + " ans. Pour 2022 tu as pris une nouvelle résolution : " + resolution);

    }



    public LiveData<String> getMessageLiveData() {
        return messageMediatorLiveData;
    }

    public void onNameChanged(String name) {

        nameMutableLiveData.setValue(name);
    }

    public void onIncreaseButtonClicked() {
        ageMutableLiveData.setValue(ageMutableLiveData.getValue() + 1);
    }

    public void onSexChanged(boolean isAWoman) {
        if ( isAWoman == true)
        sexMutableLiveData.setValue(true);
        else
            sexMutableLiveData.setValue(false);
    }

    public void onDateChanged(String date){
        dateMutableLiveData.setValue(date);
    }

    public void onAgeChanged(int deducAge){
        ageDeducMutableLiveData.setValue(deducAge);
    }

    public void onResolutionSelected(String resolution){
        resolutionMutableLiveData.setValue(resolution);
    }

}

