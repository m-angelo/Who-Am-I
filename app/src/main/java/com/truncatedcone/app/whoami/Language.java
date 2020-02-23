package com.truncatedcone.app.whoami;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.Locale;

public class Language extends Fragment {

    ConstraintLayout ViewLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.lang_layout, container, false);
        ViewLayout = v.findViewById(R.id.lang);
        final ImageButton PL = v.findViewById(R.id.pl);
        final ImageButton UK = v.findViewById(R.id.uk);
        final ImageButton DE = v.findViewById(R.id.de);
        View.OnClickListener LangListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ImageButton b = (ImageButton)v;
                PL.setImageResource(R.drawable.circle);
                UK.setImageResource(R.drawable.circle);
                DE.setImageResource(R.drawable.circle);
                Configuration config = getContext().getResources().getConfiguration();
                String lang = "eng";
                if (b==UK){
                    lang="en";
                }
                else if (b==PL){
                    lang="pl";
                }
                else if (b==DE){
                    lang="de";
                }else{}
                Locale locale = new Locale(lang);
                Locale.setDefault(locale);
                config.locale = locale;
                getContext().getResources().updateConfiguration(config,
                        getContext().getResources().getDisplayMetrics());
                getActivity().recreate();
            }

        };
        PL.setOnClickListener(LangListener);
        UK.setOnClickListener(LangListener);
        DE.setOnClickListener(LangListener);
        return v;
    }




}
