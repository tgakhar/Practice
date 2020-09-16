package com.example.practice;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class


ProfileFragment extends Fragment {
EditText edt_name,edt_email,edt_phone;
Button btn_update,btn_signOut,btn_delete;
FirebaseUser user;
FirebaseFirestore db;
FirebaseAuth auth;
String name,phone;

    public ProfileFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth=FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edt_email=view.findViewById(R.id.edt_profEmail);
        edt_name=view.findViewById(R.id.edt_profName);
        edt_phone=view.findViewById(R.id.edt_profPhno);
        btn_update=view.findViewById(R.id.btn_UpdateProf);
        btn_signOut=view.findViewById(R.id.btn_proSignOut);
        btn_delete=view.findViewById(R.id.btn_proDelet);
        btn_delete.setOnClickListener(delete);
        btn_signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                Intent intent=new Intent(getActivity().getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });
        btn_update.setOnClickListener(update);
        loadData();
    }

    View.OnClickListener update=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            name=edt_name.getText().toString().trim();
            phone=edt_phone.getText().toString().trim();

            Map<String,Object> userMap=new HashMap<>();
            userMap.put("Name",name);
            userMap.put("Phone Number",phone);
            user=auth.getCurrentUser();
            db.collection("users").document(user.getUid()).update(userMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(getActivity().getApplicationContext(), "Data updated", Toast.LENGTH_SHORT).show();
                    loadData();
                }
            });

        }
    };

    private void loadData() {
        user=auth.getCurrentUser();
        db.collection("users").document(user.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                edt_email.setText(documentSnapshot.get("Email id").toString());
                edt_name.setText(documentSnapshot.get("Name").toString());
                edt_phone.setText(documentSnapshot.get("Phone Number").toString());
            }
        });
    }

    View.OnClickListener delete=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            user=auth.getCurrentUser();
            db.collection("users").document(user.getUid()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(getActivity().getApplicationContext(), "User Deleted", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(getActivity().getApplicationContext(),LoginActivity.class);
                            startActivity(intent);
                        }
                    });
                }
            });
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }
}