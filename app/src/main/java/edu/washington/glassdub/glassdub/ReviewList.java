package edu.washington.glassdub.glassdub;


import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import edu.washington.glassdub.glassdub.CompanyReview;
import edu.washington.glassdub.glassdub.R;

/**
 * A simple {@link Fragment} subclass.
 */

/**
 * TODO: Load the list of fetched job reviews from Kumulos (after the user has chosen a company or a job)
 */

public class ReviewList extends Fragment {
    private String[] companyReviews = new String[] {
            "Job Review 1", "Job Review 2", "Job Review 3"
    };

    public ReviewList() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_review_list, container, false);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, companyReviews);
        ListView companyReviewList = (ListView) view.findViewById(R.id.reviews);
        companyReviewList.setAdapter(adapter);

        companyReviewList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
            Intent intent = new Intent(getContext(), ReviewPage.class);
            startActivity(intent);
            }
        });
        return view;
    }

}
