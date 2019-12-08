package com.example.roundnetstattracker.recycler;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roundnetstattracker.GameDetailsActivity;
import com.example.roundnetstattracker.R;
import com.example.roundnetstattracker.model.TeamGameProfile;

import java.util.List;

public class GameRecyclerViewAdapter extends RecyclerView.Adapter<GameRecyclerViewAdapter.ViewHolder>{

    private static final String TAG = "GameRecyclerViewAdapter";

    private List<TeamGameProfile> teamAProfiles;
    private List<TeamGameProfile> teamBProfiles;
    private Context context;

    public GameRecyclerViewAdapter(Context context, List<TeamGameProfile> teamAProfiles, List<TeamGameProfile> teamBProfiles) {
        this.teamAProfiles = teamAProfiles;
        this.teamBProfiles = teamBProfiles;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_game_listitem, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.teamsTextView.setText(String.format("%s vs %s",
                teamAProfiles.get(position).teamName, teamBProfiles.get(position).teamName));
        holder.scoresTextView.setText(String.format("%s vs %s",
                teamAProfiles.get(position).teamScore,teamBProfiles.get(position).teamScore));

        final int copy = position;
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, GameDetailsActivity.class);
                i.putExtra("TEAM_A", teamAProfiles.get(copy));
                i.putExtra("TEAM_B", teamBProfiles.get(copy));
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return teamAProfiles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView teamsTextView;
        TextView scoresTextView;
        RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            teamsTextView = itemView.findViewById(R.id.teamsTextView);
            scoresTextView = itemView.findViewById(R.id.scoresTextView);
            parentLayout = itemView.findViewById(R.id.game_parent_layout);
        }
    }
}
