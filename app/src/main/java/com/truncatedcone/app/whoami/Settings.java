package com.truncatedcone.app.whoami;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class Settings extends Fragment {

    ConstraintLayout RelationLayout;
    AnimationDrawable animationDrawable;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.settings, container, false);
        RelationLayout = v.findViewById(R.id.sett_layout);
        Button lang = v.findViewById(R.id.lang);
        Button log_out = v.findViewById(R.id.log_out);

        View.OnClickListener LangListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_settings_to_lang);
            }
        };
        View.OnClickListener OutListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                LoginManager.getInstance().logOut();
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_settings_to_login);
            }
        };
        lang.setOnClickListener(LangListener);
        log_out.setOnClickListener(OutListener);
        return v;
    }

}
