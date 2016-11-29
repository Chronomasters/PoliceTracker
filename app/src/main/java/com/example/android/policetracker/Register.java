package com.example.android.policetracker;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.R.attr.x;

public class Register extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextPassword2;
    private Button buttonRegister;
    private TextView textViewSignIn;
    private TextView loginEnforcement;
    private TextView goBackRegister;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    public String choice;
    public DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    public DatabaseReference policeReference = databaseReference.child("Police");
    public DatabaseReference dispatchReference = databaseReference.child("Dispatch");
    public DatabaseReference ambulanceReference = databaseReference.child("Ambulance");
    public DatabaseReference fireReference = databaseReference.child("Fire Department");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextPassword2 = (EditText) findViewById(R.id.editTextPassword2);
        textViewSignIn = (TextView) findViewById(R.id.textViewSignIn);
        loginEnforcement = (TextView) findViewById(R.id.loginEnforcement);
        goBackRegister = (TextView) findViewById(R.id.goBack);


        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();

        buttonRegister.setOnClickListener(this);
        textViewSignIn.setOnClickListener(this);
        goBackRegister.setOnClickListener(this);

        goBackRegister.setText("Already have an account? Sign in Here");

        Spinner dropdown = (Spinner) findViewById(R.id.choice);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.choice, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        dropdown.setAdapter(adapter);

        dropdown.setOnItemSelectedListener(this);

    }


    private void registerUser() {
        String email = editTextEmail.getText().toString().trim();
        final String databaseName = email.substring(0, email.indexOf("@"));
        System.out.println(x);
        String password = editTextPassword.getText().toString().trim();
        String password2 = editTextPassword2.getText().toString().trim();

        if (!password.equals(password2)) {
            Toast.makeText(this, "Your passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }


        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter an email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() <= 6 || password2.length() <= 6) {
            Toast.makeText(this, "Your password must have at least 6 characters", Toast.LENGTH_SHORT).show();
        }


        progressDialog.setMessage("Registering....");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            if (choice.equals("Police")) {
                                policeReference.child(databaseName).setValue(choice);
                            }

                            if (choice.equals("Ambulance")) {
                                ambulanceReference.child(databaseName).setValue(choice);
                            }

                            if (choice.equals("Dispatch")) {
                                dispatchReference.child(databaseName).setValue(choice);
                            }

                            if (choice.equals("Fire Department")) {
                                fireReference.child(databaseName).setValue(choice);
                            }
                            finish();
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            user.sendEmailVerification()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(Register.this, "A confirmation email has been sent", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                            Toast.makeText(getApplicationContext(), "You have successfully registered", Toast.LENGTH_SHORT).show();
                            if (choice.equals("Police")) {
                                startActivity(new Intent(getApplicationContext(), Police.class));
                            }

                            if (choice.equals("Ambulance")) {
                                startActivity(new Intent(getApplicationContext(), Ambulance.class));
                            }

                            if (choice.equals("Dispatch")) {
                                startActivity(new Intent(getApplicationContext(), Dispatch.class));
                            }

                            if (choice.equals("Fire Department")) {
                                startActivity(new Intent(getApplicationContext(), Firemen.class));
                            }

                        } else {
                            Toast.makeText(Register.this, "An error occurred", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }

    @Override
    public void onClick(View v) {
        if (v == buttonRegister) {
            registerUser();
        }

        if (v == textViewSignIn) {
            startActivity(new Intent(this, Login.class));
        }

        if (v == goBackRegister) {
            startActivity(new Intent(this, Login.class));
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        choice = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Toast.makeText(this, "Please choose a department", Toast.LENGTH_SHORT).show();
    }
}
