package com.example.project;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class bookgallerycheckout extends Fragment {

    TextView dname,dseats,dblock,dfloor,dedate,dperiod;
    EditText ename,eguests,eguestname,edepart,transportation,others,ecoord;
    DatabaseReference databaseReference,bookingdb,userdb;
    ImageView dimageview;
    Button book;
    HashMap<String,Object> finaluser;
    ProgressDialog progressDialog;
    String eventname,eventguest,evendepartment,noofguests,eco,gname,phoneno;
    String strans,sothers,userid,ddate,period,galleryid;
    int max=100000000;
    int min=100000;
    int key;
    RadioGroup food,laptop,projector,chairs,bells,remuneration,sapling,roses,bouquet,momento,stand,cord,colar,water;
    RadioButton radioButton,radio,button,rchairs,rbells,rrem,rsap,rrose,rbouq,rmom,rstand,rcord,rcolar,rwater;
    int fid,lid,proid,cid,bid,rid,sid,roid,boid,mid,stid,cordid,colid,wid;
    String ff,ll,pp,cc,bb,rr,ss,roro,bobo,mm,stst,coco,clcl,ww;
    String name,floor,seats,block,image;
    View checkout;


    public bookgallerycheckout() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        checkout=inflater.inflate(R.layout.fragment_fragment__checkout, container, false);

        // four lines retreiving data from last page;
        ddate=bookgallerycheckoutArgs.fromBundle(getArguments()).getDate();
        period=bookgallerycheckoutArgs.fromBundle(getArguments()).getPeriod();
        galleryid=bookgallerycheckoutArgs.fromBundle(getArguments()).getGalleryid();
        userid=bookgallerycheckoutArgs.fromBundle(getArguments()).getUserid();

        food=checkout.findViewById(R.id.Food);
        laptop=checkout.findViewById(R.id.laptop);
        projector=checkout.findViewById(R.id.projector);
        chairs=checkout.findViewById(R.id.rchairs);
        bells=checkout.findViewById(R.id.rbells);
        momento=checkout.findViewById(R.id.rmomen);
        remuneration=checkout.findViewById(R.id.rremuneration);
        sapling=checkout.findViewById(R.id.rsapling);
        roses=checkout.findViewById(R.id.rroses);
        bouquet=checkout.findViewById(R.id.rbouq);
        water=checkout.findViewById(R.id.rwater);
        stand=checkout.findViewById(R.id.rstandmike);
        cord=checkout.findViewById(R.id.rcordlessmike);
        colar=checkout.findViewById(R.id.rcolarmike);

        dname=checkout.findViewById(R.id.detailtitle);
        dfloor=checkout.findViewById(R.id.detailfloor);
        dseats=checkout.findViewById(R.id.detailseats);
        dblock=checkout.findViewById(R.id.detailblock);
        dedate=checkout.findViewById(R.id.detailprintdate);
        dimageview=checkout.findViewById(R.id.detailimage);

        ename=checkout.findViewById(R.id.eventname);
        edepart=checkout.findViewById(R.id.departmentname);
        eguestname=checkout.findViewById(R.id.guestname);
        eguests=checkout.findViewById(R.id.noofguests);
        ecoord=checkout.findViewById(R.id.eventcoordinator);

        transportation=checkout.findViewById(R.id.texttransport);
        others=checkout.findViewById(R.id.textextra);

        dname=checkout.findViewById(R.id.detailtitle);
        dfloor=checkout.findViewById(R.id.detailfloor);
        dseats=checkout.findViewById(R.id.detailseats);
        dblock=checkout.findViewById(R.id.detailblock);
        dedate=checkout.findViewById(R.id.detailprintdate);
        dimageview=checkout.findViewById(R.id.detailimage);
        dperiod=checkout.findViewById(R.id.detailperiod);
        finaluser=new HashMap<>();

        bookingdb=FirebaseDatabase.getInstance().getReference().child("Bookings");

        book=checkout.findViewById(R.id.finalbutton);
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });
        //working
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Galleries").child(galleryid);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                name=dataSnapshot.child("Name").getValue().toString();
                floor=dataSnapshot.child("Floor").getValue().toString();
                seats=dataSnapshot.child("Seats").getValue().toString();
                block=dataSnapshot.child("Block").getValue().toString();
                image=dataSnapshot.child("Image").getValue().toString();

                dname.setText(name);
                dfloor.setText("Floor: "+floor);
                dseats.setText(seats);
                dblock.setText(block);
                dedate.setText(ddate);
                dperiod.setText(period);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //working
        userdb=FirebaseDatabase.getInstance().getReference().child("Users").child(userid);
        userdb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                gname=dataSnapshot.child("Name").getValue().toString();
                phoneno=dataSnapshot.child("Phone").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return checkout;
    }

    public void validate(){
        book=checkout.findViewById(R.id.finalbutton);

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventname=ename.getText().toString();
                evendepartment=edepart.getText().toString();
                noofguests=eguests.getText().toString();
                eventguest=eguestname.getText().toString();
                eco=ecoord.getText().toString();

                strans=transportation.getText().toString();
                sothers=others.getText().toString();

                fid=food.getCheckedRadioButtonId();
                radioButton=checkout.findViewById(fid);
                ff=radioButton.getText().toString();

                lid=laptop.getCheckedRadioButtonId();
                radio=checkout.findViewById(lid);
                ll=radio.getText().toString();

                proid=projector.getCheckedRadioButtonId();
                button=checkout.findViewById(proid);
                pp=button.getText().toString();

                cid=chairs.getCheckedRadioButtonId();
                rchairs=checkout.findViewById(cid);
                cc=rchairs.getText().toString();

                bid=bells.getCheckedRadioButtonId();
                rbells=checkout.findViewById(bid);
                bb=rbells.getText().toString();

                rid=remuneration.getCheckedRadioButtonId();
                rrem=checkout.findViewById(rid);
                rr=rrem.getText().toString();

                sid=sapling.getCheckedRadioButtonId();
                rsap=checkout.findViewById(sid);
                ss=rsap.getText().toString();

                roid=roses.getCheckedRadioButtonId();
                rrose=checkout.findViewById(roid);
                roro=rrose.getText().toString();

                boid=bouquet.getCheckedRadioButtonId();
                rbouq=checkout.findViewById(boid);
                bobo=rbouq.getText().toString();

                mid=momento.getCheckedRadioButtonId();
                rmom=checkout.findViewById(mid);
                mm=rmom.getText().toString();

                stid=stand.getCheckedRadioButtonId();
                rstand=checkout.findViewById(stid);
                stst=rstand.getText().toString();

                cordid=cord.getCheckedRadioButtonId();
                rcord=checkout.findViewById(cordid);
                coco=rcord.getText().toString();

                colid=colar.getCheckedRadioButtonId();
                rcolar=checkout.findViewById(colid);
                clcl=rcolar.getText().toString();

                wid=water.getCheckedRadioButtonId();
                rwater=checkout.findViewById(wid);
                ww=rwater.getText().toString();

                if (evendepartment.length()==0){
                    edepart.setError("Enter Department Name");
                    edepart.requestFocus();
                }else if (eventname.length()==0){
                    ename.setError("Enter Event Name");
                    ename.requestFocus();
                }else if (eco.length()==0){
                    ecoord.setError("Please Enter Event Coordinator Name");
                    ecoord.requestFocus();
                } else{
                    progressDialog=new ProgressDialog(getActivity());
                    progressDialog.setTitle("Booking Gallery");
                    progressDialog.setMessage("Dear User,Please Wait while we are confirming your Request");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    Calendar calendar=Calendar.getInstance();
                    String savecurrenttime;

                    SimpleDateFormat currenttime=new SimpleDateFormat("HH:MM:SS aa");
                    savecurrenttime=currenttime.format(calendar.getTime());

                    Random random=new Random();

                    key=min+random.nextInt(max);

                    String newkey=Integer.toString(key);

                    String finalkey=newkey+savecurrenttime;

                    finaluser.put("GalleryID",galleryid);
                    finaluser.put("UserID",userid);
                    finaluser.put("GalleryName",name);
                    finaluser.put("GalleryFloor",floor);
                    finaluser.put("GalleryBlock",block);
                    finaluser.put("UserName",gname);
                    finaluser.put("PhoneNo",phoneno);
                    finaluser.put("Period",period);
                    finaluser.put("Date",ddate);
                    finaluser.put("BookingID",finalkey);
                    finaluser.put("EventName",eventname);
                    finaluser.put("EventDepartment",evendepartment);
                    finaluser.put("EventCoordinator",eco);
                    finaluser.put("GuestName",eventguest);
                    finaluser.put("GuestCount",noofguests);
                    finaluser.put("Chairs",cc);
                    finaluser.put("Bells",bb);
                    finaluser.put("Remuneration",rr);
                    finaluser.put("Sapling",ss);
                    finaluser.put("Rose",roro);
                    finaluser.put("Bouquet",bobo);
                    finaluser.put("Momento",mm);
                    finaluser.put("StandMike",stst);
                    finaluser.put("Cordless",coco);
                    finaluser.put("Colar",clcl);
                    finaluser.put("WaterBottles",ww);
                    finaluser.put("Transportation",strans);
                    finaluser.put("Others",sothers);
                    finaluser.put("Laptop",ll);
                    finaluser.put("Projector",pp);
                    finaluser.put("FoodSnacks",ff);
                    finaluser.put("Status","Booked");

                    bookingdb.child(finalkey).updateChildren(finaluser).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful())
                            {
                              /*  AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                                builder.setTitle("Proceed..")
                                        .setMessage("Dear User,Please Click Ok to Book Gallery and Restart")
                                        .setCancelable(false)
                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Toast.makeText(getActivity(), "Successfully Booked", Toast.LENGTH_SHORT).show();
                                                progressDialog.dismiss();
                                                Intent intent=new Intent(getActivity(),MainActivity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(intent);
                                            }
                                        });
                                builder.show();*/

                                Toast.makeText(getActivity(), "Auditorium Booked Successfully,Please Check your Bookings", Toast.LENGTH_SHORT).show();
                                Navigation.findNavController(checkout).navigateUp();
                                progressDialog.dismiss();
                            }else{
                                Toast.makeText(getActivity(), "Error :"+task.getException().getMessage().toString(), Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        }
                    });

                }
            }
        });
    }
    public void clearall()
    {
        transportation.setText("");
        others.setText("");

        food.clearCheck();
        laptop.clearCheck();
        projector.clearCheck();
        chairs.clearCheck();;
        bells.clearCheck();
        momento.clearCheck();
        remuneration.clearCheck();
        sapling.clearCheck();
        roses.clearCheck();
        bouquet.clearCheck();
        water.clearCheck();
        stand.clearCheck();
        cord.clearCheck();
        colar.clearCheck();
    }

}
