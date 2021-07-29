package com.example.project;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddgalleryFragment extends Fragment {


    public AddgalleryFragment() {
        // Required empty public constructor
    }

    private ImageView img;
    private EditText name,seats,floor;
    private Button done;
    private String savecurrentdate,savecurrenttime,productrandomkey;
    private static final int Open_Gallery=1;
    private Uri imageuri;
    private String gname,gseats,gfloor,downloadimageurl,item;
    private StorageReference productimagesref;
    private DatabaseReference productref;
    private ProgressDialog loadingbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View add=inflater.inflate(R.layout.fragment_addgallery, container, false);
        img=add.findViewById(R.id.galleryimage);
        name=add.findViewById(R.id.galleryname);
        seats=add.findViewById(R.id.galleryseats);
        done=add.findViewById(R.id.savegallery);
        floor=add.findViewById(R.id.floor);

        productimagesref= FirebaseStorage.getInstance().getReference().child("Product Images");
        productref=FirebaseDatabase.getInstance().getReference().child("Galleries");
        loadingbar=new ProgressDialog(getActivity());

        String[] values={"CBSE","ICSE","PUC","DEGREE/LAW"};
        Spinner spinner=(Spinner)add.findViewById(R.id.galleryblock);
        ArrayAdapter<String> adapter= new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item,values);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);

    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                item=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                Toast.makeText(getActivity(), "Please select block", Toast.LENGTH_SHORT).show();
            }
    });


    img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectimage();
            }
        });

    done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validatedata();
            }
        });
        return add;
    }


    private void validatedata()
    {
        gname=name.getText().toString();
        gseats=seats.getText().toString();
        gfloor=floor.getText().toString();

        if (imageuri==null)
        {
            Toast.makeText(getActivity(), "Image Required", Toast.LENGTH_SHORT).show();
        }else if (gname.isEmpty())
        {
            name.setError("Name Required");
            name.setText("");
            name.requestFocus();
        }else if (gseats.isEmpty())
        {
            seats.setError("Please enter no of Seats available");
            seats.setText("");
            seats.requestFocus();
        }else if (gfloor.isEmpty())
        {
            floor.setText("");
            floor.setError("Enter Floor Number");
            floor.requestFocus();
        }else{
            saveinformation();
        }
    }

    private void saveinformation()
    {
        loadingbar.setTitle("Adding Gallery");
        loadingbar.setMessage("Dear Admin,Please Wait while we are adding the Gallery");
        loadingbar.setCanceledOnTouchOutside(false);
        loadingbar.show();
        Calendar calendar=Calendar.getInstance();

        SimpleDateFormat currentdate=new SimpleDateFormat("MMM dd,yyyy");
        savecurrentdate=currentdate.format(calendar.getTime());

        SimpleDateFormat currenttime=new SimpleDateFormat("HH:MM:SS a");
        savecurrenttime=currenttime.format(calendar.getTime());

        productrandomkey=savecurrentdate+savecurrenttime;

        final StorageReference filepath=productimagesref.child(imageuri.getLastPathSegment()+productrandomkey+".jpg");

        final UploadTask uploadtask=filepath.putFile(imageuri);

        uploadtask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message=e.toString();
                Toast.makeText(getActivity(), "Error :"+message, Toast.LENGTH_SHORT).show();
                loadingbar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getActivity(), "Image uploaded successfully", Toast.LENGTH_SHORT).show();

                Task<Uri> uriTask=uploadtask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful())
                        {
                            throw task.getException();
                        }
                        downloadimageurl=filepath.getDownloadUrl().toString();
                        return filepath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful())
                    {
                        downloadimageurl=task.getResult().toString();

                        Toast.makeText(getActivity(), "Image URL Acquired", Toast.LENGTH_SHORT).show();
                        saveproductinfotodatabase();
                    }
                    }
                });
            }
        });
    }

    private void saveproductinfotodatabase()
    {
        HashMap<String,Object> productmap=new HashMap<>();
        productmap.put("Pid",productrandomkey);
        productmap.put("Date",savecurrentdate);
        productmap.put("Time",savecurrenttime);
        productmap.put("Name",gname);
        productmap.put("Seats",gseats);
        productmap.put("Block",item);
        productmap.put("Floor",gfloor);
        productmap.put("Image",downloadimageurl);

        productref.child(productrandomkey).updateChildren(productmap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
            if (task.isSuccessful())
            {
                loadingbar.dismiss();
                Toast.makeText(getActivity(), "Gallery Added Successfully", Toast.LENGTH_SHORT).show();
                img.setImageResource(R.drawable.ic_camera_alt_black_24dp);
                name.setText("");
                seats.setText("");
                floor.setText("");
            }
            else
            {
                loadingbar.dismiss();
                String message=task.getException().toString();
                Toast.makeText(getActivity(), "Error : "+ message, Toast.LENGTH_SHORT).show();
                name.setText("");
                seats.setText("");
                floor.setText("");
            }
            }
        });
    }

    private void selectimage()
    {
        Intent galleryintent=new Intent();
        galleryintent.setAction(Intent.ACTION_GET_CONTENT);
        galleryintent.setType("image/*");
        startActivityForResult(galleryintent,Open_Gallery);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==Open_Gallery && resultCode== Activity.RESULT_OK && data!=null)
        {
            imageuri=data.getData();
            img.setImageURI(imageuri);
        }
    }
}
