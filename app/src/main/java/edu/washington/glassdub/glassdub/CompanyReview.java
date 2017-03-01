package edu.washington.glassdub.glassdub;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */

/**
 * TODO: Load the chosen job review from Kumulos and all of their data (review title, rating, etc.)
 */

public class CompanyReview extends Fragment {
    public CompanyReview() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_company_review, container, false);
    }
}
