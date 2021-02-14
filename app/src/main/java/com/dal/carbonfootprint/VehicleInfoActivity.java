package com.dal.carbonfootprint;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.dal.carbonfootprint.profile.VehcileInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import pl.droidsonroids.gif.GifImageView;

import static java.security.AccessController.getContext;

public class VehicleInfoActivity extends AppCompatActivity {

    private static int spashTime=5000; // 5 sec
    AutoCompleteTextView vehicletypeFilledExposedDropdown;
    // Variable
    GifImageView bgapp;
    ConstraintLayout contentSection;
    ImageView imageView;
    TextView textView;
    AutoCompleteTextView brandFilledExposedDropdown;
    AutoCompleteTextView vehicleModel;
    AutoCompleteTextView fuelConsumption;
    Animation frombottom;
    Animation fadein;
    Animation fadeout;
    Animation side;
    AutoCompleteTextView yearFilledExposedDropdown;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN);

        setContentView(R.layout.activity_vehcileinfo);

        Button save = (Button) findViewById(R.id.vehicle_next_button);

        String[] vehicletype = new String[] {"Car", "Truck", "Bus", "MotorCycle"};
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(getApplicationContext(), R.layout.dropdown_menu_popup_item,vehicletype);

        vehicletypeFilledExposedDropdown =
                findViewById(R.id.filled_exposed_dropdown_vehicletype);
        System.out.println(vehicletypeFilledExposedDropdown.getX());
        vehicletypeFilledExposedDropdown.setAdapter(adapter);


        String[] year = new String[] {"2015", "2016", "2017", "2018","2019","2020","older than 2015"};
        ArrayAdapter<String> adapteryear =
                new ArrayAdapter<String>(getApplicationContext(), R.layout.dropdown_menu_popup_item,
                        year);

        yearFilledExposedDropdown =
               findViewById(R.id.filled_exposed_dropdown_year);
        yearFilledExposedDropdown.setAdapter(adapteryear);

        String[] vehiclebrand = new String[] {"Audi", "Ford", "Fiat", "Honda","Mercedis","Nissan","Tata Motors","Toyota"};
        ArrayAdapter<String> adapterbrand =
                new ArrayAdapter<String>(getApplicationContext(), R.layout.dropdown_menu_popup_item,
                        vehiclebrand);

        brandFilledExposedDropdown =
                findViewById(R.id.filled_exposed_dropdown_brand);
        brandFilledExposedDropdown.setAdapter(adapterbrand);


        vehicleModel = (AutoCompleteTextView)findViewById(R.id.filled_exposed_dropdown_model);
        fuelConsumption = (AutoCompleteTextView)findViewById(R.id.filled_exposed_dropdown_fuel);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> docData = new HashMap<>();

                //vehicle type
                String yearInput= yearFilledExposedDropdown.getText().toString();
                String vehcileBrandInput = brandFilledExposedDropdown.getText().toString();
                String vehicletypeInput = vehicletypeFilledExposedDropdown.getText().toString();
                String modelInput = vehicleModel.getText().toString();
                String fuelInput = fuelConsumption.getText().toString();
                docData.put("Vehicle type", vehicletypeInput);
                docData.put("Model Year", yearInput);
                docData.put("Vehicle Brand", vehcileBrandInput);
                docData.put("Vehicle Model Name", modelInput);
                docData.put("Fuel Consumption", fuelInput);

                FirebaseAuth mAuth;
                mAuth = FirebaseAuth.getInstance();
                FirebaseUser currentUser = mAuth.getCurrentUser();

                if(currentUser!=null) {
                    docData.put("User Id", currentUser.getEmail());
                }

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                DocumentReference newvehicleref = db.collection("UserVehicle").document();
                newvehicleref
                        .set(docData)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                startActivity(intent);
                                Toast.makeText(VehicleInfoActivity.this, "Vehicle Details Added Successfully", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(VehicleInfoActivity.this, "Sorry couldn't add Vehicle Details.", Toast.LENGTH_SHORT).show();
                            }
                        });



            }
        });



    }
}