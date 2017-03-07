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

import com.kumulos.android.Kumulos;
import com.kumulos.android.ResponseHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class jobListHome extends Fragment {

    private String[] titles = {"Job 1", "Job 2", "Job 3", "Job 4", "Job 5", "Job 6"};
    private String[] subtitles = {"User 1 - Date 1", "User 2 - Date 2", "User 3 - Date 3",
            "User 4 - Date 4", "User 5 - Date 5", "User 6 - Date 6"};
    private String[] contents = {"Contents 1", "Contents 2", "Contents 3", "Contents 4", "Contents 5", "Contents 6"};
    private int[] counts = {4, 3, 2, 3, 5, 2};

    private static final String TAG = "JobList";

    public jobListHome() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_job_list, container, false);

        Map<String, String> jobParam = new HashMap<>();
        if(getArguments().getString("job").equals("none")){
            jobParam.put("company","");
        }else{
            jobParam.put("company", getArguments().getString("job"));
        }

        Kumulos.call("searchJob", jobParam, new ResponseHandler() {

            @Override
            public void didCompleteWithResult(Object result) {
                if (result.toString().equals("32") || result.toString().equals("64") || result.toString().equals("128")) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            getActivity());
                    alertDialogBuilder
                            .setTitle("Error")
                            .setMessage("We were unable to fetch the jobs jobs. Try searching again.")
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
                        // TODO: show the user that there were no results
                    } else {
                        // Populate with data
                        CustomAdapter adapter = new CustomAdapter(getActivity(), R.layout.list_item, getData(objects));

                        ListView companyReviewList = (ListView) view.findViewById(R.id.job_listview);
                        companyReviewList.setAdapter(adapter);

                        companyReviewList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                                Intent intent = new Intent(getActivity(), JobPage.class);
                                intent.putExtra("jobID", objects.get(position).get("jobID").toString());
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
            data[i] = new CustomItem(obj.get("title").toString(), obj.get("type").toString(), "", 5);
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
