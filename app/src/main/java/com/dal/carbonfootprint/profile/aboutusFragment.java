package com.dal.carbonfootprint.profile;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.dal.carbonfootprint.R;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

/**
 * @Author Carbon vision
 * About Us Fragment
 */
public class aboutusFragment extends Fragment{

    Context mcontext = null;
    private ImageView backBtn = null;
    private String TAG = "AboutusFragment";
    RelativeLayout layout =null;

    /**
     * Method to create the view associated to the fragment
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
                    Bundle savedInstanceState
    ){
        mcontext = getContext();

        View view = inflater.inflate(R.layout.fragment_aboutus, container, false);
        backBtn = view.findViewById(R.id.back_arrow);
        layout = view.findViewById(R.id.relativecontent);
        return view;
    }

    /**
     * Method to perform the operation once the activity is being created
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String yourString = getString(R.string.About);
        Element obj = new Element();


        View aboutPage = new AboutPage(mcontext)
                .isRTL(false)
                .setImage(R.drawable.logo_transparent)
                .setDescription(yourString)
                .addItem(obj.setTitle("Help us improve"))
                .addGroup("Connect with us")
                .addEmail("carbonvision@gmail.com")
                .addWebsite("https://www.carbonvision.com/")
                .addFacebook("carbon_vision")
                .addPlayStore("Carbon Visison")
                .create();


        getActivity().setContentView(aboutPage);

        //setting on click listener
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "click on back button");
                Fragment frag = new Fragment(R.layout.fragment_profile);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.navigation_profile, frag);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
    }

}
