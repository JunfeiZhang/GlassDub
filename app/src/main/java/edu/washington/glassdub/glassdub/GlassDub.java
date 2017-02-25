package edu.washington.glassdub.glassdub;

import android.app.Application;
import android.util.Log;

import com.kumulos.android.Kumulos;

/**
 * Created by alihaugh on 2/24/17.
 */

public class GlassDub extends Application {
    private static GlassDub instance = null;

    public GlassDub() {
        Log.i("class", "App created");
    }

    public static GlassDub getInstance() {
        if (instance == null)
            instance = new GlassDub();
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.i("testing", "WE GOT HERE");

        Kumulos.initWithAPIKeyAndSecretKey("6573923c-cfb6-4b5f-9b3f-cfd2dd63ef6a", "D+lAysXBEw9+Xr+xkNbEZZjfXxJXoJ5XmRM2", this);
    }
}
