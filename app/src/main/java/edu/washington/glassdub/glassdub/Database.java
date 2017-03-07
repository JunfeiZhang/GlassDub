package edu.washington.glassdub.glassdub;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.kumulos.android.Kumulos;
import com.kumulos.android.ResponseHandler;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by alihaugh on 3/4/17.
 */

public class Database {

    public Database() {

    }

//    public ArrayList<LinkedHashMap<String, Object>> searchCompanies(Map<String,String> map, Activity act) {
//        final Activity currentAct = act;
//
//        Kumulos.call("searchCompanies", map, new ResponseHandler() {
//
//            @Override
//            public void didCompleteWithResult(Object result) {
//                if (result.toString().equals("32") || result.toString().equals("64") || result.toString().equals("128")) {
//                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
//                            currentAct);
//                    alertDialogBuilder
//                            .setTitle("Error")
//                            .setMessage("We were unable to retrieve information about the selected review. Try again.")
//                            .setCancelable(false)
//                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//                                    // send them back to list of companies
//                                    dialog.cancel();
//                                }
//                            });
//                    AlertDialog alertDialog = alertDialogBuilder.create();
//                    alertDialog.show();
//                }
//            }
//        });
//
//        return (ArrayList<LinkedHashMap<String, Object>>) result;
//    }
}
