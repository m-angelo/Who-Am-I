package com.truncatedcone.app.whoami;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.ArrayList;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;


public class NotifyAdapter extends RecyclerView.Adapter<NotifyAdapter.ViewHolder> {

    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mLvls = new ArrayList<>();
    private ArrayList<String> mCat = new ArrayList<>();
    private String mParent;
    private Context mContext;

    public NotifyAdapter(Context context,ArrayList<String> names,ArrayList<String> categories,ArrayList<String> levels,String parenting) {
        mNames = names;
        mCat = categories;
        mLvls = levels;
        mContext = context;
        mParent = parenting;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView name;
        public final ImageButton play;
        public final TextView category;

        public ViewHolder(View view) {
            super(view);
            name =  view.findViewById(R.id.name);
            category = view.findViewById(R.id.category);
            play = view.findViewById(R.id.play);

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
        int selected_layout = R.layout.notification;
        Log.d("tag",String.valueOf(viewType));
        if (viewType == 0) {  selected_layout = R.layout.notify_title; }
        else if (viewType != 0) {  selected_layout = R.layout.notification; }
        View itemView = LayoutInflater.from(parent.getContext()).inflate(selected_layout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NotifyAdapter.ViewHolder holder, final int position) {
        if (position > 0) {
            holder.name.setText(mNames.get(position));
            holder.category.setText(mCat.get(position));
            View.OnClickListener PlayListener = new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    ImageButton b = (ImageButton) v;
                    View parent = (View) v.getParent();
                    NavController navController = Navigation.findNavController(v);
                    Log.d("data",mLvls.get(position)+ mNames.get(position)+ mCat.get(position));
                    if (parent != null) {
                        if (mParent == "profile") {
                            ProfileDirections.ActionProfileToStartPlay action = ProfileDirections.actionProfileToStartPlay(mLvls.get(position), mNames.get(position), mCat.get(position).substring(0,1),"https://graph.facebook.com/v3.2/1398060903670089/picture?height=200&width=200&migration_overrides=%7Boctober_2012%3Atrue%7D");
                            navController.navigate(action);
                        } else if (mParent == "finish") {
                            FinishDirections.ActionFinishToStartplay action = FinishDirections.actionFinishToStartplay(mLvls.get(position), mNames.get(position), mCat.get(position).substring(0,1),"https://graph.facebook.com/v3.2/1398060903670089/picture?height=200&width=200&migration_overrides=%7Boctober_2012%3Atrue%7D");
                            navController.navigate(action);
                        }
                    }

                }
            };
            holder.play.setOnClickListener(PlayListener);
        }
    }



    @Override
    public int getItemCount() {
        return mNames.size();
    }}


