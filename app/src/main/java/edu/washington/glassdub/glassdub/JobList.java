package edu.washington.glassdub.glassdub;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 */

/**
 * TODO: Load the list of fetched jobs from Kumulos (after the user has searched)
 */

public class JobList extends Fragment {
    private String[] jobs = new String[] {
            // TODO: determine if we need one fragment for search activity and one fragment for company activity
            "Job 1 Title - Company", "Job 2 Title - Company", "Job 3 Title - Company"
    };

    public JobList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_job_list, container, false);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, jobs);
        ListView jobList = (ListView) view.findViewById(R.id.listView1);
        jobList.setAdapter(adapter);

        jobList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
            Intent intent = new Intent(getContext(), JobPage.class);
            startActivity(intent);
            }
        });
        return view;
    }

}
