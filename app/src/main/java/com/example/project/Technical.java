package com.example.project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

public class Technical extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout tdrawerLayout;
    TextView tname,tmail,ttitle;
    ImageView tlogout,timagemenu;
    FirebaseAuth fAuth;
    NavigationView tnavigationview;
    NavController tnavController;
    String name,mail;
    DatabaseReference databaseReference,completeddb;

    @Override
    protected void onStart() {
        super.onStart();

        completeddb=FirebaseDatabase.getInstance().getReference().child("CompletedEvents");
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Bookings");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot abc:dataSnapshot.getChildren()){
                        String qdate=abc.child("Date").getValue().toString();
                        try {
                            //first four lines shows current date;
                            Date date=new Date();
                            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
                            String string=format.format(date);
                            Date currentdate=new SimpleDateFormat("yyyy-MM-dd").parse(string);
                            Date dbdate=new SimpleDateFormat("yyyy-MM-dd").parse(qdate);

                            if (currentdate.after(dbdate)){

                                Query remove=databaseReference.orderByChild("Date").equalTo(qdate);
                                remove.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists()){
                                            for (final DataSnapshot delpast:dataSnapshot.getChildren()){
                                                String galleryid=delpast.child("GalleryID").getValue().toString();
                                                String userid=delpast.child("UserID").getValue().toString();
                                                String galleryName=delpast.child("GalleryName").getValue().toString();
                                                String block=delpast.child("GalleryBlock").getValue().toString();
                                                String floor=delpast.child("GalleryFloor").getValue().toString();
                                                String username=delpast.child("UserName").getValue().toString();
                                                String phno=delpast.child("PhoneNo").getValue().toString();
                                                String date=delpast.child("Date").getValue().toString();
                                                String bookingId=delpast.child("BookingID").getValue().toString();
                                                String per=delpast.child("Period").getValue().toString();
                                                String Ename=delpast.child("EventName").getValue().toString();
                                                String Edepartment=delpast.child("EventDepartment").getValue().toString();
                                                String Ecoordinator=delpast.child("EventCoordinator").getValue().toString();
                                                String GuestName=delpast.child("GuestName").getValue().toString();
                                                String GCount=delpast.child("GuestCount").getValue().toString();

                                                Calendar calendar=Calendar.getInstance();
                                                String savecurrenttime;

                                                int max=100000000;
                                                int min=100000;
                                                int key;

                                                SimpleDateFormat currenttime=new SimpleDateFormat("HH:MM:SS aa");
                                                savecurrenttime=currenttime.format(calendar.getTime());

                                                Random random=new Random();

                                                key=min+random.nextInt(max);

                                                String newkey=Integer.toString(key);

                                                String finalkey=newkey+savecurrenttime;

                                                HashMap<String,Object> completed=new HashMap<>();
                                                completed.put("GalleryID",galleryid);
                                                completed.put("UserID",userid);
                                                completed.put("GalleryName",galleryName);
                                                completed.put("GalleryBlock",block);
                                                completed.put("GalleryFloor",floor);
                                                completed.put("UserName",username);
                                                completed.put("PhoneNo",phno);
                                                completed.put("Date",date);
                                                completed.put("BookingID",bookingId);
                                                completed.put("Period",per);
                                                completed.put("EventName",Ename);
                                                completed.put("EventDepartment",Edepartment);
                                                completed.put("EventCoordinator",Ecoordinator);
                                                completed.put("GuestName",GuestName);
                                                completed.put("GuestCount",GCount);

                                                completeddb.child(finalkey).updateChildren(completed).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful())
                                                        {
                                                            //delete data
                                                            delpast.getRef().removeValue();
                                                        }else{
                                                            Toast.makeText(Technical.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });

                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                        }catch (Exception e){

                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technical);

        tdrawerLayout=findViewById(R.id.technicaldrawerlayout);
        timagemenu=findViewById(R.id.technicalimagemenu);
        tlogout=findViewById(R.id.technicalgetout);
        fAuth=FirebaseAuth.getInstance();

        tnavigationview=findViewById(R.id.technicalnavigation);
        tnavigationview.setItemIconTintList(null);

        tnavController= Navigation.findNavController(this,R.id.technicalnavHostFragment);
        NavigationUI.setupWithNavController(tnavigationview,tnavController);

        ttitle=findViewById(R.id.technicaltexttitle);

        tnavController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                ttitle.setText(destination.getLabel());
            }
        });

        timagemenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tdrawerLayout.openDrawer(GravityCompat.START);
            }
        });

        View headerview=tnavigationview.getHeaderView(0);
        tname=headerview.findViewById(R.id.name);
        tmail=headerview.findViewById(R.id.mail);

        name=getIntent().getExtras().getString("username");
        mail=getIntent().getExtras().getString("usermail");

        tname.setText(name);
        tmail.setText(mail);


        tlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(Technical.this);
                builder.setTitle("LOGOUT ...")
                        .setMessage("Are you Sure to Quit ?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                           fAuth.signOut();
                           Intent intent=new Intent(Technical.this,MainActivity.class);
                           intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                           startActivity(intent);
                            }
                        });
                builder.show();
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void onBackPressed() {
        if (tdrawerLayout.isDrawerOpen(GravityCompat.START))
        {
            tdrawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }
}
