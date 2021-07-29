package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class changepassword extends AppCompatActivity {

    TextInputEditText resetemail;
    Button reset;
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);

                resetemail=(TextInputEditText)findViewById(R.id.resetemail);
                reset=(Button)findViewById(R.id.done);

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
                                Toast.makeText(changepassword.this,"Reset Link Sent to Email Address", Toast.LENGTH_SHORT).show();
                                resetemail.setText("");
                                Intent intent=new Intent(changepassword.this,MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        });
                    }
                });
        }
}
