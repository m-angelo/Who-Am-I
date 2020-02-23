package com.truncatedcone.app.whoami;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class StartPlay extends Fragment {
    ConstraintLayout StartPlayLayout;
    Context context = getContext();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.start_play, container, false);
        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        DocumentReference codesRef = rootRef.collection("questions").document("pl"); //Dodać zależność od języka
        final SimpleDraweeView profilepic = v.findViewById(R.id.avatar);
        Uri nduri= Uri.parse(StartPlayArgs.fromBundle(getArguments()).getPic());
        profilepic.setImageURI(nduri);
        final ImageView color = v.findViewById(R.id.level_color);
        final String[] transfer = {"q1","a1","b1","c1","d1","q2","a2","b2","c2","d2","q3","a3","b3","c3","d3"};
        final Button play = v.findViewById(R.id.play);
        TextView name = v.findViewById(R.id.name);
        Integer level = 0;
        final ProgressBar bar = v.findViewById(R.id.progressBar);
        final ImageView avatar = v.findViewById(R.id.avatar);
        Log.d("ctg",StartPlayArgs.fromBundle(getArguments()).getSelectedCateg());
        switch (StartPlayArgs.fromBundle(getArguments()).getSelectedCateg()) {
            case "S":
                name.setText("Starter");
                break;
            case "H":
                name.setText("Hobbies");
                break;
            case "C":
                name.setText("Culture");
                break;
            case "B":
                name.setText("Beliefs");
                break;
            case "M":
                name.setText("Morality");
                break;
            case "R":
                name.setText("Relationships");
                break;

        }
        name.setVisibility(name.VISIBLE);

        if (StartPlayArgs.fromBundle(getArguments()).getSelectedLvl() == "") {
            level = 0;
        }
        else {
            level = Integer.parseInt(StartPlayArgs.fromBundle(getArguments()).getSelectedLvl());
        }
            final View.OnClickListener PlayListener = new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    NavController navController = Navigation.findNavController(v);
                    String s_id = StartPlayArgs.fromBundle(getArguments()).getSelectedId();
                    String s_lvl = StartPlayArgs.fromBundle(getArguments()).getSelectedLvl();
                    String s_cat = StartPlayArgs.fromBundle(getArguments()).getSelectedCateg();
                    StartPlayDirections.ActionStartPlayToPlay action = StartPlayDirections.actionStartPlayToPlay(s_id,name.getText().toString(),s_lvl,transfer[0],transfer[1],transfer[2],transfer[3],transfer[4],transfer[5],transfer[6],transfer[7],transfer[8],transfer[9],transfer[10],transfer[11],transfer[12],transfer[13],transfer[14],StartPlayArgs.fromBundle(getArguments()).getPic());
                    navController.navigate(action);
                }
            };

        codesRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    Random rand = new Random();
                    List<String> list = new ArrayList<>();
                    List<String> selected = new ArrayList<>();
                    Map<String, Object> map = task.getResult().getData();
                    for (Map.Entry<String, Object> entry : map.entrySet()) {
                        if (entry.getKey().substring(0, 1).equals(StartPlayArgs.fromBundle(getArguments()).getSelectedCateg())){
                            list.add(entry.getKey());
                        }

                    }
                    Integer beginning = list.size();
                    if (beginning<3){
                        for (int i = 0; i < 3; i++) {
                            transfer[i * 5] = "?null";
                            transfer[1 + i * 5] = "1null";
                            transfer[2 + i * 5] = "2null";
                            transfer[3 + i * 5] = "3null";
                            transfer[4 + i * 5] = "4null";
                        }

                    }
                    else {
                        for (int i = 0; i < beginning; i++) {
                            int randomIndex = rand.nextInt(list.size());
                            String randomElement = list.get(randomIndex);
                            Log.i("data", task.getResult().get(randomElement).toString());
                            Log.d("list size", String.valueOf(list.size()));
                            selected.add(randomElement);
                            list.remove(randomIndex);
                        }
                        for (int i = 0; i < 3; i++) {
                            Map<String, String> items = (Map) task.getResult().get(selected.get(i));
                            transfer[i * 5] = items.get("Question");
                            transfer[1 + i * 5] = items.get("1");
                            transfer[2 + i * 5] = items.get("2");
                            transfer[3 + i * 5] = items.get("3");
                            transfer[4 + i * 5] = items.get("4");
                        }
                    }
                        bar.setVisibility(bar.INVISIBLE);
                        avatar.setVisibility(avatar.VISIBLE);
                        color.setVisibility(color.VISIBLE);
                        play.setVisibility(play.VISIBLE);
                        play.setOnClickListener(PlayListener);

                }
            }
        });
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                bar.setVisibility(bar.INVISIBLE);
                avatar.setVisibility(avatar.VISIBLE);
                color.setVisibility(color.VISIBLE);
                play.setVisibility(play.VISIBLE);

                play.setOnClickListener(PlayListener);
            }
        }, 1000);


            if (level < 10) {
                color.setColorFilter(ContextCompat.getColor(getActivity(), R.color.level1));
            } else if (level < 20) {
                color.setColorFilter(ContextCompat.getColor(getActivity(), R.color.level2));
            } else if (level < 30) {
                color.setColorFilter(ContextCompat.getColor(getActivity(), R.color.level3));
            } else if (level < 40) {
                color.setColorFilter(ContextCompat.getColor(getActivity(), R.color.level4));
            } else if (level < 50) {
                color.setColorFilter(ContextCompat.getColor(getActivity(), R.color.level5));
            } else if (level < 60) {
                color.setColorFilter(ContextCompat.getColor(getActivity(), R.color.level6));
            } else if (level < 70) {
                color.setColorFilter(ContextCompat.getColor(getActivity(), R.color.level7));
            } else if (level < 80) {
                color.setColorFilter(ContextCompat.getColor(getActivity(), R.color.level8));
            } else if (level < 90) {
                color.setColorFilter(ContextCompat.getColor(getActivity(), R.color.level9));
            } else if (level < 100) {
                color.setColorFilter(ContextCompat.getColor(getActivity(), R.color.level10));
            }
            return v;
        }


    }


