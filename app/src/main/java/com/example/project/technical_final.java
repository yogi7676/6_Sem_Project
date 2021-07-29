package com.example.project;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class technical_final extends Fragment {

    View view;
    String Bookid;
    DatabaseReference tfDatabaseReference;
    TextView chairs,bells,rem,stand,cord,colar,sap,rose,bouq,lap,proj,water,trans,other,momen,food;


    public technical_final() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view=inflater.inflate(R.layout.fragment_technical_final, container, false);

        chairs=view.findViewById(R.id.fchairs);
        bells=view.findViewById(R.id.fbells);
        rem=view.findViewById(R.id.fremuneration);
        stand=view.findViewById(R.id.fstandmike);
        cord=view.findViewById(R.id.fcordless);
        colar=view.findViewById(R.id.fcolar);
        sap=view.findViewById(R.id.fsapling);
        rose=view.findViewById(R.id.frose);
        bouq=view.findViewById(R.id.fbouquet);
        lap=view.findViewById(R.id.flaptop);
        proj=view.findViewById(R.id.fprojector);
        water=view.findViewById(R.id.fwater);
        trans=view.findViewById(R.id.ftransportation);
        other=view.findViewById(R.id.fothers);
        momen=view.findViewById(R.id.fmomento);
        food=view.findViewById(R.id.ffood);


        Bookid=technical_finalArgs.fromBundle(getArguments()).getBookingid();

        tfDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Bookings").child(Bookid);

        tfDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String schairs,sbells,srem,sstand,scord,scolar,ssap,srose,sbouq,slap,sproj,swater,strans,sother,smomen,sfood;
                schairs=dataSnapshot.child("Chairs").getValue().toString();
                sbells=dataSnapshot.child("Bells").getValue().toString();
                srem=dataSnapshot.child("Remuneration").getValue().toString();
                sstand=dataSnapshot.child("StandMike").getValue().toString();
                scord=dataSnapshot.child("Cordless").getValue().toString();
                scolar=dataSnapshot.child("Colar").getValue().toString();
                ssap=dataSnapshot.child("Sapling").getValue().toString();
                srose=dataSnapshot.child("Rose").getValue().toString();
                sbouq=dataSnapshot.child("Bouquet").getValue().toString();
                slap=dataSnapshot.child("Laptop").getValue().toString();
                sproj=dataSnapshot.child("Projector").getValue().toString();
                swater=dataSnapshot.child("WaterBottles").getValue().toString();
                strans=dataSnapshot.child("Transportation").getValue().toString();
                sother=dataSnapshot.child("Others").getValue().toString();
                smomen=dataSnapshot.child("Momento").getValue().toString();
                sfood=dataSnapshot.child("FoodSnacks").getValue().toString();


                chairs.setText("Chairs : "+schairs);
                bells.setText("Bells : "+sbells);
                rem.setText("Remuneration : "+srem);
                stand.setText("StandMike : "+sstand);
                cord.setText("Cordless : "+scord);
                colar.setText("Colar : "+scolar);
                sap.setText("Sapling : "+ssap);
                rose.setText("Rose : "+srose);
                bouq.setText("Bouquet : "+sbouq);
                lap.setText("Laptop : "+slap);
                proj.setText("Projector : "+sproj);
                water.setText("WaterBottles : "+swater);
                trans.setText("Transportation : "+strans);
                other.setText("Others : "+sother);
                momen.setText("Momento : "+smomen);
                food.setText("FoodSnacks : "+sfood);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }

}
