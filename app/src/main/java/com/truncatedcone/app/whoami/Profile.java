package com.truncatedcone.app.whoami;

import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.Arrays;

public class Profile extends Fragment {

    ConstraintLayout Layout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.profile, container, false);

        Layout = v.findViewById(R.id.profile);
        String[] aNames = {""+"Szymon Stasik","Natalia Fitowska","Kuba Jurzak","Oliwia Kowalska"};
        String[] aLevels = {""+"65","52","45","33"};
        String[] aCat = {""+"Morality","Culture","Beliefs","Hobbies"};
        final SimpleDraweeView profilepic = v.findViewById(R.id.avatar);
        com.facebook.Profile profile = com.facebook.Profile.getCurrentProfile();
        Uri furi= Uri.parse(""+com.facebook.Profile.getCurrentProfile().getProfilePictureUri(200, 200));
        profilepic.setImageURI(furi);
        ArrayList<String> mNames = new ArrayList<String>(Arrays.asList(aNames));
        ArrayList<String> mCat = new ArrayList<String>(Arrays.asList(aCat));
        ArrayList<String> mLevels = new ArrayList<String>(Arrays.asList(aLevels));
        RecyclerView recyclerView =  v.findViewById(R.id.notify_list);
        NotifyAdapter adapter = new NotifyAdapter(getActivity(),mNames, mCat,mLevels,"profile");
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity() ));
        return v;
    }



}