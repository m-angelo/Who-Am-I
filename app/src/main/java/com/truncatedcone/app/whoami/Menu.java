package com.truncatedcone.app.whoami;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class Menu extends Fragment {
    ImageButton viewid;
    ScrollView MainLayout;
    ConstraintLayout BodyLayout;
    public View sv;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    String lang ="en",fid,fpic;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Fresco.initialize(getContext());
        View v = inflater.inflate(R.layout.menu2, container, false);
        sv = v;
        //getActivity().recreate();
        final List<String> fage = new ArrayList<>();
        final List<String> fnames = new ArrayList<>();
        final List<String> flevels = new ArrayList<>();
        final List<String> fpics = new ArrayList<>();
        final Map<String, Object> Data = new HashMap<>();
        fage.add("");
        fnames.add("");
        flevels.add("");
        fpics.add("");
        Profile.getCurrentProfile().getId();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        final SimpleDraweeView profilepic = v.findViewById(R.id.you);
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        Log.d("perm",AccessToken.getCurrentAccessToken().getPermissions().toString());
        Uri fburi= Uri.parse(""+com.facebook.Profile.getCurrentProfile().getProfilePictureUri(200, 200));
        Uri nduri= Uri.parse("https://graph.facebook.com/v3.2/2194193533998767/picture?height=200&width=200&migration_overrides=%7Boctober_2012%3Atrue%7D");
        profilepic.setImageURI(fburi);
        GraphRequest.Callback gCallback = new GraphRequest.Callback() {
            public void onCompleted(GraphResponse response) {
                JSONObject jsonObj = (response.getJSONObject());
                try {
                    JSONArray json_array = jsonObj.getJSONArray("data");
                    final Date currentDate = new Date();
                    CollectionReference colRef = db.collection("users");

                    for (int i = 0; i < json_array.length(); i++) {
                        Log.i("tag",(i)+"  "+(json_array.length()));
                        colRef.whereEqualTo("fbid", json_array.getJSONObject(i).getString("id"))
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                Map<String, Object> nestedData = new HashMap<>();
                                                nestedData.put("level", 0);
                                                nestedData.put("exp", 0);
                                                Data.put(document.getId(),nestedData);
                                                db.collection("friends").document(mUser.getUid()).update(document.getId(),nestedData);

                                                SimpleDateFormat birthDate = new SimpleDateFormat("yyyyMM");
                                                Date birth = new Date();
                                                try {
                                                    birth = birthDate.parse(document.getString("birth"));
                                                } catch (ParseException e) {
                                                    e.printStackTrace();
                                                }
                                                DateFormat formatter = new SimpleDateFormat("yyyyMM");
                                                int d1 = Integer.parseInt(formatter.format(birth));
                                                int d2 = Integer.parseInt(formatter.format(currentDate));
                                                int age = (d2 - d1) / 100;


                                                String name = document.getString("first") + " " + document.getString("last");
                                                Log.i("name", (name));
                                                fage.add(String.valueOf(age));
                                                fnames.add(name);
                                                if(document.getString("first").equals("Katarzyna")) {
                                                    flevels.add("52");

                                                }else if (document.getString("first").equals("Szymon")) {
                                                    flevels.add("65");
                                                }
                                                else{flevels.add("0");}

                                                fpics.add(document.getString("fbpic"));

                                            }

                                        } else {
                                            Log.d("tag", "Error getting documents: ", task.getException());
                                        }
                                    }
                                });



                    }

                } catch (JSONException e) {
                }



            }
        };

        final GraphRequest request = new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/friends", null, HttpMethod.GET, gCallback);
        request.executeAsync();   Bundle parameters = new Bundle();



        MainLayout = v.findViewById(R.id.menu);
        BodyLayout = v.findViewById(R.id.content);
        TextView Title= v.findViewById(R.id.titleview);
        final ImageButton relation1=(ImageButton)v.findViewById(R.id.first);
        final  SimpleDraweeView relation2=v.findViewById(R.id.second);
        final ImageButton relation3=(ImageButton)v.findViewById(R.id.third);
        ImageButton findrel=(ImageButton)v.findViewById(R.id.find);
        ImageButton groupmap=(ImageButton)v.findViewById(R.id.map);
        relation2.setImageURI(nduri);

        final TextView allnotify = v.findViewById(R.id.all_notify);
        final ImageButton settings=(ImageButton)v.findViewById(R.id.settBTN);
        final ImageButton list=(ImageButton)v.findViewById(R.id.listBTN);
        final Animation frombot = AnimationUtils.loadAnimation(getActivity(),R.anim.menu_anim);
        final Animation fromtop = AnimationUtils.loadAnimation(getActivity(),R.anim.fromtop);
        final Animation fadein = AnimationUtils.loadAnimation(getActivity(),R.anim.playfadein);
        final Animation fadeout = AnimationUtils.loadAnimation(getActivity(),R.anim.fadeout);
        final Animation scaleup = AnimationUtils.loadAnimation(getActivity(),R.anim.scaleup);
        scaleup.setStartOffset(800);
        allnotify.setAnimation(scaleup);
        BodyLayout.setAnimation(frombot);
        fadein.setStartOffset(500);
        Title.setAnimation(fadein);

        View.OnClickListener RelationListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String s_id = "Szymon Stasik";
                String s_lvl= "65";
                String s_pic="";
                if (v.getId()==R.id.first) {
                    s_id = "Szymon Stasik";
                    s_lvl= "65";
                    s_pic="https://graph.facebook.com/v3.3/1572695402862806/picture?height=200&width=200&migration_overrides=%7Boctober_2012%3Atrue%7D";

                }
                else if (v.getId()==R.id.second) {
                    s_id = "Katarzyna Wyszyńska";
                    s_lvl= "52";
                    s_pic="https://graph.facebook.com/v3.2/2194193533998767/picture?height=200&width=200&migration_overrides=%7Boctober_2012%3Atrue%7D";
                }
                else if (v.getId()==R.id.third) {
                    s_id = "Kuba Jurzak";
                    s_lvl= "45";

                }
                NavController navController = Navigation.findNavController(v);
                MenuDirections.ActionMenuToRelations action = MenuDirections.actionMenuToRelations(s_id,s_lvl,s_pic);
                navController.navigate(action);
            }
        };
        View.OnClickListener SettListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                 NavController navController = Navigation.findNavController(sv);
                navController.navigate(R.id.action_menu_to_settings);
            }
        };
        View.OnClickListener ListListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(sv);
                String[] age_arr = fage.toArray(new String[fage.size()]);
                String[] name_arr = fnames.toArray(new String[fnames.size()]);
                String[] level_arr = flevels.toArray(new String[flevels.size()]);
                String[] pics_arr = fpics.toArray(new String[fpics.size()]);
                MenuDirections.ActionMenuToFriendlist action = MenuDirections.actionMenuToFriendlist(name_arr,level_arr,age_arr,pics_arr);
                navController.navigate(action);
            }
        };
        View.OnClickListener ProfListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(sv);
                navController.navigate(R.id.action_menu_to_profile);
            }
        };
        View.OnClickListener MatchListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(sv);
                navController.navigate(R.id.action_menu_to_matchLoader);
            }
        };
        View.OnClickListener MapListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(sv);
                Log.i("to","sie dzieje");
                navController.navigate(R.id.action_menu_to_geo);
            }
        };

        groupmap.setOnClickListener(MapListener);
        findrel.setOnClickListener(MatchListener);
        relation1.setOnClickListener(RelationListener);
        relation2.setOnClickListener(RelationListener);
        relation3.setOnClickListener(RelationListener);
        profilepic.setOnClickListener(ProfListener);
        list.setOnClickListener(ListListener);
        settings.setOnClickListener(SettListener);
        return v;

    }




}
