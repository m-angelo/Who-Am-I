package com.truncatedcone.app.whoami;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

public class Age extends Fragment {

    ConstraintLayout ViewLayout;

    String smonth,syear,fid,fpic,first,last;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.age_selector, container, false);
        String[] months = new String[]{"MONTH","01", "02", "03","04","05","06","07","08","09","10","11","12"};
        String[] years = new String[]{"YEAR","2000", "1999", "1998","1997","1996","1995","1994"};
        final FirebaseUser currentUser;
        final FirebaseAuth mAuth;
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        final Spinner month= v.findViewById(R.id.month);
        final Spinner year= v.findViewById(R.id.year);
        final Button confirm= v.findViewById(R.id.confirm);
        final Button hakjer= v.findViewById(R.id.hakjer);
        final View mv=v;
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        ArrayAdapter<String> monthadapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, months);
        ArrayAdapter<String> yearadapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, years);
        month.setAdapter(monthadapter);
        year.setAdapter(yearadapter);
        month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                smonth=  month.getSelectedItem().toString();
                }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                smonth = "";
            }
        });
        year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                syear=  year.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                syear = "";
            }
        });
        View.OnClickListener hakjerstwo = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            }
        };
        View.OnClickListener Conf = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (syear!="YEAR"& smonth!="MONTH") {
                    final View clickv = v;
                    GraphRequest request = GraphRequest.newMeRequest(
                            AccessToken.getCurrentAccessToken(),
                            new GraphRequest.GraphJSONObjectCallback() {
                                @Override
                                public void onCompleted(JSONObject object, GraphResponse response) {
                                    // Application code
                                    try {
                                        Log.i("Response",response.toString());
                                        String firstName = response.getJSONObject().getString("first_name");
                                        String lastName = response.getJSONObject().getString("last_name");
                                        com.facebook.Profile profile = com.facebook.Profile.getCurrentProfile();
                                        String id = profile.getId();
                                        String link = profile.getLinkUri().toString();
                                        Log.i("Link",link);
                                        if (com.facebook.Profile.getCurrentProfile()!=null)
                                        {
                                            Log.i("Login", "ProfilePic " +com.facebook.Profile.getCurrentProfile().getProfilePictureUri(200, 200));
                                        }
                                        Log.i("Login"+ "FirstName", firstName);
                                        Log.i("Login" + "LastName", lastName);
                                        fid=id;
                                        first=firstName;
                                        last=lastName;
                                        Map<String, Object> user_data = new HashMap<>();
                                        user_data.put("birth", syear+smonth);
                                        user_data.put("first",first );
                                        user_data.put("last",last);
                                        user_data.put("lang", "pl");
                                        user_data.put("fbid", fid);
                                        user_data.put("fbpic",""+com.facebook.Profile.getCurrentProfile().getProfilePictureUri(200, 200));
                                        db.collection("users").document(currentUser.getUid()).set(user_data);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                    request.executeAsync();   Bundle parameters = new Bundle();
                    parameters.putString("fields", "id,email,first_name,last_name");
                    request.setParameters(parameters);
                    request.executeAsync();
                    Navigation.findNavController(clickv).navigate(R.id.action_age2_to_menu);

                }
            }
        };
        confirm.setOnClickListener(Conf);
        hakjer.setOnClickListener(hakjerstwo);



        return v;
    }




}
