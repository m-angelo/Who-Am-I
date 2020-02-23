package com.truncatedcone.app.whoami;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Friendlist extends Fragment {

    ConstraintLayout RelationLayout;
    ImageView AnimationFade;
    AnimationDrawable animationDrawable;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    String fid,sbirth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.list, container, false);
        RecyclerView recyclerView =  v.findViewById(R.id.Friends);
        Log.i("array",FriendlistArgs.fromBundle(getArguments()).toString());
        Log.i("is that array?",FriendlistArgs.fromBundle(getArguments()).toString());

        ArrayList<String> mNames = new ArrayList<String>(Arrays.asList(FriendlistArgs.fromBundle(getArguments()).getNames()));
        ArrayList<String> mAge = new ArrayList<String>(Arrays.asList(FriendlistArgs.fromBundle(getArguments()).getAges()));
        ArrayList<String> mLevel = new ArrayList<String>(Arrays.asList(FriendlistArgs.fromBundle(getArguments()).getLevels()));
        ArrayList<String> mPics = new ArrayList<String>(Arrays.asList(FriendlistArgs.fromBundle(getArguments()).getPics()));

        FriendAdapter adapter = new FriendAdapter(getActivity(),mNames, mAge, mLevel,mPics);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        Log.i("perm",mNames.toString());
        Log.i("perm",mPics.toString());


        return v;
    }



}




