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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.model.Galleriesmodel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class DeleteGallery extends Fragment {
    View delete;
    DatabaseReference fdatabase;
    RecyclerView delrecRecyclerView;
    FirebaseStorage firebaseStorage;

    public  static  class  deleteviewholder extends RecyclerView.ViewHolder
    {
        TextView title,seats,block,floor;
        ImageView imageView;
        LinearLayout senddetails;

        public deleteviewholder(@NonNull View itemView) {
            super(itemView);

            title=itemView.findViewById(R.id.galleryviewTitle);
            seats=itemView.findViewById(R.id.galleryviewnumberseats);
            block=itemView.findViewById(R.id.galleryviewblock);
            floor=itemView.findViewById(R.id.galleryviewfloor);
            imageView=itemView.findViewById(R.id.galleryviewimage);
            senddetails=itemView.findViewById(R.id.listdetails);
        }
    }

    public DeleteGallery() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Galleriesmodel> options=new FirebaseRecyclerOptions.Builder<Galleriesmodel>()
                .setQuery(fdatabase,Galleriesmodel.class)
                .build();

        FirebaseRecyclerAdapter<Galleriesmodel,DeleteGallery.deleteviewholder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Galleriesmodel, DeleteGallery.deleteviewholder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull DeleteGallery.deleteviewholder deleteviewholder, int i, @NonNull final Galleriesmodel galleriesmodel) {
                deleteviewholder.title.setText(galleriesmodel.getName());
                deleteviewholder.seats.setText(galleriesmodel.getSeats());
                deleteviewholder.block.setText(galleriesmodel.getBlock());
                deleteviewholder.floor.setText("Floor No : "+galleriesmodel.getFloor());
                Picasso.get().load(galleriesmodel.getImage()).into(deleteviewholder.imageView);

                deleteviewholder.senddetails.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String  gid,imageid;
                        gid=galleriesmodel.getPid().toString();
                        imageid=galleriesmodel.getImage().toString();

                        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                        builder.setTitle("Delete Gallery");
                        builder.setIcon(R.drawable.ic_delete_black_24dp);
                        builder.setMessage("Are You Sure to Delete");
                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                StorageReference storageReference=firebaseStorage.getReferenceFromUrl(imageid);
                                storageReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            Query a;
                                            a=fdatabase.orderByChild("Pid").equalTo(gid);

                                            a.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    if (dataSnapshot.exists()){
                                                        for (DataSnapshot remove:dataSnapshot.getChildren()){
                                                            remove.getRef().removeValue();
                                                            Toast.makeText(getActivity(), "Gallery Deleted Successfully", Toast.LENGTH_SHORT).show();

                                                        }
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });
                                        }
                                    }
                                });
                            }
                        });
                    builder.show();
                    }
                });
            }

            @NonNull
            @Override
            public DeleteGallery.deleteviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.list_layout,parent,false);
                return new DeleteGallery.deleteviewholder(v);
            }
        };

        delrecRecyclerView.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        delete=inflater.inflate(R.layout.fragment_delete_gallery, container, false);

        fdatabase=FirebaseDatabase.getInstance().getReference("Galleries");
        delrecRecyclerView=delete.findViewById(R.id.deleterecyclerview);
        delrecRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        firebaseStorage=FirebaseStorage.getInstance();

        return delete;
    }

}
