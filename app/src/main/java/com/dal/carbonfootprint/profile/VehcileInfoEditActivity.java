package com.dal.carbonfootprint.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.dal.carbonfootprint.HomeActivity;
import com.dal.carbonfootprint.R;
import com.dal.carbonfootprint.trips.AddTripActivity;
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

/**
 * @author Carbon Vision
 */
public class VehcileInfoEditActivity extends AppCompatActivity {

    AutoCompleteTextView vehicleModel;
    AutoCompleteTextView fuelConsumption;
    AutoCompleteTextView brandFilledExposedDropdown;
    AutoCompleteTextView yearFilledExposedDropdown;
    AutoCompleteTextView vehicletypeFilledExposedDropdown;
    private VehcileInfo travelViewModel;
    Context mcontext = null;

    /**
     * Method to perform operation while creation of the view
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN);

        setContentView(R.layout.fragment_vehcileinfo);

        String[] vehicletype = new String[]{"Car", "Truck", "Bus", "MotorCycle"};
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(getApplicationContext(), R.layout.dropdown_menu_popup_item, vehicletype);

        vehicletypeFilledExposedDropdown =
                findViewById(R.id.filled_exposed_dropdown_vehicletype);
        vehicletypeFilledExposedDropdown.setAdapter(adapter);

        String[] year = new String[]{"2015", "2016", "2017", "2018", "2019", "2020", "older than 2015"};
        ArrayAdapter<String> adapteryear =
                new ArrayAdapter<String>(getApplicationContext(), R.layout.dropdown_menu_popup_item,
                        year);

        yearFilledExposedDropdown =
                findViewById(R.id.filled_exposed_dropdown_year);
        yearFilledExposedDropdown.setAdapter(adapteryear);

        String[] vehiclebrand = new String[]{"Audi", "Ford", "Fiat", "Honda", "Mercedis", "Nissan", "Tata Motors", "Toyota"};
        ArrayAdapter<String> adapterbrand =
                new ArrayAdapter<String>(getApplicationContext(), R.layout.dropdown_menu_popup_item,
                        vehiclebrand);

        brandFilledExposedDropdown =
                findViewById(R.id.filled_exposed_dropdown_brand);
        brandFilledExposedDropdown.setAdapter(adapterbrand);

        vehicleModel = (AutoCompleteTextView) findViewById(R.id.filled_exposed_dropdown_model);
        fuelConsumption = (AutoCompleteTextView) findViewById(R.id.filled_exposed_dropdown_fuel);

        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        final String[] id = new String[1];
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Fetching user detail to be displayed on the view
        db.collection("UserVehicle").whereEqualTo("User Id", currentUser.getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {

                                id[0] = document.getId();

                                //setting the user vehicle details in the view
                                vehicletypeFilledExposedDropdown.setText((document.getData().get("Vehicle type")).toString());
                                yearFilledExposedDropdown.setText((document.getData().get("Model Year")).toString());
                                brandFilledExposedDropdown.setText((document.getData().get("Vehicle Brand")).toString());
                                vehicleModel.setText((document.getData().get("Vehicle Model Name")).toString());
                                fuelConsumption.setText((document.getData().get("Fuel Consumption")).toString());

                                //setting up on clcik listner for update button
                                Button update = findViewById(R.id.update);
                                update.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Map<String, Object> docData = new HashMap<>();

                                        String yearInput = yearFilledExposedDropdown.getText().toString();
                                        String vehcileBrandInput = brandFilledExposedDropdown.getText().toString();
                                        String vehicletypeInput = vehicletypeFilledExposedDropdown.getText().toString();
                                        String modelInput = vehicleModel.getText().toString();
                                        String fuelInput = fuelConsumption.getText().toString();

                                        //setting up the updated details into the document
                                        docData.put("Vehicle type", vehicletypeInput);
                                        docData.put("Model Year", yearInput);
                                        docData.put("Vehicle Brand", vehcileBrandInput);
                                        docData.put("Vehicle Model Name", modelInput);
                                        docData.put("Fuel Consumption", fuelInput);

                                        FirebaseAuth mAuth;
                                        mAuth = FirebaseAuth.getInstance();
                                        FirebaseUser currentUser = mAuth.getCurrentUser();

                                        if (currentUser != null) {
                                            docData.put("User Id", currentUser.getEmail());
                                        }

                                        // Establishing connection and getting instance of firebase fire store to update the vehicle information with new details
                                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                                        DocumentReference newvehicleref = db.collection("UserVehicle").document(id[0]);
                                        newvehicleref
                                                .update(docData)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Intent i = new Intent(VehcileInfoEditActivity.this, VehcileInfoEditActivity.class);
                                                        startActivity(i);
                                                        Toast.makeText(getApplicationContext(), "Vehicle Details Updated Successfully", Toast.LENGTH_SHORT).show();
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(VehcileInfoEditActivity.this, "Sorry couldn't add Vehicle Details.", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    }
                                });
                            }
                        } else {
                            System.out.println("Error" + task.getException());
                            Log.w("teja", "Error getting documents.", task.getException());
                        }
                    }
                });
    }
}