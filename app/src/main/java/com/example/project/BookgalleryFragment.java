package com.example.project;


import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.jhonnyx2012.horizontalpicker.DatePickerListener;
import com.github.jhonnyx2012.horizontalpicker.HorizontalPicker;
import com.google.firebase.auth.FirebaseAuth;

import org.joda.time.DateTime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class BookgalleryFragment extends Fragment implements DatePickerListener{
    String userid;
    FirebaseAuth firebaseAuth;
    private  View book;
    Button check;
    Spinner spinner;
    String status,subs;
    HorizontalPicker picker;
    String selected,selectedstring;

    public BookgalleryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        book=inflater.inflate(R.layout.fragment_bookgallery, container, false);
        // Inflate the layout for this fragment

        firebaseAuth=FirebaseAuth.getInstance();
        userid=firebaseAuth.getCurrentUser().getUid();

        spinner=book.findViewById(R.id.period);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected=parent.getItemAtPosition(position).toString();

                if (selected.equals("Please Select")){
                    //do nothing
                }else if (!selected.equals("Please Select")){
                    selectedstring=selected;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //datepicker
        picker=book.findViewById(R.id.datepicker);
        picker.setListener(this)
                .setDays(50)
                .setOffset(7)
                .setDateSelectedColor(Color.DKGRAY)
                .setDateSelectedTextColor(Color.WHITE)
                .setMonthAndYearTextColor(Color.DKGRAY)
                .setTodayButtonTextColor(getResources().getColor(R.color.colorPrimary))
                .setTodayDateBackgroundColor(getResources().getColor(R.color.colorPrimary))
                .setUnselectedDayTextColor(Color.DKGRAY)
                .setDayOfWeekTextColor(Color.DKGRAY)
                .setUnselectedDayTextColor(getResources().getColor(R.color.colorPrimary))
                .showTodayButton(false)
                .init();
        picker.setBackgroundColor(Color.LTGRAY);
        picker.setDate(new DateTime());

        check=book.findViewById(R.id.checkgalleriesbutton);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //On Clicking Button to check galleries
                        subs=status.substring(0,10);
                         try {
                                    Date date=new Date();
                                    SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
                                    String string=format.format(date);
                                    Date currentdate=new SimpleDateFormat("yyyy-MM-dd").parse(string);
                                    Date dbdate=new SimpleDateFormat("yyyy-MM-dd").parse(subs);

                                    if (selected.equals("Please Select")){
                                        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                                        builder.setTitle("Slot Information");
                                        builder.setIcon(R.drawable.fault);
                                        builder.setMessage("Dear User,Please Select Period");
                                        builder.setCancelable(true);
                                        builder.show();
                                    }else if (currentdate.after(dbdate)){
                                        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                                        builder.setTitle("Date Information");
                                        builder.setMessage("Dear User,You are not allowed to Book Galleries at earlier Dates");
                                        builder.setIcon(R.drawable.fault);
                                        builder.show();
                                    }else if (currentdate.before(dbdate)){
                                        NavDirections directions=BookgalleryFragmentDirections.actionbookgallerytoview(subs,userid,selectedstring);
                                        Navigation.findNavController(book).navigate(directions);
                                    }else if (currentdate.equals(dbdate)){
                                        NavDirections directions=BookgalleryFragmentDirections.actionbookgallerytoview(subs,userid,selectedstring);
                                        Navigation.findNavController(book).navigate(directions);
                                    }
                             }catch (Exception e){
                             Toast.makeText(getActivity(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                              }
                }
        });

        return book;
    }
    @Override
    public void onDateSelected(DateTime dateSelected)
    {
        status=dateSelected.toString();
    }
}
