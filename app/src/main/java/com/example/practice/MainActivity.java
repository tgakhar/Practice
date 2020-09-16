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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edt_email, edt_pass, edt_cpass,edt_name,edt_phNo;
    TextView txt_alrdy;
    Button btn_reg;
    String email, pass, cpass, name, phoneNo;
    FirebaseAuth auth;
    FirebaseUser curUser;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth=FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();
        edt_name=findViewById(R.id.edt_NameRegister);
        edt_email=findViewById(R.id.edt_mainEmail);
        edt_pass=findViewById(R.id.edt_mainPass);
        edt_cpass=findViewById(R.id.edt_mainCpass);
        edt_phNo=findViewById(R.id.edt_RegphNo);
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

        name=edt_name.getText().toString().trim();
        email=edt_email.getText().toString().trim();
        pass= edt_pass.getText().toString().trim();
        cpass=edt_cpass.getText().toString().trim();
        phoneNo=edt_phNo.getText().toString().trim();


        if (TextUtils.isEmpty(name)){
            edt_name.requestFocus();
            edt_name.setError("Name field mandatory");
            return;

        } else if (TextUtils.isEmpty(email)){
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
                        Intent intent= new Intent(getApplicationContext(),LoginActivity.class);
                        startActivity(intent);

                       saveData();
                    }else{
                        try {
                            throw task.getException();

                        }catch (FirebaseAuthUserCollisionException e){
                            edt_email.getText().clear();
                            edt_email.requestFocus();
                            edt_email.setError("Email Id already exist");
                        }
                        catch (Exception e){

                        }
                        Toast.makeText(MainActivity.this, "Registeration failed!!", Toast.LENGTH_SHORT).show();
                    }

                }
            });


    }

    private void saveData() {

        Map<String, String> user = new HashMap<>();
        user.put("Name", name);
        user.put("Email id", email);
        user.put("Phone Number", phoneNo);
        curUser=auth.getCurrentUser();

        db.collection("users").document(curUser.getUid()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(MainActivity.this, "Data saved successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        curUser=auth.getCurrentUser();
        if (curUser!=null){
            Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
            startActivity(intent);
            Toast.makeText(this, "Already login", Toast.LENGTH_SHORT).show();
        }
    }
}