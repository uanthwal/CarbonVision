package com.dal.carbonfootprint.trips;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dal.carbonfootprint.R;
import com.dal.carbonfootprint.dashboard.Travel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * @Author Carbon vision
 * Trip fragment
 */
public class TripsFragment extends Fragment {

    private TripViewModel tripViewModel;
    private Button addtrip;

    /**
     * Method overwrite to create view with required content
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return View
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        tripViewModel =
                new ViewModelProvider(this).get(TripViewModel.class);
        View view = inflater.inflate(R.layout.fragment_trips, container, false);
        addtrip = view.findViewById(R.id.add_trip);

        // defining on click listener for add trip button
        addtrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AddTripActivity.class);
                startActivity(i);
                ((Activity) getActivity()).overridePendingTransition(0, 0);
            }
        });
        return view;
    }

    /**
     * method to get the recycler view
     * @param view
     * @param savedInstanceState
     */
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getList(view);

    }

    /**
     * Method to retrieve trip details from fire store and setting it up on recycler view
     * @param view
     */
    private void getList(final View view) {
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = mAuth.getCurrentUser();
        //Setting up firebase instance
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference docRef = db.collection("TripDetails");
        //Retrieving user trip details
        docRef.whereEqualTo("User Id", currentUser.getEmail()).
                get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<Travel> myListData = new ArrayList<>();
                    for (DocumentSnapshot document : task.getResult()) {
                        String dateString = document.getData().get("date").toString();
                        String month = document.getData().get("month").toString();
                        String year = document.getData().get("year").toString();
                        String completeDate = dateString + "/" + month + "/" + year;
                        DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
                        Date date = null;
                        try {
                            date = format.parse(completeDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        myListData.add(new Travel(date, Integer.parseInt(document.getData().get("Distance").toString()), document.getData().get("source").toString(), document.getData().get("destination").toString(), currentUser.getEmail()));
                    }
                    RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.usersRecycler);
                    //setting up the recycler view
                    MyListAdapter adapter = new MyListAdapter(myListData);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(adapter);
                } else {
                    Log.i("This is Tag", "get failed with ", task.getException());
                }
            }
        });
    }
}