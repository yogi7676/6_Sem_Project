package com.example.project;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.project.model.MybookingsView;
import com.example.project.model.Technical_model;
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
public class CompletedEvents extends Fragment {

    View CompletedEvents;
    FirebaseAuth firebaseAuth;
    String userid,bookingid;
    DatabaseReference databaseReference;
    Query query,del;
    RecyclerView mRecyclerView;

    public CompletedEvents() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Technical_model> options=new FirebaseRecyclerOptions.Builder<Technical_model>()
                .setQuery(databaseReference,Technical_model.class)
                .build();

        FirebaseRecyclerAdapter<Technical_model,TechnicalHome.ViewHolder> adapter=new FirebaseRecyclerAdapter<Technical_model, TechnicalHome.ViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull TechnicalHome.ViewHolder viewHolder, int i, @NonNull final Technical_model technical_model)
            {
                viewHolder.username.setText(technical_model.getUserName());
                viewHolder.userphone.setText(technical_model.getPhoneNo());
                viewHolder.name.setText(technical_model.getGalleryName());
                viewHolder.block.setText(technical_model.getGalleryBlock());
                viewHolder.floor.setText(technical_model.getGalleryFloor());
                viewHolder.ename.setText(technical_model.getEventName());
                viewHolder.edepart.setText(technical_model.getEventDepartment());
                viewHolder.date.setText(technical_model.getDate());
                viewHolder.ftime.setText(technical_model.getPeriod());
                viewHolder.ttime.setText(technical_model.getToTime());
                viewHolder.tgid.setText(technical_model.getGalleryID());
                viewHolder.tuid.setText(technical_model.getUserID());
                viewHolder.coord.setText(technical_model.getEventCoordinator());

            }

            @NonNull
            @Override
            public TechnicalHome.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.technical_view_file,parent,false);

                return new TechnicalHome.ViewHolder(view);
            }
        };
        mRecyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        CompletedEvents=inflater.inflate(R.layout.fragment_completed_events, container, false);

        firebaseAuth= FirebaseAuth.getInstance();

        databaseReference= FirebaseDatabase.getInstance().getReference("CompletedEvents");

        mRecyclerView= CompletedEvents.findViewById(R.id.completedrecycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return CompletedEvents;
    }
}
