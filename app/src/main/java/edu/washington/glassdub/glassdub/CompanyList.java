package edu.washington.glassdub.glassdub;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.kumulos.android.Kumulos;
import com.kumulos.android.ResponseHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class CompanyList extends Fragment {
    private String[] titles = new String[] {
            "Amazon", "Microsoft", "Boeing", "Whitepages", "T-Mobile", "WorkDay",
            "Amazon", "Microsoft", "Boeing"
    };

    private String[] subtitles =
            {"UI/UX Designer","SDE Internship", "Electrical Engineer", "Data Scientist",
                    "Mechanical Engineer", "SDE Internship", "Electrical Engineer",
                    "Data Scientist", "Mechanical Engineer"};

    private String[] contents =
            {"Seattle, WA", "Redmond, WA", "Seattle, WA", "Seattle, WA", "Bonn, Germany",
                    "Pleasanton, CA", "Seattle, WA", "Redmond, WA", "Seattle, WA"};

    private int[] counts = {3, 2, 4, 5, 1, 2, 3, 2, 1};

    private static final String TAG = "CompanyList";

    private TextView noSearchResults;

    public CompanyList() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_company_list, container, false);

        Map<String, String> queryParam = new HashMap<>();
        Bundle bundle = getArguments();
//        if (bundle.) {
//
//
//        }
        if(getArguments().getString("user_query").equals("none")){
            queryParam.put("name","");
        }else{
            queryParam.put("name", getArguments().getString("user_query"));
        }

        noSearchResults = (TextView) view.findViewById(R.id.noResultstext);

        Kumulos.call("searchCompanies", queryParam, new ResponseHandler() {
            @Override
            public void didCompleteWithResult(Object result) {
                if (result.toString().equals("32") || result.toString().equals("64") || result.toString().equals("128")) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            getActivity());
                    alertDialogBuilder
                            .setTitle("Error")
                            .setMessage("We were unable to complete your search. Try searching again.")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                } else {
                    final ArrayList<LinkedHashMap<String, Object>> objects = (ArrayList<LinkedHashMap<String, Object>>) result;

                    if (objects.size() == 0) {
                        // TODO: tell the user that their query didn't return any results
                        noSearchResults.setVisibility(View.VISIBLE);
                    } else {
                        noSearchResults.setVisibility(View.INVISIBLE);
                        CustomAdapter adapter = new CustomAdapter(getActivity(), R.layout.list_item, getData(objects));

                        ListView companyReviewList = (ListView) view.findViewById(R.id.JPlistview);
                        companyReviewList.setAdapter(adapter);

                        companyReviewList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                                Intent intent = new Intent(getActivity(), CompanyActivity.class);
                                intent.putExtra("companyID", objects.get(position).get("companyID").toString());
                                startActivity(intent);
                            }
                        });
                    }
                }
            }
        });

        return view;
    }

    private CustomItem[] getData(ArrayList<LinkedHashMap<String, Object>> objects) {
        CustomItem data[] = new CustomItem[objects.size()];

        for (int i = 0; i < objects.size(); i++) {
            LinkedHashMap<String, Object> obj = objects.get(i);
            data[i] = new CustomItem(obj.get("name").toString(), obj.get("description").toString(), obj.get("description").toString(), Integer.valueOf(obj.get("rating").toString()));
        }

        return data;
    }

    public void setTitles(String[] titles) {
        this.titles = titles;
    }

    public void setUsers(String[] users) {
        this.subtitles = users;
    }

    public void setReviews(String[] reviews) {
        this.contents = reviews;
    }

    public void setCounts(int[] counts) {
        this.counts = counts;
    }

}