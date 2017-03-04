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


/**
 * A simple {@link Fragment} subclass.
 */

/**
 * TODO: Load the chosen job from Kumulos and all of its data (company, type, etc.)
 */

public class Job extends Fragment {
    private String[] searchResults = new String[] {
            "Amazon", "Microsoft", "Boeing", "Whitepages", "T-Mobile", "WorkDay", "Amazon", "Microsoft", "Boeing"
    };

    public Job() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_job, container, false);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, searchResults);
        ListView companyReviewList = (ListView) view.findViewById(R.id.listView4);
        companyReviewList.setAdapter(adapter);

        companyReviewList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Intent intent = new Intent(getActivity(), CompanyActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

}