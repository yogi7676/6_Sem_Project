package com.example.project;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateFinal extends Fragment {

    View ufiinal;
    String email;
    Spinner block,type;
    String sblock,stype,ssty,ssbl;
    EditText name,phone;
    Button updatebtn;
    String a,b,c,d;

    public UpdateFinal() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ufiinal=inflater.inflate(R.layout.fragment_update_final, container, false);
        email=UpdateFinalArgs.fromBundle(getArguments()).getEmail();

        block=ufiinal.findViewById(R.id.updatespinner);
        type=ufiinal.findViewById(R.id.updatetype);
        name=ufiinal.findViewById(R.id.updatename);
        phone=ufiinal.findViewById(R.id.updatephone);
        updatebtn=ufiinal.findViewById(R.id.updatebutton);

        block.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sblock=parent.getItemAtPosition(position).toString();

                if (sblock.equals("Choose Block")){
                    //do nothing
                }else if (sblock.equals("ICSE")){
                    ssbl="ICSE";
                }else if (sblock.equals("CBSE")){
                    ssbl="CBSE";
                }else if (sblock.equals("PUC")){
                    ssbl="PUC";
                }else if (sblock.equals("DEGREE")){
                    ssbl="DEGREE";
                }else if (sblock.equals("LAW")){
                    ssbl="LAW";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                stype=parent.getItemAtPosition(position).toString();

                if (stype.equals("Choose UserType")){
                   //do nothing
                }else if (stype.equals("ADMIN")){
                    ssty="ADMIN";
                }else if (stype.equals("STAFF")){
                    ssty="STAFF";
                }else if (stype.equals("IT STAFF")){
                    ssty="IT STAFF";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nn=name.getText().toString();
                String pp=phone.getText().toString();
                final DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("Users").child(email);
               /* databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String Name="ABC";
                        databaseReference.child("Name").setValue(Name);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });*/

                if (!stype.equals("Choose UserType")){
                    databaseReference.child("type").setValue(ssty);
                    a="User Type";
                    Navigation.findNavController(ufiinal).navigateUp();
                }else if (nn.length()!=0){
                    databaseReference.child("Name").setValue(nn);
                    b="Name";
                    Navigation.findNavController(ufiinal).navigateUp();
                }else if (pp.length()>10 && pp.length()<10){
                    phone.setError("Please enter a valid Phone Number");
                    phone.setText("");
                    phone.requestFocus();
                }else if (pp.length()!=0 ){
                    databaseReference.child("Phone").setValue(pp);
                    c="Phone Number";
                    Navigation.findNavController(ufiinal).navigateUp();
                }else if (!sblock.equals("Choose Block")){
                    databaseReference.child("Block").setValue(ssbl);
                    d="User Block";
                    Navigation.findNavController(ufiinal).navigateUp();
                }

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Toast.makeText(getActivity(), "Updated Successfully", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                //Toast.makeText(getActivity(), "Updated Sucessfully "+a+" "+b+" "+c+" "+d, Toast.LENGTH_SHORT).show();
            }
        });

        return ufiinal;
    }

}
