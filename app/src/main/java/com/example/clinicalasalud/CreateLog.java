package com.example.clinicalasalud;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.clinicalasalud.fragments.DateFragment;
import com.example.clinicalasalud.fragments.TimeFragment;
import com.example.clinicalasalud.models.Dossiers;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Objects;

public class CreateLog extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    MaterialTextView date, time;
    MaterialButton createLogButton;
    TextInputLayout nameLayout, ageLayout, heightLayout, weightLayout, phoneLayout, sicknessLayout, historyLayout, backgroundLayout, physicalLayout, diagnoseLayout, treatmentLayout;
    TextInputEditText nameInput, heightInput, ageInput, weightInput, phoneInput, sicknessInput, historyInput, backgroundInput, physicalInput, diagnoseInput, treatmentInput;
    String currentDate, currentTime, name, phone, sickness, history, background, physical, diagnose, treatment;
    String age;
    String weight;
    String height;

    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_log);

        ActionBar actionBar = getSupportActionBar();
        Objects.requireNonNull(actionBar).setDisplayHomeAsUpEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        //Layouts to display errors on typing.
        nameLayout = findViewById(R.id.layout_name);
        ageLayout = findViewById(R.id.layout_age);
        heightLayout = findViewById(R.id.layout_height);
        weightLayout = findViewById(R.id.layout_weight);
        phoneLayout = findViewById(R.id.layout_phone);
        sicknessLayout = findViewById(R.id.layout_sickness);
        historyLayout = findViewById(R.id.layout_history);
        backgroundLayout = findViewById(R.id.layout_background_record);
        physicalLayout = findViewById(R.id.layout_physical_test);
        diagnoseLayout = findViewById(R.id.layout_diagnose);
        treatmentLayout = findViewById(R.id.layout_treatment);
        checkBox = findViewById(R.id.checkbox_confirmation);

        //Get errors on typing.
        nameInput = findViewById(R.id.input_name);
        ageInput = findViewById(R.id.input_age);
        heightInput = findViewById(R.id.input_height);
        weightInput = findViewById(R.id.input_weight);
        phoneInput = findViewById(R.id.input_phone);
        sicknessInput = findViewById(R.id.input_sickness);
        historyInput = findViewById(R.id.input_history);
        backgroundInput = findViewById(R.id.input_background_record);
        physicalInput = findViewById(R.id.input_physical_test);
        diagnoseInput = findViewById(R.id.input_diagnose);
        treatmentInput = findViewById(R.id.input_treatment);
        createLogButton = findViewById(R.id.btn_create_log);

        date.setOnClickListener(v -> {
            DialogFragment dialogFragment = new DateFragment();
            dialogFragment.show(getSupportFragmentManager(), "dateFragment");
        });

        time.setOnClickListener(v -> {
            DialogFragment dialogFragment = new TimeFragment();
            dialogFragment.show(getSupportFragmentManager(), "timeFragment");
        });
    }

    public void onCheckClicked(View view) {
        boolean state = ((CheckBox) view).isChecked();
        if (view.getId() == R.id.checkbox_confirmation) {
            if (state) {
                createLogButton.setVisibility(View.VISIBLE);
                createLogButton.setOnClickListener(v -> createDossier());
            } else {
                createLogButton.setVisibility(View.INVISIBLE);
            }
        }
    }

    public void createDossier() {

        //Get user input
        name = Objects.requireNonNull(nameInput.getText()).toString().trim();
        age = Objects.requireNonNull(ageInput.getText()).toString().trim();
        height = Objects.requireNonNull(heightInput.getText()).toString().trim();
        weight = Objects.requireNonNull(weightInput.getText()).toString().trim();
        phone = Objects.requireNonNull(phoneInput.getText()).toString().trim();
        sickness = Objects.requireNonNull(sicknessInput.getText()).toString().trim();
        history = Objects.requireNonNull(historyInput.getText()).toString().trim();
        background = Objects.requireNonNull(backgroundInput.getText()).toString().trim();
        physical = Objects.requireNonNull(physicalInput.getText()).toString().trim();
        diagnose = Objects.requireNonNull(diagnoseInput.getText()).toString().trim();
        treatment = Objects.requireNonNull(treatmentInput.getText()).toString().trim();

        Dossiers dossiers = new Dossiers(currentDate, currentTime, name, age, height, weight, phone, sickness, history, background, physical, diagnose, treatment);
        firebaseFirestore.collection("Dossiers")
                .document(name)
                .set(dossiers)
                .addOnSuccessListener(documentReference -> Toast.makeText(this, getResources().getString(R.string.added_dossier), Toast.LENGTH_SHORT).show()).addOnFailureListener(e -> Log.w("ErrorInserting", "Error adding document.", e));
        finish();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        date.setText(currentDate);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        currentTime = hourOfDay + ":" + minute;
        time.setText(currentTime);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}