package com.dal.carbonfootprint.trips;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.dal.carbonfootprint.HomeActivity;
import com.dal.carbonfootprint.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author Carbon Vision
 * Add Trip Activity
 */
public class AddTripActivity extends AppCompatActivity {

    TextView dateFilledExposedDropdown;
    AutoCompleteTextView sourceFilledExposedDropdown;
    AutoCompleteTextView destinationFilledExposedDropdown;
    AutoCompleteTextView distanceFilledExposedDropdown;

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    AutoCompleteTextView mDisplayDate;
    /**
     * Method to override the logic of creating the view
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN);

        //setting the content view
        setContentView(R.layout.fragment_addtrips);

        dateFilledExposedDropdown = findViewById(R.id.tvDate);
        final String[] selecteddate = new String[3];

         mDisplayDate = (AutoCompleteTextView) findViewById(R.id.tvDate);

        //Setting up onclick listener to display the date picker
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(
                        AddTripActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        //setting up on date select listener
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d("TAG", "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);
                String date = day + "/" + month + "/" + year;
                selecteddate[0] = day + "";
                selecteddate[1] = month + "";
                selecteddate[2] = year + "";
                mDisplayDate.setText(date);
            }
        };


        /**
         * setting up on click listener on Add Trip button to add trip details manually
         */
        Button addTrip = (Button) findViewById(R.id.add_trip);
        addTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, String> docData = new HashMap<>();

                sourceFilledExposedDropdown = findViewById(R.id.filled_exposed_dropdown_source);
                destinationFilledExposedDropdown = findViewById(R.id.filled_exposed_dropdown_destination);
                distanceFilledExposedDropdown = findViewById(R.id.filled_exposed_dropdown_distance);

                String source = sourceFilledExposedDropdown.getText().toString();
                String destination = destinationFilledExposedDropdown.getText().toString();
                String distance = distanceFilledExposedDropdown.getText().toString();
                String travelDate = dateFilledExposedDropdown.getText().toString();

                //setting up document based on user provided trip details
                docData.put("source", destination);
                docData.put("destination", source);
                docData.put("Distance", distance);
                docData.put("date", selecteddate[0]);
                docData.put("month", selecteddate[1]);
                docData.put("year", selecteddate[2]);

                // setting up fire base instance
                FirebaseAuth mAuth;
                mAuth = FirebaseAuth.getInstance();
                FirebaseUser currentUser = mAuth.getCurrentUser();

                if (currentUser != null) {
                    docData.put("User Id", currentUser.getEmail());
                }

                //Establishing fire store instance and connection to add trip into "Trip Details collection
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                DocumentReference tripRef = db.collection("TripDetails").document();
                tripRef
                        .set(docData)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                Intent i = new Intent(AddTripActivity.this, HomeActivity.class);
                                startActivity(i);

                                Toast.makeText(getApplicationContext(), "Trip Details Added Successfully", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "Sorry couldn't add Vehicle Details.", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}
