package com.example.practice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edt_email, edt_pass;
    Button btn_login;
    TextView txt_nUser;
    String email, pass;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth=FirebaseAuth.getInstance();
        edt_email = findViewById(R.id.edt_logEmail);
        edt_pass = findViewById(R.id.edt_logPass);
        btn_login = findViewById(R.id.btn_logLogin);
        txt_nUser = findViewById(R.id.txt_loginRegister);
        btn_login.setOnClickListener(this);
        txt_nUser.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_logLogin:
                login();
                break;

            case R.id.txt_loginRegister:
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                break;

        }


    }

    private void login() {

        email = edt_email.getText().toString().trim();
        pass = edt_pass.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            edt_email.requestFocus();
            edt_email.setError("Email cannot be blank");
            return;

        } else if (TextUtils.isEmpty(pass)) {
            edt_pass.requestFocus();
            edt_pass.setError("Password cannot be blank");
            return;


        } else if (pass.length() < 6) {
            edt_pass.getText().clear();
            edt_pass.requestFocus();
            edt_pass.setError("Password cannot be less than 6 characters");
            return;



        }auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "User logged in successfully", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(LoginActivity.this, "Login failed!!", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
}