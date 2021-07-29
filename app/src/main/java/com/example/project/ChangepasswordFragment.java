package com.example.project;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChangepasswordFragment extends Fragment {

    TextInputEditText resetemail;
    Button reset;
    FirebaseAuth fAuth;

    public ChangepasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View change=inflater.inflate(R.layout.fragment_changepassword,container,false);

        resetemail=(TextInputEditText)change.findViewById(R.id.resetemail);
        reset=(Button)change.findViewById(R.id.done);

        fAuth= FirebaseAuth.getInstance();

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email=resetemail.getText().toString().trim();
                if (email.isEmpty())
                {
                    resetemail.setError("Enter Email Address");
                    resetemail.setText("");
                    resetemail.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    resetemail.setError("Enter Valid Email Address");
                    resetemail.requestFocus();
                    resetemail.setText("");
                    return;
                }

                fAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getActivity(),"Reset Link Sent to Email Address",Toast.LENGTH_SHORT).show();
                            resetemail.setText("");
                        }

                    }
                });
            }
        });
        return change;
    }

}
