package com.dal.carbonfootprint.profile;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.dal.carbonfootprint.R;

/**
 * @Author Carbon vision
 * FAQ Fragment
 */
public class FAQFragment extends Fragment {

    Context mcontext = null;
    private ImageView backBtn;
    private String TAG = "AboutusFragment";

    /**
     * Method to create the view associated to the fragment
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mcontext = getContext();
        View view = inflater.inflate(R.layout.fragment_faqsact, container, false);
        backBtn = view.findViewById(R.id.back_arrow);
        return view;
    }




}