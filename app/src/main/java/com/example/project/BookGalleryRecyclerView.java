package com.example.project;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.service.autofill.Dataset;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.model.Galleriesmodel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Vector;


/**
 * A simple {@link Fragment} subclass.
 */
public class BookGalleryRecyclerView extends Fragment {

    private RecyclerView recyclerView;
    private DatabaseReference databaseReference,bookingdb;
    String galleryid;
    View some;
    String date,period;
    String userid;
    private int counter;
    List<String> ds;
    String qdate;

    public static class BookViewHolder extends  RecyclerView.ViewHolder
    {
        TextView title,seats,block,floor;
        ImageView imageView;
        LinearLayout senddetails;

        public BookViewHolder(@NonNull View itemView)
        {
            super(itemView);

            title=itemView.findViewById(R.id.galleryviewTitle);
            seats=itemView.findViewById(R.id.galleryviewnumberseats);
            block=itemView.findViewById(R.id.galleryviewblock);
            floor=itemView.findViewById(R.id.galleryviewfloor);
            imageView=itemView.findViewById(R.id.galleryviewimage);
            senddetails=itemView.findViewById(R.id.listdetails);
        }
    }


    public BookGalleryRecyclerView() {
        // Required empty public constructor
    }


    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Galleriesmodel> options= new FirebaseRecyclerOptions.Builder<Galleriesmodel>()
                .setQuery(databaseReference,Galleriesmodel.class)
                .build();

        FirebaseRecyclerAdapter<Galleriesmodel, BookGalleryRecyclerView.BookViewHolder> adapter=new FirebaseRecyclerAdapter<Galleriesmodel, BookGalleryRecyclerView.BookViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final BookGalleryRecyclerView.BookViewHolder bookViewHolder, final int i, @NonNull final Galleriesmodel galleriesmodel)
            {
                bookViewHolder.title.setText(galleriesmodel.getName());
                bookViewHolder.floor.setText("Floor No :" +galleriesmodel.getFloor() +"");
                bookViewHolder.seats.setText(galleriesmodel.getSeats());
                bookViewHolder.block.setText(galleriesmodel.getBlock());
                Picasso.get().load(galleriesmodel.getImage()).into(bookViewHolder.imageView);

                bookViewHolder.senddetails.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //pass from view to details
                        galleryid=galleriesmodel.getPid().toString();
                        bookingdb=FirebaseDatabase.getInstance().getReference("Bookings");
                        Query query=bookingdb.orderByChild("GalleryID").equalTo(galleryid);

                        ds=new ArrayList<>();
                        query.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()){
                                    ds.clear();
                                    for (DataSnapshot data:dataSnapshot.getChildren()){
                                        qdate=data.child("Date").getValue().toString();
                                        String qperiod=data.child("Period").getValue().toString();
                                        counter=(int) data.getChildrenCount();
                                        ds.add(qperiod);
                                    }
                                    try {
                                        Date dbdate,selectdate;
                                        dbdate=new SimpleDateFormat("yyyy-MM-dd").parse(qdate);
                                        selectdate=new SimpleDateFormat("yyyy-MM-dd").parse(date);

                                        if (dbdate.equals(selectdate)){
                                            for (int i=0;i<=ds.size();i++){
                                                if (ds.contains(period)){
                                                   AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                                                   builder.setTitle("Event Information");
                                                   builder.setIcon(R.drawable.fault);
                                                   builder.setMessage("Dear User,There is another Event During the Selected Period,Please try selecting Other Auditorium/Date/Period");
                                                   builder.show();
                                                }else{
                                                    NavDirections directions=BookGalleryRecyclerViewDirections.actionbookgalleryviewwtofinal(galleryid,userid,date,period);
                                                    Navigation.findNavController(some).navigate(directions);
                                                }
                                            }
                                        }else if (!dbdate.equals(selectdate)){
                                            NavDirections directions=BookGalleryRecyclerViewDirections.actionbookgalleryviewwtofinal(galleryid,userid,date,period);
                                            Navigation.findNavController(some).navigate(directions);                                            }
                                    }catch (Exception e){

                                    }
                                }else if (!dataSnapshot.exists()){
                                    NavDirections directions=BookGalleryRecyclerViewDirections.actionbookgalleryviewwtofinal(galleryid,userid,date,period);
                                    Navigation.findNavController(some).navigate(directions);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                });


            }

            @NonNull
            @Override
            public BookGalleryRecyclerView.BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
            {
                View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.list_layout,parent,false);

                return new BookGalleryRecyclerView.BookViewHolder(view);
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        some=inflater.inflate(R.layout.fragment_book_gallery_recycler_view, container, false);

        databaseReference= FirebaseDatabase.getInstance().getReference().child("Galleries");

        date=BookGalleryRecyclerViewArgs.fromBundle(getArguments()).getDate();
        period=BookGalleryRecyclerViewArgs.fromBundle(getArguments()).getPeriod();
        userid=BookGalleryRecyclerViewArgs.fromBundle(getArguments()).getUserid();

        recyclerView=some.findViewById(R.id.bookrecycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return some;
    }

}
