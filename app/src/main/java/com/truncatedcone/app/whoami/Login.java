package com.truncatedcone.app.whoami;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.Locale;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;


public class Login extends Fragment {
    String lang ="eng;";
    private FirebaseAuth mAuth;
    private ImageView imageSwitcher;
    private FirebaseAuth.AuthStateListener mAuthListener;

    ConstraintLayout LoginLayout;
    CallbackManager callbackManager = CallbackManager.Factory.create();
    public View mv;
    public Button mloginButton;
    public com.eyalbira.loadingdots.LoadingDots mbar;
    int i = 1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.login_view, container, false);
        mAuth = FirebaseAuth.getInstance();
        LoginLayout = v.findViewById(R.id.login);
        mv=v;
        final LoginButton loginButton = v.findViewById(R.id.fb_login_button);
        loginButton.setFragment(this);
        loginButton.setReadPermissions(Arrays.asList("user_friends"));
        Button fakelogin = v.findViewById(R.id.login_button);
        final Animation fadein = AnimationUtils.loadAnimation(getActivity(),R.anim.fadeinlong);
        final Animation fadeout = AnimationUtils.loadAnimation(getActivity(),R.anim.fadeoutlong);
        final int imageSwitcherImages[] = {R.drawable.slide1, R.drawable.slide2, R.drawable.slide3, R.drawable.slide4, R.drawable.slide5, R.drawable.slide6,};
        mloginButton=fakelogin;
        imageSwitcher = v.findViewById(R.id.frame);



        Runnable r = new Runnable(){
            public void run(){
                imageSwitcher.startAnimation(fadeout);
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imageSwitcher.setImageResource(imageSwitcherImages[i]);
                        imageSwitcher.startAnimation(fadein);

                    }
                }, 1000);

                i++;
                if(i >= imageSwitcherImages.length){
                    i = 0;
                }
                imageSwitcher.postDelayed(this, 3000); //set to go off again in 3 seconds.
            }
        };
        imageSwitcher.postDelayed(r,3000); // set first time for 3 seconds
        mbar=v.findViewById(R.id.loginbar);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());

            }

            @Override
            public void onCancel() {
                mloginButton.setVisibility(mloginButton.VISIBLE);
                mbar.setVisibility(mbar.INVISIBLE);
                updateUI(null);
            }

            @Override
            public void onError(FacebookException exception) {
                mloginButton.setVisibility(mloginButton.VISIBLE);
                mbar.setVisibility(mbar.INVISIBLE);
                updateUI(null);
            }
        });
        View.OnClickListener LoginListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mloginButton.setVisibility(mloginButton.INVISIBLE);
                mbar.setVisibility(mbar.VISIBLE);
                loginButton.performClick();
            }
        };
        mAuth = FirebaseAuth.getInstance();
        fakelogin.setOnClickListener(LoginListener);

        return v;
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    public void updateUI(FirebaseUser currentUser) {
        if (currentUser!=null) {
            mloginButton.setVisibility(mloginButton.INVISIBLE);
            mbar.setVisibility(mbar.VISIBLE);
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference UserDataRef =  db.collection("users").document(currentUser.getUid());
            Configuration config = getContext().getResources().getConfiguration();
            Locale locale = new Locale(lang);
            Locale.setDefault(locale);
            config.locale = locale;
            getContext().getResources().updateConfiguration(config,
                    getContext().getResources().getDisplayMetrics());

            UserDataRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.getString("birth")!=null) {
                            Log.i("LOGGER","Lang "+document.getString("language"));
                            lang = document.getString("language");
                            NavController navController = Navigation.findNavController(mv);
                            navController.navigate(R.id.action_login_to_menu);
                        } else {
                            Log.d("LOGGER", "No such document");
                            NavController navController = Navigation.findNavController(mv);
                            navController.navigate(R.id.action_login_to_age2);
                        }
                    } else {
                        Log.d("LOGGER", "get failed with ", task.getException());
                    }
                }
            });



        }
    }


    private void handleFacebookAccessToken(AccessToken accessToken) {

        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener( getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("signInWithCredential", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            mloginButton.setVisibility(mloginButton.VISIBLE);
                            mbar.setVisibility(mbar.INVISIBLE);
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }


}
