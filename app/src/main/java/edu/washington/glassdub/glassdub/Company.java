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
public class Company extends Fragment {
    private Button review;
    private Button interview;
    private Button jobs;

    private String[] job_titles = {"Job 1", "Job 2", "Job 3"};
    private String[] job_users = {"User 1 - Date 1", "User 2 - Date 2", "User 3 - Date 3"};
    private String[] job_contents = {"Contents 1", "Contents 2", "Contents 3"};
    private int[] job_counts = {4, 3, 2};

    private String[] interview_titles = {"Interview 1", "Interview 2", "Interview 3"};
    private String[] interview_users = {"User 1 - Date 1", "User 2 - Date 2", "User 3 - Date 3"};
    private String[] interview_contents = {"Contents 1", "Contents 2", "Contents 3"};
    private int[] interview_counts = {2, 1, 4};

    public Company() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_company, container, false);

        Fragment jobList = new ListFragment();

        // store information in Bundle
        Bundle bundle = new Bundle();
        bundle.putStringArray("titles", job_titles);
        bundle.putStringArray("subtitles", job_users);
        bundle.putStringArray("contents", job_contents);
        bundle.putIntArray("counts", job_counts);
        jobList.setArguments(bundle);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.listView_company, jobList);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        return view;
    }


}
