package com.truncatedcone.app.whoami;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;

public class Matcher extends Fragment {

    ConstraintLayout Layout;
    AnimationDrawable animationDrawable;
    ConstraintLayout AnimationFade;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.matcher_layout, container, false);
        AnimationFade = v.findViewById(R.id.matcher_lay);
        animationDrawable = (AnimationDrawable) AnimationFade.getBackground();
        animationDrawable.setEnterFadeDuration(1500);
        animationDrawable.setExitFadeDuration(1500);
        animationDrawable.start();
        Layout = v.findViewById(R.id.matcher_lay);
        String[] aNames = {"Szymon Stasik","Natalia Fitowska","Kuba Jurzak","Oliwia Kowalska","Pawel Nowak"};
        String[] aAge = {"20","19","20","21","18"};
        String[] aLevel = {"65","52","45","33","22"};
        ArrayList<String> mNames = new ArrayList<String>(Arrays.asList(aNames));
        ArrayList<String> mAge = new ArrayList<String>(Arrays.asList(aAge));
        ArrayList<String> mLevels = new ArrayList<String>(Arrays.asList(aLevel));
        RecyclerView recyclerView =  v.findViewById(R.id.match_cards);
        MatchAdapter adapter = new MatchAdapter(getActivity(),mNames, mAge,mLevels);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        return v;
    }



}