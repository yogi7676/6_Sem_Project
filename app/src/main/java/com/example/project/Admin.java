package com.example.project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.example.project.model.CompletedEventsModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.internal.NavigationMenu;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

public class Admin extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayout;
    TextView title,name,mail;
    ImageView logoff;
    public FirebaseAuth fAuth;
    String a,b;
    NavController navController;
    DatabaseReference databaseReference,completeddb;

    @Override
    protected void onStart() {
        super.onStart();

        completeddb=FirebaseDatabase.getInstance().getReference().child("CompletedEvents");
        databaseReference=FirebaseDatabase.getInstance().getReference().child("Bookings");
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
                                                            Toast.makeText(Admin.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
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
        setContentView(R.layout.activity_admin);

       drawerLayout=findViewById(R.id.drawerlayout);
       findViewById(R.id.imagemenu).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               drawerLayout.openDrawer(GravityCompat.START);
           }
       });

        NavigationView navigationView=findViewById(R.id.navigationview);
        navigationView.setItemIconTintList(null);

        navController= Navigation.findNavController(Admin.this,R.id.navHostFragment);
        NavigationUI.setupWithNavController(navigationView,navController);

        title=findViewById(R.id.texttitle);
        logoff=findViewById(R.id.getout);

        fAuth=FirebaseAuth.getInstance();

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
               title.setText(destination.getLabel());
            }
        });

        logoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder=new AlertDialog.Builder(Admin.this);
                builder.setTitle("LOGOUT...")
                        .setMessage("Are you sure to Quit ?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                           fAuth.signOut();
                           Intent intent=new Intent(Admin.this,MainActivity.class);
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

        a=getIntent().getExtras().getString("username");
        b=getIntent().getExtras().getString("usermail");
        name.setText(a);
        mail.setText(b);

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
    {
        drawerLayout.closeDrawer(GravityCompat.START);
        return  true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.navigation_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
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

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp();
    }
}
