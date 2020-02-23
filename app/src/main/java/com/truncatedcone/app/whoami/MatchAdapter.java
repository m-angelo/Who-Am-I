package com.truncatedcone.app.whoami;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.ViewHolder> {

    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mAges = new ArrayList<>();
    private ArrayList<String> mLevels = new ArrayList<>();
    private Context mContext;

    public MatchAdapter(Context context, ArrayList<String> names,ArrayList<String> ages,ArrayList<String> levels) {
        mNames = names;
        mAges = ages;
        mLevels = levels;
        mContext = context;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView age;
        public TextView levels;
        public ImageButton game;
        public ImageButton chat;


        public ViewHolder(View view) {
            super(view);
            name =  view.findViewById(R.id.name);
            age = view.findViewById(R.id.age);
            levels =view.findViewById(R.id.prof_lvl);
            game = view.findViewById(R.id.play);
            chat = view.findViewById(R.id.chat);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int selected_layout = R.layout.user_card;
        Log.d("tag",String.valueOf(viewType));
        View itemView = LayoutInflater.from(parent.getContext()).inflate(selected_layout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

            holder.name.setText( mNames.get(position));
            holder.age.setText( mAges.get(position)+" lat");
            holder.levels.setText(mLevels.get(position)+" lvl");
            final int pos = position;
             View.OnClickListener PlayListener = new View.OnClickListener() {

                 @Override
                 public void onClick(View v) {
                     ImageButton b = (ImageButton) v;
                     View parent = (View) v.getParent();
                     TextView txtname = parent.findViewById(R.id.name);
                     TextView txtlvl = parent.findViewById(R.id.prof_lvl);
                     NavController navController = Navigation.findNavController(v);
                     MatcherDirections.ActionMatcherToStartplay action = MatcherDirections.actionMatcherToStartplay( mLevels.get(pos), (txtname.getText()).toString(),"S","https://graph.facebook.com/v3.2/1398060903670089/picture?height=200&width=200&migration_overrides=%7Boctober_2012%3Atrue%7D");
                     navController.navigate(action);

                 }
             };
             holder.game.setOnClickListener(PlayListener);

    }

    @Override
    public int getItemCount() {
        return mNames.size();
    }
}