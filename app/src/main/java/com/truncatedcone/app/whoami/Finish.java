package com.truncatedcone.app.whoami;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.scwang.wave.MultiWaveHeader;

import java.util.ArrayList;
import java.util.Arrays;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class Finish extends Fragment {

    ConstraintLayout Layout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.finish_screen, container, false);
        ImageButton chat = v.findViewById(R.id.chat);
        ImageButton play = v.findViewById(R.id.play);
        String[] aNames = {"Szymon Stasik","Natalia Fitowska","Kuba Jurzak","Oliwia Kowalska"};
        String[] aLevels = {"65","52","45","33"};
        String[] aCat = {"Morality","Culture","Beliefs","Hobbies"};
        final View.OnClickListener PlayListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                navController.navigate(FinishDirections.actionFinishToMatchLoader());
            }
        };
        final View.OnClickListener ChatListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                navController.navigate(FinishDirections.actionFinishToChat());
            }
        };
        play.setOnClickListener(PlayListener);
        ArrayList<String> mNames = new ArrayList<String>(Arrays.asList(aNames));
        ArrayList<String> mCat = new ArrayList<String>(Arrays.asList(aCat));
        ArrayList<String> mLevels = new ArrayList<String>(Arrays.asList(aLevels));
        RecyclerView recyclerView =  v.findViewById(R.id.notifications);
        NotifyAdapter adapter = new NotifyAdapter(getActivity(),mNames, mCat,mLevels,"finish");
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity() ));
        return v;
    }



}