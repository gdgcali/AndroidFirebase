package co.com.gdgcali.labfirebase.util;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by jggomezt on 03/06/2015.
 */
public class LabFirebaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
