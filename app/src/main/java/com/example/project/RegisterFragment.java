package com.example.project;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {


    public RegisterFragment() {
        // Required empty public constructor
    }

    TextInputEditText rusername,remail,rpass,rphone;
    Spinner rblock;
    RadioGroup rtype;
    ProgressDialog pgbar;
    Button rregister;
    FirebaseAuth fAuth;
    DatabaseReference rootref;
    RadioButton radioButton;
    ArrayAdapter<String> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View reg=inflater.inflate(R.layout.fragment_register, container, false);

        rusername=reg.findViewById(R.id.rusername);
        remail=reg.findViewById(R.id.remail);
        rpass=reg.findViewById(R.id.rpass);
        rblock=reg.findViewById(R.id.rblock);
        rphone=reg.findViewById(R.id.rphone);
        pgbar=new ProgressDialog(getActivity());

        String[] values={"CBSE","ICSE","PUC","DEGREE","LAW"};
        adapter= new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item,values);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        rblock.setAdapter(adapter);

        rregister=reg.findViewById(R.id.registerbtn);

        rtype=reg.findViewById(R.id.usertype);
        rtype.clearCheck();

        fAuth= FirebaseAuth.getInstance();

        rregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectid = rtype.getCheckedRadioButtonId();
                radioButton =reg.findViewById(selectid);
                final String usertype;
                usertype=radioButton.getText().toString();

                final String username=rusername.getText().toString();
                final String email=remail.getText().toString();
                final String password=rpass.getText().toString();
                final String phone=rphone.getText().toString();
                final String block=rblock.getSelectedItem().toString();



                if (username.isEmpty()) {
                    rusername.setError("Enter Username");
                    rusername.requestFocus();
                    return;
                }else if (email.isEmpty()) {
                    remail.setError("Enter Email Address");
                    remail.requestFocus();
                    return;
                }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    remail.setError("Email does not exist !!");
                    remail.requestFocus();
                    return;
                }else if (password.isEmpty()) {
                    rpass.setError("Password Required");
                    rpass.requestFocus();
                    return;
                }else if (phone.isEmpty()) {
                    rphone.setError("Enter Phone No");
                    rphone.requestFocus();
                    return;
                }else if (phone.length()<10 || phone.length()>10) {
                    rphone.setError("Invalid Phone Number");
                    rphone.requestFocus();
                    return;
                }else
                {
                    pgbar.setTitle("Create Account");
                    pgbar.setMessage("Please Wait, while we are checking for Credentials");
                    pgbar.setCanceledOnTouchOutside(false);
                    pgbar.show();

                    fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful())
                            {
                                final String id;
                                id=fAuth.getCurrentUser().getUid();
                                rootref= FirebaseDatabase.getInstance().getReference();

                                rootref.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        if (usertype.equals("ADMIN")){
                                            HashMap<String,Object> user=new HashMap<>();
                                            user.put("Name",username);
                                            user.put("Email",email);
                                            user.put("Phone",phone);
                                            user.put("type",usertype);
                                            user.put("UID",id);

                                            rootref.child("Users").child(id).updateChildren(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful())
                                                    {
                                                        Toast.makeText(getActivity(), "User Created Successfully", Toast.LENGTH_SHORT).show();
                                                        pgbar.dismiss();
                                                        rusername.setText("");
                                                        remail.setText("");
                                                        rphone.setText("");
                                                        rtype.clearCheck();
                                                        rpass.setText("");
                                                        rtype.requestFocus();
                                                    }
                                                }
                                            });
                                        }else {
                                            HashMap<String,Object> user=new HashMap<>();
                                            user.put("Name",username);
                                            user.put("Email",email);
                                            user.put("Phone",phone);
                                            user.put("type",usertype);
                                            user.put("Block",block);
                                            user.put("UID",id);

                                            rootref.child("Users").child(id).updateChildren(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful())
                                                    {
                                                        Toast.makeText(getActivity(), "User Created Successfully", Toast.LENGTH_SHORT).show();
                                                        pgbar.dismiss();
                                                        rusername.setText("");
                                                        remail.setText("");
                                                        rphone.setText("");
                                                        rtype.clearCheck();
                                                        rpass.setText("");
                                                        rtype.requestFocus();
                                                    }
                                                }
                                            });
                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                            }
                            else
                            {
                                Toast.makeText(getActivity(), "User with"+email+"already exist", Toast.LENGTH_SHORT).show();
                                pgbar.dismiss();
                                rusername.setText("");
                                remail.setText("");
                                rphone.setText("");
                                rtype.clearCheck();
                                rpass.setText("");
                            }
                        }
                    });
                }
            }
        });
        return reg;
    }

}
