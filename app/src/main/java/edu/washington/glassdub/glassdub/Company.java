package edu.washington.glassdub.glassdub;


import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.kumulos.android.Kumulos;
import com.kumulos.android.ResponseHandler;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */

/**
 * TODO: Load the chosen company from Kumulos and all of their data (company name, job reviews, interview reviews , jobs, etc.)
 */

public class Company extends Fragment {
    private Button review;
    private Button interview;
    private Button jobs;
    private TextView title, descr;
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
//
//        ViewPagerAdapter vpAdapter = new ViewPagerAdapter(getFragmentManager());
//        vpAdapter.addFragments(new ReviewList(), "Reviews");
//        vpAdapter.addFragments(new InterviewList(), "Interviews");
//        vpAdapter.addFragments(new JobList(), "Jobs");
//
//        mViewPager.setAdapter(vpAdapter);
//
////        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
//        tabLayout.setupWithViewPager(mViewPager);
//
//        title = (TextView) view.findViewById(R.id.cTitle);
//        descr = (TextView) view.findViewById(R.id.cDescr);
//
//        // TODO: get info sent to fragment (comapny ID)
//        int companyID = 2; // getArguments().getInt("comapnyID");
//
//        Map<String,String> companyParams = new HashMap<>();
//        companyParams.put("companyID", String.valueOf(companyID));
//
//        Kumulos.call("getCompany", companyParams, new ResponseHandler() {
//            @Override
//            public void didCompleteWithResult(Object result) {
//                if (result.toString().equals("32") || result.toString().equals("64") || result.toString().equals("128")) {
//                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
//                            getActivity());
//                    alertDialogBuilder
//                            .setTitle("Error")
//                            .setMessage("We We were unable to retrieve information about the selected company. Try again.")
//                            .setCancelable(false)
//                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//                                    // TODO: send them back to list of companies
//                                    dialog.cancel();
//                                }
//                            });
//                    AlertDialog alertDialog = alertDialogBuilder.create();
//                    alertDialog.show();
//                } else {
//                    ArrayList<LinkedHashMap<String, Object>> objects = (ArrayList<LinkedHashMap<String,Object>>) result;
//                    // TODO: go through and update all the fields
//                    if (objects.size() > 0) {
//                        LinkedHashMap<String, Object> object = objects.get(0);
//                        title.setText(object.get("name").toString());
//                        descr.setText((object.get("description").toString()));
//                    }
//                }
//            }
//        });
//
//        review = (Button) view.findViewById(R.id.button3);
//        review.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Fragment reviewList = new ReviewList();
//
//                FragmentManager fragmentManager = getFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.list_Fragment, reviewList);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
//            }
//        });
//
//        interview = (Button) view.findViewById(R.id.button6);
//        interview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Fragment interviewList = new InterviewList();
//
//                FragmentManager fragmentManager = getFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.list_Fragment, interviewList);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
//            }
//        });
//
//        jobs = (Button) view.findViewById(R.id.button4);
//        jobs.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Fragment jobList = new JobList();
//
//                FragmentManager fragmentManager = getFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.list_Fragment, jobList);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
//            }
//        });
        return view;
    }

}
