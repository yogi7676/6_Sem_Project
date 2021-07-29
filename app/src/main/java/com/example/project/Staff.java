package com.example.project;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

public class Staff extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    DrawerLayout drawerLayout;
    TextView title,name,mail;
    ImageView stafflogout;
    FirebaseAuth fAuth;
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
                                                //String from=delpast.child("Fromtime").getValue().toString();

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
                                                            Toast.makeText(Staff.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff);

        drawerLayout=findViewById(R.id.staffdrawerlayout);
        findViewById(R.id.staffimagemenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        NavigationView navigationView=findViewById(R.id.staffnavigationview);
        navigationView.setItemIconTintList(null);

        NavController navController= Navigation.findNavController(this,R.id.staffnavHostFragment);
        NavigationUI.setupWithNavController(navigationView,navController);

        title=findViewById(R.id.stafftexttitle);
        stafflogout=findViewById(R.id.staffgetout);

        fAuth=FirebaseAuth.getInstance();

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                title.setText(destination.getLabel());
            }
        });

        stafflogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(Staff.this);
                builder.setTitle("LOGOUT...")
                        .setMessage("Are you sure to Quit ?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                     fAuth.signOut();
                                Intent intent =new Intent(Staff.this,MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        });
                builder.show();
            }
        });

        View headerview=navigationView.getHeaderView(0);
        name=headerview.findViewById(R.id.name);
        mail=headerview.findViewById(R.id.mail);

        final String a,b;
        a=getIntent().getExtras().getString("username");
        b=getIntent().getExtras().getString("usermail");
        name.setText(a);
        mail.setText(b);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }
}


