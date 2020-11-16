package com.example.clinicalasalud;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class LoginScreen extends AppCompatActivity {

    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        updateUI(user);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        ActionBar actionBar = getSupportActionBar();
        Objects.requireNonNull(actionBar).hide();

        firebaseAuth = FirebaseAuth.getInstance();

        TextInputEditText textInputEditTextEmail = findViewById(R.id.input_email);
        TextInputEditText textInputEditTextPassword = findViewById(R.id.input_password);
        textInputLayoutEmail = findViewById(R.id.layout_email);
        textInputLayoutPassword = findViewById(R.id.layout_password);
        MaterialButton materialButtonLogin = findViewById(R.id.btn_log_in);

        final String[] email = new String[1];
        final String[] password = new String[1];

        textInputEditTextEmail.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                textInputLayoutEmail.setErrorEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textInputLayoutEmail.setErrorEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    textInputLayoutEmail.setError(getResources().getString(R.string.empty_email));
                } else {
                    email[0] = s.toString().trim();
                }
            }
        });

        textInputEditTextPassword.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                textInputLayoutPassword.setErrorEnabled(false);
                materialButtonLogin.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textInputLayoutPassword.setErrorEnabled(true);
                materialButtonLogin.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    textInputLayoutPassword.setError(getResources().getString(R.string.empty_email));
                    materialButtonLogin.setVisibility(View.INVISIBLE);
                } else {
                    password[0] = s.toString().trim();
                }
            }
        });

        materialButtonLogin.setOnClickListener(v -> signIn(email[0], password[0]));
    }

    private void signIn(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                updateUI(user);
            } else {
                Toast.makeText(this, getResources().getString(R.string.warning), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Log.e("Login", "Error Login");
        }
    }
}