package edu.washington.glassdub.glassdub;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import static android.view.View.VISIBLE;


/**
 * A simple {@link Fragment} subclass.
 */


/*
 * TODO: Fetch search query job results (save the user input from the search bar) and set the filter for jobs/companies
 * TODO: If time permits query on search bar text change
 */

public class HomeFragment extends Fragment {
    private SearchView searchView;
    private TextView hintText;
    private TabLayout tabLayout;
    private String query;
    private View view;
    private BottomNavigationView botNavigation;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);


        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getContext());
        Log.i("test", settings.getString("username", "hi"));


        query = "";

        botNavigation = (BottomNavigationView) view.findViewById(R.id.bottomBar);
        botNavigation.getMenu().getItem(1).setChecked(true);
        botNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.jobItem) {
                    Intent intent = new Intent(getActivity(), WriteReview.class);
                    startActivity(intent);
                } else if (item.getItemId() == R.id.homeItem) {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                } else if (item.getItemId() == R.id.interviewItem) {
                    Intent intent = new Intent(getActivity(), WriteInterview.class);
                    startActivity(intent);
                }
                return false;
            }
        });

        searchView = (SearchView) view.findViewById(R.id.search);
        hintText = (TextView) view.findViewById(R.id.search_title);
        hintText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(false);
            }
        });

        final ViewPager mViewPager = (ViewPager) view.findViewById(R.id.company_container);
        final ViewPagerAdapter vpAdapter = new ViewPagerAdapter(getFragmentManager());


        final Fragment defaultCompanysFrag = new CompanyList();
        final Bundle bundle = new Bundle();
        bundle.putString("user_query","none");
        defaultCompanysFrag.setArguments(bundle);
        vpAdapter.addFragments(defaultCompanysFrag,"Companies");


        final Fragment defaultJobsFrag = new jobListHome();
        bundle.putString("job","none");
        defaultJobsFrag.setArguments(bundle);
        vpAdapter.addFragments(defaultJobsFrag, "Jobs");

        mViewPager.setAdapter(vpAdapter);
        tabLayout = (TabLayout) view.findViewById(R.id.company_tabs);
        tabLayout.setupWithViewPager(mViewPager);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                query = searchView.getQuery().toString().toLowerCase();

                // TODO: Grab information from Kumulos and save it to fragment
                Fragment newCompaniesFrag = new CompanyList();
                Fragment newJobsFrag = new JobList();
                Bundle b = new Bundle();
                b.putString("user_query", query);
                newCompaniesFrag.setArguments(b);
                newJobsFrag.setArguments(b);

                ViewPager afterSearch = (ViewPager) view.findViewById(R.id.company_container);
                ViewPagerAdapter searchAdapter = new ViewPagerAdapter(getFragmentManager());
                searchAdapter.addFragments(newCompaniesFrag, "Companies");
                searchAdapter.addFragments(newJobsFrag, "Jobs");
                afterSearch.setAdapter(searchAdapter);
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
                mViewPager.setAdapter(vpAdapter);
                tabLayout.setupWithViewPager(mViewPager);
                return false;
            }
        });


//        writeInterview = (Button) view.findViewById(R.id.button7);
//        writeInterview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), WriteInterview.class);
//                startActivity(intent);
//            }
//        });
//
//        writeReview = (Button) view.findViewById(R.id.submit_interview_button);
//        writeReview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), WriteReview.class);
//                startActivity(intent);
//            }
//        });

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
