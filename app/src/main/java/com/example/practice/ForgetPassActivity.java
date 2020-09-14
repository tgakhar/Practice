package com.example.practice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edt_email;
    Button btn_sendEmail;
    FirebaseAuth auth;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);
        auth=FirebaseAuth.getInstance();
        edt_email=findViewById(R.id.edt_ForgetPassEmail);
        btn_sendEmail=findViewById(R.id.btn_SendResetLink);
        btn_sendEmail.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_SendResetLink:
                resetLink();
                break;
        }


    }

    private void resetLink() {

        email=edt_email.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            edt_email.getText().clear();
            edt_email.requestFocus();
            edt_email.setError("Valid email required");
            return;
        }
        auth.sendPasswordResetEmail(email).addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()){
                    Toast.makeText(ForgetPassActivity.this, "Reset email sent successfully", Toast.LENGTH_SHORT).show();
                    Intent intent =new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(intent);

                }else {
                    Toast.makeText(ForgetPassActivity.this, "Email sending failed!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}