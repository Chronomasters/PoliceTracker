package com.example.android.policetracker;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static com.example.android.policetracker.MainActivity.aFlag;
import static com.example.android.policetracker.MainActivity.dFlag;
import static com.example.android.policetracker.MainActivity.fFlag;
import static com.example.android.policetracker.MainActivity.pFlag;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogin;
    private TextView textViewSignUp;
    private ProgressDialog progressDialog;
    public static FirebaseAuth firebaseAuth;
    private TextView textViewForgotPassword;
    private TextView goBack;
    private TextView enforcement;

    public static String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        textViewSignUp = (TextView) findViewById(R.id.textViewSignUp);
        textViewForgotPassword = (TextView) findViewById(R.id.textViewForgotPassword);
        goBack = (TextView) findViewById(R.id.back);
        enforcement = (TextView) findViewById(R.id.enforcementLogin);


        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();

        buttonLogin.setOnClickListener(this);
        textViewSignUp.setOnClickListener(this);
        textViewForgotPassword.setOnClickListener(this);
        goBack.setOnClickListener(this);



        if (aFlag) {
            goBack.setText("Not an Ambulance? Click Here");
            enforcement.setText("Ambulance Login");
        }

        if (dFlag) {
            goBack.setText("Not part of Dispatch? Click Here");
            enforcement.setText("Dispatch Login");
        }

        if (pFlag) {
            goBack.setText("Not a Police Officer? Click Here");
            enforcement.setText("Police Login");
        }

        if (fFlag) {
            goBack.setText("Not a Fireman? Click Here");
            enforcement.setText("Firemen Login");
        }

    }

    private void userLogin() {
        final String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();


        if(TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter an email", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Logging in....");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()) {
                            username = email;
                            finish();
                            startActivity(new Intent(getApplicationContext(), Police.class));

                        }
                        else {
                            Toast.makeText(Login.this, "This account is not registered", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }





    @Override
    public void onClick(View v) {
        if (v == goBack) {
            aFlag = false;
            dFlag = false;
            pFlag = false;
            fFlag = false;

            startActivity(new Intent(this, MainActivity.class));
        }

        if(v == buttonLogin) {
            userLogin();
        }

        if(v == textViewSignUp) {
            startActivity(new Intent(this, Register.class));
        }

        if(v == textViewForgotPassword) {
            startActivity(new Intent(this, ForgotPassword.class));

        }

    }
}
