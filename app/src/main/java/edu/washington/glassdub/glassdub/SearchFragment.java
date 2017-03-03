package edu.washington.glassdub.glassdub;


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
 * TODO: Fetch data from Kumulos based on user input and display list of companies/jobs
 * TODO: Implement tab slide navigation
 */

public class SearchFragment extends Fragment {
    private String[] searchResults = new String[] {
            "Amazon", "Microsoft", "Boeing"
    };

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, searchResults);
        ListView results = (ListView) view.findViewById(R.id.listView);
        results.setAdapter(adapter);

        results.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                //Check if item is interview or company
                Fragment company = new Company();
                Fragment reviews = new ReviewList();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_placeholder, company);
                fragmentTransaction.replace(R.id.list_Fragment, reviews);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return view;
    }

}
