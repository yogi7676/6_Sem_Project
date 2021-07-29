package com.example.project;


import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.model.UsersModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateUser extends Fragment {

    private RecyclerView mreRecyclerView;
    private DatabaseReference databaseReference;
    View Userremove;
    private SearchView searchView;
    String name,selection,method;
    Spinner spinner;
    public static class removeviewholder extends RecyclerView.ViewHolder{
        TextView name, email, phone, type,block;
        LinearLayout layout;
        public removeviewholder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.removename);
            email = itemView.findViewById(R.id.removeid);
            type = itemView.findViewById(R.id.removetype);
            layout=itemView.findViewById(R.id.deleteuser);
            phone = itemView.findViewById(R.id.removephone);
            block=itemView.findViewById(R.id.removeblockname);
                    }
    }

    public UpdateUser() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Userremove=inflater.inflate(R.layout.fragment_update_user, container, false);

        mreRecyclerView=Userremove.findViewById(R.id.removeuserrecycler);
        mreRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        searchView=Userremove.findViewById(R.id.removesearch);
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Users");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                name=query;
                final Query query1=databaseReference.orderByChild(method).equalTo(name);

                query1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){
                            FirebaseRecyclerOptions<UsersModel> options=new FirebaseRecyclerOptions.Builder<UsersModel>()
                                    .setQuery(query1,UsersModel.class)
                                    .build();

                            FirebaseRecyclerAdapter<UsersModel,UpdateUser.removeviewholder> adapter=new FirebaseRecyclerAdapter<UsersModel, UpdateUser.removeviewholder>(options) {
                                @Override
                                protected void onBindViewHolder(@NonNull UpdateUser.removeviewholder removeviewholder, int i, @NonNull final UsersModel usersModel) {
                                    removeviewholder.name.setText(usersModel.getName());
                                    removeviewholder.email.setText(usersModel.getEmail());
                                    removeviewholder.block.setText(usersModel.getBlock());
                                    removeviewholder.type.setText(usersModel.getType());
                                    removeviewholder.phone.setText(usersModel.getType());

                                    removeviewholder.layout.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            String mail=usersModel.getUID();

                                            NavDirections directions=UpdateUserDirections.actionupdateusertoupdateuserfinal(mail);
                                            Navigation.findNavController(Userremove).navigate(directions);
                                        }
                                    });
                                }

                                @NonNull
                                @Override
                                public UpdateUser.removeviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                                    View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.removeuser_file,parent,false);
                                    return new UpdateUser.removeviewholder(v);
                                }
                            };

                            mreRecyclerView.setAdapter(adapter);
                            adapter.startListening();

                        }else{
                            AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                            builder.setTitle("User Information");
                            builder.setIcon(R.drawable.fault);
                            builder.setMessage("Dear User,There are No Matches to your Seacrh Entry");
                            builder.setCancelable(true);
                            builder.show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

              return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        spinner=Userremove.findViewById(R.id.updateuserspinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selection=parent.getItemAtPosition(position).toString();
                if (selection.equals("Name")){
                    method="Name";
                    searchView.setInputType(InputType.TYPE_CLASS_TEXT);
                    searchView.setQueryHint("Enter Name");
                }else if (selection.equals("Email")){
                    method="Email";
                    searchView.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                    searchView.setQueryHint("Enter Email Address");
                }else if (selection.equals("PhoneNo")){
                    method="Phone";
                    searchView.setInputType(InputType.TYPE_CLASS_PHONE);
                    searchView.setQueryHint("Enter Phone Number");
                }else if (selection.equals("UserType")){
                    method="type";
                    searchView.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
                    searchView.setQueryHint("Enter UserType");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return Userremove;
    }

}
