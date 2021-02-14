package com.dal.carbonfootprint.profile;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.dal.carbonfootprint.R;
import com.dal.carbonfootprint.SignInActivity;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;


/**
 * @Author Carbon vision
 * Profile Fragment
 */
public class ProfileFragment extends Fragment {

    private DatabaseReference firebaseDatabaseReference;
    private FirebaseDatabase fireabseDatabase;
    private TextView tvFirstName;
    private TextView tvLastName;
    private TextView tvEmail;
    private TextView tvaboutus;
    private TextView tvsupport;
    private TextView manageVehcile;
    private final int RC_SIGN_IN = 1;
    private final String TAG = "SignInActivity";
    private FirebaseAuth firebaseAuth;
    private String email;
    private String name;
    private Uri photoUr;
    private ImageView profileimage;
    private TextView btnSignout;

    /**
     * Method to create the view associated to the fragment
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        initializeFirebase();
        View view= inflater.inflate(R.layout.fragment_profile, container, false);
        startProfileFragment(view);
        String uid;
        FirebaseUser mUser = firebaseAuth.getCurrentUser();
        if(mUser!=null) {

            // Name, email address, and profile photo Url
            name = mUser.getDisplayName();
            email = mUser.getEmail();
            if (mUser.getPhotoUrl() != null) {
                photoUr = mUser.getPhotoUrl();
            }
        }

        // setting the view content
        tvEmail.setText(email);
        tvFirstName.setText(name);
        tvLastName.setText("");
        if(photoUr!=null){
            Glide.with((Fragment)this).load(this.photoUr).into(profileimage);
        }

        // Setting up on click listener on Manage Vehicle icon
        manageVehcile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), VehcileInfoEditActivity.class);
                startActivity(i);
                ((Activity) getActivity()).overridePendingTransition(0, 0);
            }
        });

        // Setting up on click listener on About Us icon
        tvaboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment frag = new Fragment(R.layout.fragment_aboutus);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment, frag);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        // Setting up on click listener on Help and Support icon
        tvsupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment frag = new Fragment(R.layout.fragment_faqsact);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment, frag);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        return view;
    }

    /**
     * Method to set up the Profile fragment View and content
     * @param view
     */
    private void startProfileFragment(View view) {

        tvFirstName = view.findViewById(R.id.tvFirstName);
        tvLastName = view.findViewById(R.id.tvLastName);
        tvEmail = view.findViewById(R.id.tvEmail);
        tvaboutus = view.findViewById(R.id.about_us);
        tvsupport = view.findViewById(R.id.supporthelp);
        manageVehcile = view.findViewById(R.id.manage_vehcile) ;
        profileimage = view.findViewById(R.id.profileCircleImageView);
        btnSignout=view.findViewById(R.id.logout);
        btnSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    AuthUI.getInstance()
                            .signOut(getContext())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                public void onComplete(@NonNull Task<Void> task) {
                                    // User would be signed out
                                    startActivity(new Intent(getActivity(), SignInActivity.class));
                                }
                            });
            }
        });
    }

    /**
     * Initializing the firebase
     */
    private void initializeFirebase() {
        fireabseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabaseReference = fireabseDatabase.getReference().child("Users");
        firebaseAuth = FirebaseAuth.getInstance();

    }
}