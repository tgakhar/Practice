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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edt_email, edt_pass, edt_cpass;
    TextView txt_alrdy;
    Button btn_reg;
    String email, pass, cpass;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth=FirebaseAuth.getInstance();
        edt_email=findViewById(R.id.edt_mainEmail);
        edt_pass=findViewById(R.id.edt_mainPass);
        edt_cpass=findViewById(R.id.edt_mainCpass);
        txt_alrdy=findViewById(R.id.txt_mainLogin);
        btn_reg=findViewById(R.id.btn_mainReg);
        btn_reg.setOnClickListener(this);
        txt_alrdy.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_mainReg:
                register();
                                 break;
            case R.id.txt_mainLogin:
                Intent intent= new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                                    break;

        }
    }


    private void register() {

        email=edt_email.getText().toString().trim();
        pass= edt_pass.getText().toString().trim();
        cpass=edt_cpass.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            edt_email.requestFocus();
            edt_email.setError("Email cannot be blank");
            return;

        }else if (TextUtils.isEmpty(pass)){
            edt_pass.requestFocus();
            edt_pass.setError("Password cannot be blank");
            return;

        }else if (pass.length()<6){
            edt_pass.getText().clear();
            edt_pass.requestFocus();
            edt_pass.setError("Password cannot be less than 6 characters");
            return;

        }else if(TextUtils.isEmpty(cpass)){
            edt_cpass.getText().clear();
            edt_cpass.requestFocus();
            edt_cpass.setError("Cannot be blank");
            return;


        }else if (!pass.equals(cpass)){
            edt_pass.getText().clear();
            edt_cpass.getText().clear();
            edt_pass.requestFocus();
            edt_pass.setError("Password fields do not match");
            return;

        }

            auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(MainActivity.this, "User successfully registered", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(MainActivity.this, "Registeration failed!!", Toast.LENGTH_SHORT).show();
                    }

                }
            });


    }
}