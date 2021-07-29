package com.example.project;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.project.model.MybookingsView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class AllBookings extends Fragment {

    View AllBookings;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    RecyclerView mRecyclerView;

    public AllBookings() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<MybookingsView> options=new FirebaseRecyclerOptions.Builder<MybookingsView>()
                .setQuery(databaseReference,MybookingsView.class)
                .build();
        FirebaseRecyclerAdapter<MybookingsView,MyBookings.mybookingsviewholder> madapter=new FirebaseRecyclerAdapter<MybookingsView, MyBookings.mybookingsviewholder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final MyBookings.mybookingsviewholder mybookingsviewholder, int i, @NonNull final MybookingsView mybookingsView) {
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        mybookingsviewholder.ename.setText(mybookingsView.getGalleryName());
                        mybookingsviewholder.evname.setText(mybookingsView.getEventName());
                        mybookingsviewholder.evfloor.setText(mybookingsView.getGalleryFloor());
                        mybookingsviewholder.evblock.setText(mybookingsView.getGalleryBlock());
                        mybookingsviewholder.evperiod.setText(mybookingsView.getPeriod());
                        mybookingsviewholder.evdate.setText(mybookingsView.getDate());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @NonNull
            @Override
            public MyBookings.mybookingsviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.mybookings_,parent,false);

                return new MyBookings.mybookingsviewholder(view);
            }
        };

        mRecyclerView.setAdapter(madapter);
        madapter.startListening();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        AllBookings= inflater.inflate(R.layout.fragment_all_bookings, container, false);

        firebaseAuth=FirebaseAuth.getInstance();

        databaseReference= FirebaseDatabase.getInstance().getReference("Bookings");

        mRecyclerView= AllBookings.findViewById(R.id.allrecycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return  AllBookings;
    }
}
