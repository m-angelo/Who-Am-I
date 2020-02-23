package com.truncatedcone.app.whoami;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.firebase.auth.FirebaseAuth;
import com.scwang.wave.MultiWaveHeader;

import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class Play extends Fragment {
    ConstraintLayout PlayLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.play, container, false);
        PlayLayout = v.findViewById(R.id.relation);


        TextView categview = v.findViewById(R.id.category);
        TextView lvlview = v.findViewById(R.id.lvl);
        final TextView question = v.findViewById(R.id.question);
        SimpleDraweeView player = v.findViewById(R.id.player);
        SimpleDraweeView friend = v.findViewById(R.id.friend);
        MultiWaveHeader bar = v.findViewById(R.id.bar);
        MultiWaveHeader background = v.findViewById(R.id.wave);
        Uri nduri= Uri.parse(StartPlayArgs.fromBundle(getArguments()).getPic());
        Uri fburi= Uri.parse(""+com.facebook.Profile.getCurrentProfile().getProfilePictureUri(200, 200));
        friend.setImageURI(nduri);
        player.setImageURI(fburi);
        final Button choice1 = v.findViewById(R.id.option1);
        final Button choice2 = v.findViewById(R.id.option2);
        final Button choice3 = v.findViewById(R.id.option3);
        final Button choice4 = v.findViewById(R.id.option4);
        question.setText("  "+PlayArgs.fromBundle(getArguments()).getQ1()+"  ");
        choice1.setText("  "+PlayArgs.fromBundle(getArguments()).getA1()+"  ");
        choice2.setText("  "+PlayArgs.fromBundle(getArguments()).getB1()+"  ");
        choice3.setText("  "+PlayArgs.fromBundle(getArguments()).getC1()+"  ");
        choice4.setText("  "+PlayArgs.fromBundle(getArguments()).getD1()+"  ");
        final Animation frombot = AnimationUtils.loadAnimation(getActivity(),R.anim.fadein);
        final Animation fromtop = AnimationUtils.loadAnimation(getActivity(),R.anim.fromtop);
        final Animation fadein = AnimationUtils.loadAnimation(getActivity(),R.anim.fadein);
        final Animation fadeout = AnimationUtils.loadAnimation(getActivity(),R.anim.fadeout);

        Integer level = 0;
        if (PlayArgs.fromBundle(getArguments()).getSelectedLvl() == ""){
         level = 0;
            lvlview.setText(PlayArgs.fromBundle(getArguments()).getSelectedLvl()+"");}
        else{
             level = Integer.parseInt(PlayArgs.fromBundle(getArguments()).getSelectedLvl());
            lvlview.setText(PlayArgs.fromBundle(getArguments()).getSelectedLvl()+" lvl.");}
        categview.setText(PlayArgs.fromBundle(getArguments()).getSelectedCateg());

        final Boolean[] firstplay = {true};
        final Boolean[] levelup = {false};
        final int[] counter = {0};
        final  Boolean[] clicked = {false};

        View.OnClickListener AnswerListener = new View.OnClickListener() {

            @Override
            public void onClick(View c) {
                if(!clicked[0]){

                    clicked[0] = true;
                    counter[0]++;
                int countint = counter[0];
                Log.d("COUNTER", String.valueOf(countint));

                Button b = (Button) c;
                    if (choice1 == b) {
                        choice2.startAnimation(fadeout);
                        choice3.startAnimation(fadeout);
                        choice4.startAnimation(fadeout);
                        choice2.setVisibility(choice2.GONE);
                        choice3.setVisibility(choice3.GONE);
                        choice4.setVisibility(choice4.GONE);
                    } else if (choice2 == b) {
                        choice1.startAnimation(fadeout);
                        choice3.startAnimation(fadeout);
                        choice4.startAnimation(fadeout);
                        choice1.setVisibility(choice1.GONE);
                        choice3.setVisibility(choice3.GONE);
                        choice4.setVisibility(choice4.GONE);

                    } else if (choice3 == b) {
                        choice1.startAnimation(fadeout);
                        choice2.startAnimation(fadeout);
                        choice4.startAnimation(fadeout);
                        choice1.setVisibility(choice1.GONE);
                        choice2.setVisibility(choice2.GONE);
                        choice4.setVisibility(choice4.GONE);


                    } else {
                        choice1.startAnimation(fadeout);
                        choice2.startAnimation(fadeout);
                        choice3.startAnimation(fadeout);
                        choice1.setVisibility(choice1.GONE);
                        choice2.setVisibility(choice2.GONE);
                        choice3.setVisibility(choice3.GONE);

                    }
                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (counter[0]!=3) {
                                question.startAnimation(fadeout);
                                choice1.startAnimation(fadeout);
                                choice2.startAnimation(fadeout);
                                choice3.startAnimation(fadeout);
                                choice4.startAnimation(fadeout);
                            }
                            if (counter[0]==1){
                                question.setText("  "+PlayArgs.fromBundle(getArguments()).getQ2()+"  ");
                                choice1.setText("  "+PlayArgs.fromBundle(getArguments()).getA2()+"  ");
                                choice2.setText("  "+PlayArgs.fromBundle(getArguments()).getB2()+"  ");
                                choice3.setText("  "+PlayArgs.fromBundle(getArguments()).getC2()+"  ");
                                choice4.setText("  "+PlayArgs.fromBundle(getArguments()).getD2()+"  ");
                            }else if (counter[0]==2){
                                question.setText("  "+PlayArgs.fromBundle(getArguments()).getQ3()+"  ");
                                choice1.setText("  "+PlayArgs.fromBundle(getArguments()).getA3()+"  ");
                                choice2.setText("  "+PlayArgs.fromBundle(getArguments()).getB3()+"  ");
                                choice3.setText("  "+PlayArgs.fromBundle(getArguments()).getC3()+"  ");
                                choice4.setText("  "+PlayArgs.fromBundle(getArguments()).getD3()+"  ");
                            }else if(counter[0]==3) {
                                final Boolean[] firstplay = {true};
                                final Boolean[] levelup = {true};
                                NavController navController = Navigation.findNavController(v);
                                if (firstplay[0]==true){
                                    if (levelup[0]==true) {
                                        //nav to lvlup
                                        PlayDirections.ActionPlayToLevelUp action = PlayDirections.actionPlayToLevelUp(PlayArgs.fromBundle(getArguments()).getSelectedCateg());
                                        navController.navigate(action);
                                    }
                                    else {
                                        //nav to finish
                                        navController.navigate(PlayDirections.actionPlayToFinish());
                                    }
                                }
                                else {

                                }
                            }
                            if (counter[0]!=3) {
                                fadein.setStartOffset(0);
                                choice1.setVisibility(choice1.VISIBLE);
                                choice2.setVisibility(choice2.VISIBLE);
                                choice3.setVisibility(choice3.VISIBLE);
                                choice4.setVisibility(choice3.VISIBLE);
                                question.startAnimation(fadein);
                                choice1.startAnimation(fadein);
                                choice2.startAnimation(fadein);
                                choice3.startAnimation(fadein);
                                choice4.startAnimation(fadein);
                            }

                        }
                    }, 1000);
                    new Handler().postDelayed(() -> clicked[0] = false, 1000);
                }
                }
        };
        choice1.setOnClickListener(AnswerListener);
        choice2.setOnClickListener(AnswerListener);
        choice3.setOnClickListener(AnswerListener);
        choice4.setOnClickListener(AnswerListener);
        int colorid = R.color.level1;
        int gradientid = R.color.level1_gredient;

        if (level == 0) {
            colorid = R.color.colorPrimary;
            gradientid = R.color.colorPrimaryDark;
            bar.setProgress(1);
        }
        else if (level < 10) {
            colorid = R.color.level1;
            gradientid = R.color.level1_gredient;
        } else if (level < 20) {
            colorid = R.color.level2;
            gradientid = R.color.level2_gradient;
        } else if (level < 30) {
            colorid = R.color.level3;
            gradientid = R.color.level3_gradient;
        } else if (level < 40) {
            colorid = R.color.level4;
            gradientid = R.color.level4_gradient;
        } else if (level < 50) {
            colorid = R.color.level5;
            gradientid = R.color.level5_gradient;
        } else if (level < 60) {
            colorid = R.color.level6;
            gradientid = R.color.level6_gradient;
        } else if (level < 70) {
            colorid = R.color.level7;
            gradientid = R.color.level7_gradient;
        } else if (level < 80) {
            colorid = R.color.level8;
            gradientid = R.color.level8_gradient;
        } else if (level < 90) {
            colorid = R.color.level9;
            gradientid = R.color.level9_gradient;
        } else if (level < 100) {
            colorid = R.color.level10;
            gradientid = R.color.level10_gradient;
        }
        bar.setStartColorId(colorid);
        bar.setCloseColorId(gradientid);
        background.setStartColorId(colorid);
        background.setCloseColorId(gradientid);
        return v;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
