package com.example.roundnetstattracker.recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roundnetstattracker.R;
import com.example.roundnetstattracker.model.Team;

import java.util.List;

public class TeamRecyclerViewAdapter extends RecyclerView.Adapter<TeamRecyclerViewAdapter.ViewHolder> {

    private List<Team> teams;
    private Context context;

    public TeamRecyclerViewAdapter(Context context, List<Team> teams){
        this.context = context;
        this.teams = teams;
    }

    @NonNull
    @Override
    public TeamRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_team_listitem, parent, false);
        TeamRecyclerViewAdapter.ViewHolder holder = new TeamRecyclerViewAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TeamRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.teamName.setText(teams.get(position).name);
        //holder.player1Name.setText(teams.get(position).player1.getName());
        //holder.player2Name.setText(teams.get(position).player2.getName());

        final int copy = position;
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(context, GameDetailsActivity.class);
                //intent.putExtra("TEAM_OBJECT", teams.get(copy));
                //context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return teams.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

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
