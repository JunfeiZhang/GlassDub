package edu.washington.glassdub.glassdub;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class Job extends Fragment {

    private String[] job_titles = {"Job 1", "Job 2", "Job 3"};
    private String[] job_users = {"User 1 - Date 1", "User 2 - Date 2", "User 3 - Date 3"};
    private String[] job_contents = {"Contents 1", "Contents 2", "Contents 3"};
    private int[] job_counts = {4, 3, 2};

    private String[] interview_titles = {"Interview 1", "Interview 2", "Interview 3"};
    private String[] interview_users = {"User 1 - Date 1", "User 2 - Date 2", "User 3 - Date 3"};
    private String[] interview_contents = {"Contents 1", "Contents 2", "Contents 3"};
    private int[] interview_counts = {2, 1, 4};


    public Job() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_job, container, false);

        Fragment reviewList = new ListFragment();

        // store information in Bundle
        Bundle bundle = new Bundle();
        bundle.putStringArray("titles", job_titles);
        bundle.putStringArray("subtitles", job_users);
        bundle.putStringArray("contents", job_contents);
        bundle.putIntArray("counts", job_counts);
        reviewList.setArguments(bundle);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.list_fragment, reviewList);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        Button review = (Button) view.findViewById(R.id.reviews_button);
        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment reviewList = new ListFragment();

                // store information in Bundle
                Bundle bundle = new Bundle();
                bundle.putStringArray("titles", job_titles);
                bundle.putStringArray("subtitles", job_users);
                bundle.putStringArray("contents", job_contents);
                bundle.putIntArray("counts", job_counts);
                reviewList.setArguments(bundle);

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.list_fragment, reviewList);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        Button interviews = (Button) view.findViewById(R.id.interviews_button);
        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment interviewList = new ListFragment();

                // store information in Bundle
                Bundle bundle = new Bundle();
                bundle.putStringArray("titles", interview_titles);
                bundle.putStringArray("subtitles", interview_users);
                bundle.putStringArray("contents", interview_contents);
                bundle.putIntArray("counts", interview_counts);
                interviewList.setArguments(bundle);

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.list_fragment, interviewList);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return view;
    }

}
