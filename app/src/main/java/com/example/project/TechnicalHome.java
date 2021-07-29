package com.example.project;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.model.Technical_model;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class TechnicalHome extends Fragment {
    View home;
    RecyclerView tRecyclerView;
    DatabaseReference tDatabaseReference;
    String userid,galleryid;
    String name,phoneno,gname,gfloor,gblock;

    public TechnicalHome() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Technical_model> options=new FirebaseRecyclerOptions.Builder<Technical_model>()
                .setQuery(tDatabaseReference,Technical_model.class)
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

                viewHolder.technicallayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        NavDirections directions=TechnicalHomeDirections.hometofinal(technical_model.getBookingID());
                        Navigation.findNavController(home).navigate(directions);

                    }
                });

            }

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.technical_view_file,parent,false);

                return new TechnicalHome.ViewHolder(view);
            }
        };
        tRecyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    public static  class ViewHolder extends  RecyclerView.ViewHolder
    {
        TextView name,ename,edepart,date,ftime,ttime,floor,block,username,userphone,coord;
        LinearLayout technicallayout;
        TextView tuid,tgid;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.tgalleryname);
            ename=itemView.findViewById(R.id.teventname);
            edepart=itemView.findViewById(R.id.tdepartmentname);
            date=itemView.findViewById(R.id.tdateview);
            ftime=itemView.findViewById(R.id.tstarttime);
            ttime=itemView.findViewById(R.id.tendtime);
            floor=itemView.findViewById(R.id.tfloorno);
            block=itemView.findViewById(R.id.tblockname);
            username=itemView.findViewById(R.id.tstaffname);
            userphone=itemView.findViewById(R.id.tphonenumber);
            tuid=itemView.findViewById(R.id.tuserid);
            tgid=itemView.findViewById(R.id.tgalleryid);
            coord=itemView.findViewById(R.id.tdcoordinator);

            technicallayout=itemView.findViewById(R.id.technicalview);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        home=inflater.inflate(R.layout.fragment_technical_home, container, false);

        tRecyclerView=home.findViewById(R.id.technicalrecycler);
        tRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        tDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Bookings");

        return home;
    }

}
