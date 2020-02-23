
package com.truncatedcone.app.whoami;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
        import android.support.constraint.ConstraintLayout;
        import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.scwang.wave.MultiWaveHeader;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class Relation extends Fragment {

    ConstraintLayout RelationLayout;
    AnimationDrawable animationDrawable;
    Context ctx = this.getContext();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.relation, container, false);
        RelationLayout = v.findViewById(R.id.relation);
        TextView name = v.findViewById(R.id.name);
        TextView obj_level = v.findViewById(R.id.level);
        final SimpleDraweeView profilepic = v.findViewById(R.id.relation_ico);
        Uri nduri= Uri.parse(RelationArgs.fromBundle(getArguments()).getPic());
        profilepic.setImageURI(nduri);
        final String id = RelationArgs.fromBundle(getArguments()).getSelectedId();
        int level =10;
        if ("" == RelationArgs.fromBundle(getArguments()).getSelectedLvl())
        {
            level=0;
            obj_level.setText("");
        }
        else
            {
            level = Integer.parseInt(RelationArgs.fromBundle(getArguments()).getSelectedLvl());
            obj_level.setText(RelationArgs.fromBundle(getArguments()).getSelectedLvl()+" lvl.");
            }

        Button cat1 = v.findViewById(R.id.btn1);
        Button cat2 = v.findViewById(R.id.btn2);
        Button cat3 = v.findViewById(R.id.btn3);
        Button cat4 = v.findViewById(R.id.btn4);
        Button cat5 = v.findViewById(R.id.btn5);
        MultiWaveHeader waveHeader = v.findViewById(R.id.wave);
        name.setText(id);
        int colorid = R.color.level1;
        int gradientid = R.color.level1_gredient;
        if (level == 0){
            colorid = R.color.colorPrimary;
            gradientid = R.color.colorPrimaryDark;
            waveHeader.setProgress(100);
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
        View.OnClickListener PlayListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                String categid = "S";
                if (b.getId() == R.id.btn1)
                {categid = "H";}
                else if (b.getId() == R.id.btn2)
                {categid = "C";}
                else if (b.getId() == R.id.btn3)
                {categid = "B";}
                else if (b.getId() == R.id.btn4)
                {categid = "M";}
                else if (b.getId() == R.id.btn5)
                {categid = "R";}

                NavController navController = Navigation.findNavController(v);
                RelationDirections.ActionRelationsToStartPlay action = RelationDirections.actionRelationsToStartPlay(RelationArgs.fromBundle(getArguments()).getSelectedLvl(),id,categid,RelationArgs.fromBundle(getArguments()).getPic());
                navController.navigate(action);
            }
        };
        cat1.setOnClickListener(PlayListener);
        cat2.setOnClickListener(PlayListener);
        cat3.setOnClickListener(PlayListener);
        cat4.setOnClickListener(PlayListener);
        cat5.setOnClickListener(PlayListener);

        waveHeader.setStartColorId(colorid);
        waveHeader.setCloseColorId(gradientid);
        LinearLayout bg = v.findViewById(R.id.frame);
        bg.setBackgroundColor(ContextCompat.getColor(getContext(),colorid));
        return v;
    }



}
