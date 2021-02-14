package com.dal.carbonfootprint.trips;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.dal.carbonfootprint.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddTripFragment extends  Fragment {

    Context mcontext = null;
    private TripViewModel tripViewModel;

    TextView dateFilledExposedDropdown;

    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mcontext = getContext();
        View view = inflater.inflate(R.layout.fragment_addtrips, container, false);
        dateFilledExposedDropdown = view.findViewById(R.id.tvDate);
        final Calendar calendar = Calendar.getInstance();
        dateFilledExposedDropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                DatePickerFragment dpf = new DatePickerFragment().newInstance();
//                dpf.setCallBack(onDate);
//                dpf.show(getFragmentManager().beginTransaction(), "DatePickerFragment");
            }
        });


        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d("yag","onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date = month + "/" + day + "/" + year;
                dateFilledExposedDropdown.setText(date);
            }
        };

        Button addTrip = (Button) view.findViewById(R.id.add_trip);
        addTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, String> docData = new HashMap<>();

                //vehicle type
                AutoCompleteTextView sourceFilledExposedDropdown = view.findViewById(R.id.filled_exposed_dropdown_source);
                AutoCompleteTextView destinationFilledExposedDropdown = view.findViewById(R.id.filled_exposed_dropdown_destination);
                AutoCompleteTextView distanceFilledExposedDropdown = view.findViewById(R.id.filled_exposed_dropdown_distance);


                String source= sourceFilledExposedDropdown.getText().toString();
                String destination = destinationFilledExposedDropdown.getText().toString();
                String distance = distanceFilledExposedDropdown.getText().toString();
                String travelDate = dateFilledExposedDropdown.getText().toString();

                docData.put("source", source);
                docData.put("destination", destination);
                docData.put("Distance", distance);
                docData.put("Date", travelDate);


                FirebaseAuth mAuth;
                mAuth = FirebaseAuth.getInstance();
                FirebaseUser currentUser = mAuth.getCurrentUser();

                if(currentUser!=null) {
                    docData.put("User Id", currentUser.getEmail());
                }

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                DocumentReference tripRef = db.collection("TripDetails").document();
                tripRef
                        .set(docData)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Fragment frag = new Fragment(R.layout.fragment_trips);
                                FragmentTransaction ft = getFragmentManager().beginTransaction();
                                ft.replace(R.id.nav_host_fragment, frag);
                                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                                ft.addToBackStack(null);
                                ft.commit();
                                Toast.makeText(getActivity(), "Trip Details Added Successfully", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(), "Sorry couldn't add Vehicle Details.", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        return view;
    }

    DatePickerDialog.OnDateSetListener onDate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            TextView tvDate = (TextView) getActivity().findViewById(R.id.tvDate);
            tvDate.setText(String.valueOf(year) + "-" + String.valueOf(monthOfYear)
                    + "-" + String.valueOf(dayOfMonth));
        }
    };


}
