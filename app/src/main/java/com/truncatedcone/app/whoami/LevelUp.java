package com.truncatedcone.app.whoami;

import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;


public class LevelUp extends Fragment {

    ConstraintLayout Layout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.level_up, container, false);
        Layout = v.findViewById(R.id.levellayout);
        ImageView big = v.findViewById(R.id.big);
        ImageView med = v.findViewById(R.id.med);
        ImageView small = v.findViewById(R.id.small);
        final TextView categ_lvl = v.findViewById(R.id.categ_lvl);
        final TextView categ = v.findViewById(R.id.categ);
        final TextView lvl = v.findViewById(R.id.overall_lvl);
        final ImageView circle = v.findViewById(R.id.level_circle);
        final Animation bot =  AnimationUtils.loadAnimation(getActivity(),R.anim.frombot);
        final Animation top =  AnimationUtils.loadAnimation(getActivity(),R.anim.lvlup_anim);

        categ.setText("  "+LevelUpArgs.fromBundle(getArguments()).getCategory()+"  ");

        final Animation in = new AlphaAnimation(0.0f, 1.0f);
        in.setDuration(600);
        final Animation out = new AlphaAnimation(1.0f, 0.0f);
        out.setDuration(600);
        out.setStartOffset(1400);
        categ_lvl.startAnimation(out);
        circle.startAnimation(out);
        lvl.startAnimation(out);
        final int newoverall= Integer.valueOf((lvl.getText()).toString())+1;
        final int[] ilevel={Integer.valueOf((lvl.getText()).toString())};
        final Context mContext = getActivity();
        if (ilevel[0] < 10) {
            circle.setColorFilter(ContextCompat.getColor(mContext,R.color.level1));
        } else if (ilevel[0] < 20) {
            circle.setColorFilter(ContextCompat.getColor(mContext,R.color.level2));
        } else if (ilevel[0] < 30) {
            circle.setColorFilter(ContextCompat.getColor(mContext, R.color.level3));
        } else if (ilevel[0] < 40) {
            circle.setColorFilter(ContextCompat.getColor(mContext,R.color.level4));
        } else if (ilevel[0] < 50) {
            circle.setColorFilter(ContextCompat.getColor(mContext,R.color.level5));
        } else if (ilevel[0] < 60) {
            circle.setColorFilter(ContextCompat.getColor(mContext,R.color.level6));
        } else if (ilevel[0] < 70) {
            circle.setColorFilter(ContextCompat.getColor(mContext,R.color.level7));
        } else if (ilevel[0] < 80) {
            circle.setColorFilter(ContextCompat.getColor(mContext,R.color.level8));
        } else if (ilevel[0] < 90) {
            circle.setColorFilter(ContextCompat.getColor(mContext,R.color.level9));
        } else if (ilevel[0] < 100) {
            circle.setColorFilter(ContextCompat.getColor(mContext,R.color.level10));
        }
        Animation.AnimationListener listenr = new Animation.AnimationListener() {

            @Override
            public void onAnimationEnd(Animation animation) {
                int newcateglvl = Integer.valueOf((categ_lvl.getText()).toString())+1;
                ilevel[0] = newoverall;
                if (ilevel[0] < 10) {
                    circle.setColorFilter(ContextCompat.getColor(mContext,R.color.level1));
                } else if (ilevel[0] < 20) {
                    circle.setColorFilter(ContextCompat.getColor(mContext,R.color.level2));
                } else if (ilevel[0] < 30) {
                    circle.setColorFilter(ContextCompat.getColor(mContext, R.color.level3));
                } else if (ilevel[0] < 40) {
                    circle.setColorFilter(ContextCompat.getColor(mContext,R.color.level4));
                } else if (ilevel[0] < 50) {
                    circle.setColorFilter(ContextCompat.getColor(mContext,R.color.level5));
                } else if (ilevel[0] < 60) {
                    circle.setColorFilter(ContextCompat.getColor(mContext,R.color.level6));
                } else if (ilevel[0] < 70) {
                    circle.setColorFilter(ContextCompat.getColor(mContext,R.color.level7));
                } else if (ilevel[0] < 80) {
                    circle.setColorFilter(ContextCompat.getColor(mContext,R.color.level8));
                } else if (ilevel[0] < 90) {
                    circle.setColorFilter(ContextCompat.getColor(mContext,R.color.level9));
                } else if (ilevel[0] < 100) {
                    circle.setColorFilter(ContextCompat.getColor(mContext,R.color.level10));
                }
                categ_lvl.setText(Integer.toString(newcateglvl));
                lvl.setText(Integer.toString(newoverall));
                lvl.startAnimation(in);
                circle.startAnimation(in);
                categ_lvl.startAnimation(in);
                Layout.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        NavController navController = Navigation.findNavController(v);
                        navController.navigate(LevelUpDirections.actionLevelUpToFinish());
                    }

                });



            }
            @Override
            public void onAnimationStart(Animation animation) {

            }


            @Override
            public void onAnimationRepeat(Animation animation) {

            }

        };
        out.setAnimationListener(listenr);


        return v;
    }



}