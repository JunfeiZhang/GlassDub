package edu.washington.glassdub.glassdub;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;


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

    public Company() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_company, container, false);

        review = (Button) view.findViewById(R.id.button3);
        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment reviewList = new ReviewList();

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.list_Fragment, reviewList);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        interview = (Button) view.findViewById(R.id.button6);
        interview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment interviewList = new InterviewList();

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.list_Fragment, interviewList);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        jobs = (Button) view.findViewById(R.id.button4);
        jobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment jobList = new JobList();

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.list_Fragment, jobList);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return view;
    }

}
