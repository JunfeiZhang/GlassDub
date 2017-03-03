package edu.washington.glassdub.glassdub;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 */

/**
 * TODO: Load the chosen company from Kumulos and all of their data (company name, job reviews, interview reviews , jobs, etc.)
 */

public class Company extends Fragment {
    private ViewPager mViewPager;
    private TabLayout tabLayout;

    public Company() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_company, container, false);

//        mViewPager = (ViewPager) view.findViewById(R.id.container);

        ViewPagerAdapter vpAdapter = new ViewPagerAdapter(getFragmentManager());
        vpAdapter.addFragments(new ReviewList(), "Reviews");
        vpAdapter.addFragments(new InterviewList(), "Interviews");
        vpAdapter.addFragments(new JobList(), "Jobs");

        mViewPager.setAdapter(vpAdapter);

//        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        return view;
    }

}
