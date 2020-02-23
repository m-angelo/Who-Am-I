package com.truncatedcone.app.whoami;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class MatchLoader extends Fragment {

    ConstraintLayout ViewLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.match_loader, container, false);
        ViewLayout = v.findViewById(R.id.match_loader);


        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {

                NavController navController = Navigation.findNavController(v);
                MatchLoaderDirections.ActionMatchLoaderToMatcher action = MatchLoaderDirections.actionMatchLoaderToMatcher("1","2","3","4","5");
                navController.navigate(action);
            }
        }, 2500);
        return v;
    }




}
