package com.example.project;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.zip.Inflater;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyBookings extends Fragment {

    View mybook;
    FirebaseAuth firebaseAuth;
    String userid,bookingid;
    DatabaseReference databaseReference;
    Query query,del;
    RecyclerView mRecyclerView;

    public static class mybookingsviewholder extends RecyclerView.ViewHolder
    {
        TextView ename,evname,evblock,evfloor,evperiod,evdate;
        LinearLayout mybookingslayout;

        public mybookingsviewholder(@NonNull View itemView) {
            super(itemView);
            ename=itemView.findViewById(R.id.thgalleryname);
            evname=itemView.findViewById(R.id.theventname);
            evblock=itemView.findViewById(R.id.thblockname);
            evfloor=itemView.findViewById(R.id.thfloorno);
            evperiod=itemView.findViewById(R.id.thperiod);
            evdate=itemView.findViewById(R.id.thdateview);
            mybookingslayout=itemView.findViewById(R.id.mybookingfile);
        }
    }
    public MyBookings() {
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
                userid=firebaseAuth.getCurrentUser().getUid();
                query=databaseReference.orderByChild("UserID").equalTo(userid);
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        mybookingsviewholder.ename.setText(mybookingsView.getGalleryName());
                        mybookingsviewholder.evname.setText(mybookingsView.getEventName());
                        mybookingsviewholder.evfloor.setText(mybookingsView.getGalleryFloor());
                        mybookingsviewholder.evblock.setText(mybookingsView.getGalleryBlock());
                        mybookingsviewholder.evperiod.setText(mybookingsView.getPeriod());
                        mybookingsviewholder.evdate.setText(mybookingsView.getDate());

                        mybookingsviewholder.mybookingslayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                bookingid=mybookingsView.getBookingID().toString();

                                AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                                builder.setTitle("Event Cancelation");
                                builder.setIcon(R.drawable.cancel);
                                builder.setMessage("Are you Sure You want to Cancel this Booking,To Cancel your Booking click Yes");
                                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        del=databaseReference.orderByChild("BookingID").equalTo(bookingid);
                                        del.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                if (dataSnapshot.exists()){
                                                    for (DataSnapshot dele:dataSnapshot.getChildren()){
                                                        dele.getRef().removeValue();
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                                Toast.makeText(getActivity(), ""+databaseError.toException().toString(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                });
                                builder.show();
                            }
                        });
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
        mybook= inflater.inflate(R.layout.fragment_my_bookings, container, false);

        firebaseAuth=FirebaseAuth.getInstance();

        databaseReference=FirebaseDatabase.getInstance().getReference("Bookings");

        mRecyclerView=mybook.findViewById(R.id.mybookings);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return mybook;
    }


}
