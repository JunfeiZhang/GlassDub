package edu.washington.glassdub.glassdub;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kumulos.android.Kumulos;
import com.kumulos.android.ResponseHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */

public class LoginActivity extends AppCompatActivity {
    private GlassDub app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        app = (GlassDub) getApplication();

        final String authUrl = "https://students.washington.edu/dianaw14/glassdub_auth/";

        WebView webview = new WebView(this);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                String cookie = "glassdub_username";
                String cookieValue = null;
                if (url.contains("glassdub_auth")) {
                    // send user to main activity and add username to application obj
                    CookieManager cookieManager = CookieManager.getInstance();
                    String cookies = cookieManager.getCookie(authUrl);
                    if (cookies != null) {
                        String[] temp=cookies.split(";");
                        for (String ar1 : temp ){
                            if(ar1.contains(cookie)){
                                String[] temp1=ar1.split("=");
                                cookieValue = temp1[1];
                                createUser(cookieValue);
                                app.setUsername(cookieValue);

                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        }
                    }
                }
            }
        });
        setContentView(webview);
        webview.loadUrl(authUrl);
    }

    public void createUser(String cookie) {
        final String c = cookie;
        currentUser user = new currentUser();
        user.setCurrentUser(cookie);

        final Map<String, String> userParams = new HashMap<>();
        userParams.put("username", cookie);

        Kumulos.call("login", userParams, new ResponseHandler() {
            @Override
            public void didCompleteWithResult(Object result) {
                if (result.toString().equals("32") || result.toString().equals("64") || result.toString().equals("128")) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            getApplicationContext());
                    alertDialogBuilder
                            .setTitle("Error")
                            .setMessage("We were unable to sign you in. Try again.")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                } else {
                    ArrayList<LinkedHashMap<String, Object>> objects = (ArrayList<LinkedHashMap<String, Object>>) result;
                    if (objects.size() == 0) {
                        createNewUser(userParams, c);
                    } else {
                        app.setUsernumber(objects.get(0).get("userID").toString());
                    }
                    String notification = "Signed in as " + c;
                    Toast t = Toast.makeText(getApplicationContext(), notification, Toast.LENGTH_SHORT);
                    t.show();
                }
            }
        });
    }

    public void createNewUser(Map<String, String> userParams, String cookie) {
        final String c = cookie;

        Kumulos.call("createUser", userParams, new ResponseHandler() {
            @Override
            public void didCompleteWithResult(Object result) {
                if (result.toString().equals("32") || result.toString().equals("64") || result.toString().equals("128")) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            getApplicationContext());
                    alertDialogBuilder
                            .setTitle("Error")
                            .setMessage("We were unable to sign you in. Try again.")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                } else {
                    // pop up a toast that says who they are
                    app.setUsernumber(result.toString());
                }
            }
        });
    }
}

