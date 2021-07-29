package com.example.project;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.service.autofill.Dataset;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.model.UsersModel;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class RemoveUser extends Fragment {

    View remove;
    TextInputEditText search;
    ListView listView;
    DatabaseReference remDatabaseReference;
    SearchView searchView;
    Spinner spinner;
    String searchby,uname,select,mail;
    FirebaseRecyclerAdapter<UsersModel,RemoveUser.removeviewholder> adapter;
    RecyclerView remrRecyclerView;
    FirebaseRecyclerOptions<UsersModel> options;

    public static class removeviewholder extends RecyclerView.ViewHolder{
        TextView name, email, phone, type,bloc;
        LinearLayout layout;
        public removeviewholder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.removename);
            email = itemView.findViewById(R.id.removeid);
            type = itemView.findViewById(R.id.removetype);
            layout=itemView.findViewById(R.id.deleteuser);
            phone = itemView.findViewById(R.id.removephone);
            bloc=itemView.findViewById(R.id.removeblockname);

        }
    }

    public RemoveUser() {
        // Required empty public constructor
    }

   @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        remove = inflater.inflate(R.layout.fragment_remove_user, container, false);

        spinner=remove.findViewById(R.id.searchuserby);
        remrRecyclerView=remove.findViewById(R.id.removerecycler);
        remrRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               select=parent.getItemAtPosition(position).toString();
               if (select.equals("Name")){
                   searchby="Name";
                   searchView.setInputType(InputType.TYPE_CLASS_TEXT);
                   searchView.setQueryHint("Enter Name");
               }else if (select.equals("Email")){
                   searchby="Email";
                   searchView.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                   searchView.setQueryHint("Enter Email Address");
               }else if (select.equals("PhoneNo")){
                   searchby="Phone";
                   searchView.setInputType(InputType.TYPE_CLASS_PHONE);
                   searchView.setQueryHint("Enter Phone Number");
               }else if (select.equals("UserType")){
                   searchby="type";
                   searchView.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
                   searchView.setQueryHint("Enter UserType");
               }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //search = remove.findViewById(R.id.searchname);
        searchView=remove.findViewById(R.id.search);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                uname=query;
                DatabaseReference ss=FirebaseDatabase.getInstance().getReference("Users");
                final Query query1=ss.orderByChild(searchby).equalTo(uname);

                query1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){
                            options=new FirebaseRecyclerOptions.Builder<UsersModel>()
                                    .setQuery(query1,UsersModel.class)
                                    .build();

                            adapter=new FirebaseRecyclerAdapter<UsersModel, RemoveUser.removeviewholder>(options) {
                                @Override
                                protected void onBindViewHolder(@NonNull RemoveUser.removeviewholder removeviewholder, int i, @NonNull final UsersModel usersModel) {
                                    removeviewholder.name.setText(usersModel.getName());
                                    removeviewholder.phone.setText(usersModel.getPhone());
                                    removeviewholder.email.setText(usersModel.getEmail());
                                    removeviewholder.type.setText(usersModel.getType());
                                    removeviewholder.bloc.setText(usersModel.getBlock());

                                    removeviewholder.layout.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            mail=usersModel.getEmail().toString();
                                            String nn=usersModel.getName().toString();

                                            AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                                            builder.setTitle("Delete User");
                                            builder.setIcon(R.drawable.ic_delete_black_24dp);
                                            builder.setMessage("Deleting the User while revoke all the Permissions from the User with Name : "+nn+" and Email :"+mail);
                                            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    final DatabaseReference removedata=FirebaseDatabase.getInstance().getReference("Users");
                                                    final Query query2=removedata.orderByChild("Email").equalTo(mail);

                                                    query2.addValueEventListener(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                            if (dataSnapshot.exists()) {
                                                                for (DataSnapshot dele : dataSnapshot.getChildren()) {
                                                                    dele.getRef().removeValue();
                                                                }
                                                            }
                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError databaseError) {

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
                                public RemoveUser.removeviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                                    View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.removeuser_file,parent,false);
                                    return new RemoveUser.removeviewholder(v);
                                }
                            };

                            remrRecyclerView.setAdapter(adapter);
                            adapter.startListening();
                        }else {
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

        remDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        return remove;
    }

}