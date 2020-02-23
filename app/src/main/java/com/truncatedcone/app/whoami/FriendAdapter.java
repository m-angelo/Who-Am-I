package com.truncatedcone.app.whoami;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import org.w3c.dom.Text;

import java.util.ArrayList;
        import java.util.List;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.ViewHolder> {

    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mAges = new ArrayList<>();
    private ArrayList<String> mLevels = new ArrayList<>();
    private  ArrayList<String> mPics = new ArrayList<>();
    private Context mContext;

    public FriendAdapter(Context context, ArrayList<String> names,ArrayList<String> ages,ArrayList<String> levels,ArrayList<String> pics) {
        mNames = names;
        mAges = ages;
        mLevels = levels;
        mPics = pics;
        mContext = context;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView age;
        public Button levels;
        public ImageView level_bg;
        public SimpleDraweeView avatar;

        public ViewHolder(View view) {
            super(view);
            avatar = view.findViewById(R.id.avatar);
            name =  view.findViewById(R.id.name);
            age = view.findViewById(R.id.age);
            levels =view.findViewById(R.id.level_button);
            level_bg = view.findViewById(R.id.level_bg);
        }
    }
    @Override
    public int getItemViewType(int position) {

        if (position == 0) {
            return 0;
        } else {
            return 1;
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int selected_layout = R.layout.friend_row;
        if (viewType == 0) {  selected_layout = R.layout.blank_row; }
        else if (viewType != 0) {  selected_layout = R.layout.friend_row; }
        Log.d("tag",String.valueOf(viewType));
        View itemView = LayoutInflater.from(parent.getContext()).inflate(selected_layout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Fresco.initialize(mContext);
        if (position>0){
            Log.i("pos",String.valueOf(position));
            Log.i("uri",mPics.get(position));
            Uri fburi= Uri.parse(mPics.get(position));
            holder.avatar.setImageURI(fburi);
            holder.name.setText( mNames.get(position));
            holder.age.setText( mAges.get(position)+" lat");
            int ilevel;
            if (mLevels.get(position) != "") {
                 ilevel = Integer.valueOf(mLevels.get(position));
            }else {
                 ilevel = 0;
            }
            if (ilevel == 0){

                holder.levels.setText("");
                holder.levels.setBackgroundResource(R.drawable.circle);
                holder.level_bg.setImageResource(R.drawable.begin);
                View.OnClickListener RelationListener = new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Button b = (Button)v;
                        View parent = (View)v.getParent();
                        NavController navController = Navigation.findNavController(v);
                        if (parent != null) {
                            TextView txtView = parent.findViewById(R.id.name);
                            FriendlistDirections.ActionFriendlistToStartPlay action = FriendlistDirections.actionFriendlistToStartPlay("",(txtView.getText()).toString(),"S",mPics.get(position));
                            navController.navigate(action);
                        }

                    }
                };
                holder.levels.setOnClickListener(RelationListener);

                }
            else {
                holder.levels.setText(mLevels.get(position));
                View.OnClickListener RelationListener = new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Button b = (Button)v;
                        View parent = (View)v.getParent();
                        NavController navController = Navigation.findNavController(v);
                        if (parent != null) {
                            TextView txtView = parent.findViewById(R.id.name);
                            FriendlistDirections.ActionFriendlistToRelations action = FriendlistDirections.actionFriendlistToRelations((txtView.getText()).toString(),b.getText().toString(),mPics.get(position));
                            navController.navigate(action);
                        }

                    }
                };
                holder.levels.setOnClickListener(RelationListener);
                if (ilevel < 10) {
                    holder.level_bg.setColorFilter(ContextCompat.getColor(mContext,R.color.level1));
                } else if (ilevel < 20) {
                    holder.level_bg.setColorFilter(ContextCompat.getColor(mContext,R.color.level2));
                } else if (ilevel < 30) {
                    holder.level_bg.setColorFilter(ContextCompat.getColor(mContext, R.color.level3));
                } else if (ilevel < 40) {
                    holder.level_bg.setColorFilter(ContextCompat.getColor(mContext,R.color.level4));
                } else if (ilevel < 50) {
                    holder.level_bg.setColorFilter(ContextCompat.getColor(mContext,R.color.level5));
                } else if (ilevel < 60) {
                    holder.level_bg.setColorFilter(ContextCompat.getColor(mContext,R.color.level6));
                } else if (ilevel < 70) {
                    holder.level_bg.setColorFilter(ContextCompat.getColor(mContext,R.color.level7));
                } else if (ilevel < 80) {
                    holder.level_bg.setColorFilter(ContextCompat.getColor(mContext,R.color.level8));
                } else if (ilevel < 90) {
                    holder.level_bg.setColorFilter(ContextCompat.getColor(mContext,R.color.level9));
                } else if (ilevel < 100) {
                    holder.level_bg.setColorFilter(ContextCompat.getColor(mContext,R.color.level10));
                }
        }

        }



    }

    @Override
    public int getItemCount() {
        return mNames.size();
    }
}
/*
docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
@Override
public void onComplete(@NonNull Task<DocumentSnapshot> task) {
        if (task.isSuccessful()) {
        DocumentSnapshot document = task.getResult();
        if (document.exists()) {
        Log.d("frienddb", "DocumentSnapshot data: " + document.get("birth"));
        sbirth=""+document.get("birth");
        } else {
        Log.d("frienddb", "No such document");
        sbirth="200007";
        }
        Date birth=new Date();
        SimpleDateFormat birthDate =new SimpleDateFormat("yyyyMM");
        try {
        birth=birthDate.parse(sbirth);
        } catch (ParseException e) {
        e.printStackTrace();
        }
        Date currentDate=new Date();
        DateFormat formatter = new SimpleDateFormat("yyyyMM");
        int d1 = Integer.parseInt(formatter.format(birth));
        int d2 = Integer.parseInt(formatter.format(currentDate));
        int age = (d2 - d1)/100;
        Log.d("birth", String.valueOf(d1));
        Log.d("current",String.valueOf(d2));
        Log.d("age", String.valueOf(age));

        } else {
        Log.d("frienddb", "get failed with ", task.getException());
        }
        }
        }
        */