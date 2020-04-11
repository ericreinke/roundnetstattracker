package com.example.roundnetstattracker.recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roundnetstattracker.room.OnRecyclerViewTeamClickListener;
import com.example.roundnetstattracker.R;
import com.example.roundnetstattracker.model.Team;

import java.util.List;

public class SelectTeamRecyclerViewAdapter extends RecyclerView.Adapter<SelectTeamRecyclerViewAdapter.ViewHolder> {

    private List<Team> allTeams;
    private List<String> allPlayerNames;
    private Context context;
    private OnRecyclerViewTeamClickListener onRecyclerViewTeamClickListener;

    public SelectTeamRecyclerViewAdapter (Context context, List<Team> allTeams, List<String> allPlayerNames, OnRecyclerViewTeamClickListener onRecyclerViewTeamClickListener){
        this.allTeams = allTeams;
        this.allPlayerNames = allPlayerNames;
        this.context = context;
        this.onRecyclerViewTeamClickListener = onRecyclerViewTeamClickListener;
    }

    @NonNull
    @Override
    public SelectTeamRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_team_listitem, parent, false);
        SelectTeamRecyclerViewAdapter.ViewHolder holder = new SelectTeamRecyclerViewAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.teamName.setText(allTeams.get(position).name);
        holder.player1Name.setText(allPlayerNames.get(position*2));
        holder.player2Name.setText(allPlayerNames.get(position*2+1));

        final int copy = position;
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRecyclerViewTeamClickListener.onTeamClick(allTeams.get(copy));
            }
        });
    }

    @Override
    public int getItemCount() {
        return allTeams.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView teamName;
        TextView player1Name;
        TextView player2Name;
        RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            teamName = itemView.findViewById(R.id.teamListItemTeamNameTextView);
            player1Name = itemView.findViewById(R.id.teamListItemPlayer1NameTextView);
            player2Name = itemView.findViewById(R.id.teamListItemPlayer2NameTextView);
            parentLayout = itemView.findViewById(R.id.team_parent_layout);
        }
    }
}






