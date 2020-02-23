package com.truncatedcone.app.whoami;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import bolts.Task;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        Configuration config = getBaseContext().getResources().getConfiguration();
        Locale locale = Locale.getDefault();
        Locale.setDefault(locale);
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
    }



    @Override
    public void onBackPressed() {
        final NavController controller = Navigation.findNavController(this, R.id.thatishost);
        if (((controller.getCurrentDestination().getId())==R.id.play) || ((controller.getCurrentDestination().getId())==R.id.age) | ((controller.getCurrentDestination().getId())==R.id.menu) || ((controller.getCurrentDestination().getId())==R.id.matchLoader)|| ((controller.getCurrentDestination().getId())==R.id.rePlay)|| ((controller.getCurrentDestination().getId())==R.id.login)|| ((controller.getCurrentDestination().getId())==R.id.levelUp)){

        }
        else {
           controller.popBackStack();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            //System.out.println("@#@");
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }




}
