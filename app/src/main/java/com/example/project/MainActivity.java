package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    TextInputEditText lusername,lpassword;
    Button lloginbtn;
    TextView lforgot;
    ProgressDialog progressBar;
    FirebaseAuth fAuth;
    DatabaseReference rootref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lusername=(TextInputEditText)findViewById(R.id.emailname);
        lpassword=(TextInputEditText)findViewById(R.id.passs);
        lloginbtn=findViewById(R.id.loginbtn);
        lforgot=findViewById(R.id.forgotpass);
        progressBar=new ProgressDialog(MainActivity.this);

        fAuth= FirebaseAuth.getInstance();

        lforgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (!isconnected()){
                    Toast.makeText(MainActivity.this, "Please Ensure Internet Connection", Toast.LENGTH_SHORT).show();
                }else if (isconnected()){
                    Intent intent=new Intent(MainActivity.this,changepassword.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        });

        lloginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = lusername.getText().toString().trim();
                final String pass = lpassword.getText().toString().trim();

                if (!validator(pass)){
                    lpassword.setError("Minimum 8 Characters,One Alphabetic Character,One Small case Character,One Digit and one special symbol Required");
                    lpassword.setText("");
                    lpassword.requestFocus();
                }else if (email.isEmpty()) {
                    lusername.setError("Email Required");
                    lusername.setText("");
                    lusername.requestFocus();
                    return;
                }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    lusername.setError("Enter Valid Email Address");
                    lusername.setText("");
                    lusername.requestFocus();
                    return;
                }else if (pass.isEmpty()) {
                    lpassword.setError("Please enter Password");
                    lpassword.setText("");
                    lpassword.requestFocus();
                    return;
                }else{
                    progressBar.setTitle("Logging In");
                    progressBar.setMessage("Please Wait, While we verify Credentials");
                    progressBar.setCanceledOnTouchOutside(false);
                    progressBar.show();

                    fAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful())
                            {
                                final String id;
                                id=fAuth.getCurrentUser().getUid();

                                rootref=FirebaseDatabase.getInstance().getReference().child("Users").child(id);

                                rootref.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists()) {
                                            String usertype, username, usermail;
                                            usertype = dataSnapshot.child("type").getValue().toString();
                                            username = dataSnapshot.child("Name").getValue().toString();
                                            usermail = dataSnapshot.child("Email").getValue().toString();

                                            if (usertype.equals("ADMIN")) {
                                                Intent intent = new Intent(MainActivity.this, Admin.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                intent.putExtra("usermail", usermail);
                                                intent.putExtra("username", username);
                                                startActivity(intent);
                                                lusername.setText("");
                                                lpassword.setText("");
                                                lusername.requestFocus();
                                                progressBar.dismiss();

                                            } else if (usertype.equals("STAFF")) {
                                                Intent intent = new Intent(MainActivity.this, Staff.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                intent.putExtra("usermail", usermail);
                                                intent.putExtra("username", username);
                                                startActivity(intent);
                                                lusername.setText("");
                                                lpassword.setText("");
                                                lusername.requestFocus();
                                                progressBar.dismiss();
                                            } else if (usertype.equals("IT STAFF")) {
                                                Intent intent = new Intent(MainActivity.this, Technical.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                intent.putExtra("username", username);
                                                intent.putExtra("usermail", usermail);
                                                startActivity(intent);
                                                lusername.setText("");
                                                lpassword.setText("");
                                                lusername.requestFocus();
                                                progressBar.dismiss();
                                            }
                                        }else if (!dataSnapshot.exists()){
                                            Toast.makeText(MainActivity.this, "User does not Exists,Please Contact Admin ...", Toast.LENGTH_SHORT).show();
                                            progressBar.dismiss();
                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                            else
                            {
                                Toast.makeText(MainActivity.this, "User does not exists ", Toast.LENGTH_SHORT).show();
                                lusername.setText("");
                                lpassword.setText("");
                                lusername.requestFocus();
                                progressBar.dismiss();
                            }
                        }
                    });
                }
            }
        });

        if (!isconnected())
        {
            Toast.makeText(MainActivity.this,"Please Ensure Internet Connection",Toast.LENGTH_SHORT).show();
        }
    }

    public boolean isconnected()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null;
    }

    public static boolean validator(String password){
        Pattern pattern;
        Matcher matcher;
        final String Pass_pattern="^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])(?=.*[@#!$%^&+=])(?=\\S+$).{8,}$";
        pattern=Pattern.compile(Pass_pattern);
        matcher=pattern.matcher(password);
        return matcher.matches();
    }

}
