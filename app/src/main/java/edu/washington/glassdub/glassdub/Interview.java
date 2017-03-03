package edu.washington.glassdub.glassdub;


import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * TODO: Load the chosen interview review from Kumulos and all of their data (role, difficulty, etc.)
 */

public class Interview extends Fragment {
    private TextView position, type, experience, difficulty, offer, body, user, created;

    public Interview() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_interview, container, false);

        position = (TextView) view.findViewById(R.id.Iposition);
        type = (TextView) view.findViewById(R.id.Itype);
        experience = (TextView) view.findViewById(R.id.Iexperience);
        difficulty = (TextView) view.findViewById(R.id.Idifficulty);
        offer = (TextView) view.findViewById(R.id.Ioffer);
        body = (TextView) view.findViewById(R.id.Ibody);
        user = (TextView) view.findViewById(R.id.Iuser);
        created = (TextView) view.findViewById(R.id.Icreated);

        // TODO: get info sent to fragment (comapny ID)
        int interviewID = 2; // getArguments().getInt("interviewID");

        Map<String,String> interviewParams = new HashMap<>();
        interviewParams.put("interview_reviewID", String.valueOf(interviewID));

        Kumulos.call("getInterview", interviewParams, new ResponseHandler() {
            @Override
            public void didCompleteWithResult(Object result) {
                if (result.toString().equals("32") || result.toString().equals("64") || result.toString().equals("128")) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            getActivity());
                    alertDialogBuilder
                            .setTitle("Error")
                            .setMessage("We were unable to retrieve information about the selected company. Try again.")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // TODO: send them back to list of companies
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                } else {
                    ArrayList<LinkedHashMap<String, Object>> objects = (ArrayList<LinkedHashMap<String,Object>>) result;
                    if (objects.size() > 0) {
                        LinkedHashMap<String, Object> object = objects.get(0);
                        position.setText(object.get("position").toString());
                        type.setText(object.get("type").toString());
                        experience.setText(object.get("experience").toString());
                        difficulty.setText(object.get("difficulty").toString());
                        offer.setText(object.get("received_offer").toString());
                        body.setText(object.get("body").toString());
                        user.setText(object.get("user").toString());
                        created.setText(object.get("timeCreated").toString());
                    }
                }
            }
        });

        return view;
    }

}
