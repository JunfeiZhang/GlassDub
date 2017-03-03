package edu.washington.glassdub.glassdub;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */

/**
 * TODO: Load the chosen interview review from Kumulos and all of their data (role, difficulty, etc.)
 */

public class Interview extends Fragment {


    public Interview() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_interview, container, false);
    }

}
