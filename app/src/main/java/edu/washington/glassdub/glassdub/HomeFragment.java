package edu.washington.glassdub.glassdub;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import static android.view.View.VISIBLE;


/**
 * A simple {@link Fragment} subclass.
 */


/*
 * TODO: Implement search query (save the user input from the search bar and pass it to SearchFragment) and set the filter for jobs/companies
 */

public class HomeFragment extends Fragment {
    private Button writeReview;
    private Button writeInterview;
    private SearchView searchView;
    private TextView hintText;
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private ViewPagerAdapter vpAdapter;
    private String query;
    private View view;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        query = "";
        searchView = (SearchView) view.findViewById(R.id.search);
        hintText = (TextView) view.findViewById(R.id.textView5);

        mViewPager = (ViewPager) view.findViewById(R.id.container);
        vpAdapter = new ViewPagerAdapter(getFragmentManager());

        //First fill in both tabs with empty fragment
        vpAdapter.addFragments(new BlankFragment(), "Companies");
        vpAdapter.addFragments(new BlankFragment(), "Jobs");


        mViewPager.setAdapter(vpAdapter);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                query = searchView.getQuery().toString().toLowerCase();

                // TODO: Grab information from Kumulos and save it to fragment
                Fragment newCompaniesFrag = new CompanyList();
                Fragment newJobsFrag = new JobList();

                ViewPager afterSearch = (ViewPager) view.findViewById(R.id.container);
                ViewPagerAdapter test = new ViewPagerAdapter(getFragmentManager());
                test.addFragments(newCompaniesFrag, "Companies");
                test.addFragments(newJobsFrag, "Jobs");
                afterSearch.setAdapter(test);
                tabLayout.setupWithViewPager(afterSearch);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                String result = searchView.getQuery().toString();
                Log.i("HomeFragment", result);
                return false;
            }
        });

        searchView.setOnSearchClickListener(new SearchView.OnClickListener() {
            @Override
            public void onClick(View v) {
                hintText.setVisibility(View.INVISIBLE);
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose(){
                hintText.setVisibility(VISIBLE);
                return false;
            }
        });


        writeInterview = (Button) view.findViewById(R.id.button7);
        writeInterview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WriteInterview.class);
                startActivity(intent);
            }
        });

        writeReview = (Button) view.findViewById(R.id.button3);
        writeReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WriteReview.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        searchView.onActionViewCollapsed();
        searchView.setQuery(query, false);
        hintText.setVisibility(VISIBLE);
    }
}
