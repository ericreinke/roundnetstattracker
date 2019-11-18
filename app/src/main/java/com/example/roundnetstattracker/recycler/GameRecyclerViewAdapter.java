package com.example.roundnetstattracker.recycler;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roundnetstattracker.GameDetailsActivity;
import com.example.roundnetstattracker.R;
import com.example.roundnetstattracker.model.Game;

import java.util.List;

public class GameRecyclerViewAdapter extends RecyclerView.Adapter<GameRecyclerViewAdapter.ViewHolder>{

    private static final String TAG = "GameRecyclerViewAdapter";

    private List<Game> games;
    private Context context;

    public GameRecyclerViewAdapter(Context context, List<Game> games) {
        this.games = games;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG,"onBindViewHolder: called.");

        holder.team1Name.setText(games.get(position).teamGameProfileA.name);
        holder.team2Name.setText(games.get(position).teamGameProfileB.name);
        holder.team1Score.setText(games.get(position).teamGameProfileA.teamScore);
        holder.team2Score.setText(games.get(position).teamGameProfileB.teamScore);

        final int copy = position;
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, GameDetailsActivity.class);
                intent.putExtra("GAME_OBJECT", games.get(copy));
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return games.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView team1Name;
        TextView team2Name;
        TextView team1Score;
        TextView team2Score;
        RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            team1Name = itemView.findViewById(R.id.team1NameTextView);
            team2Name = itemView.findViewById(R.id.team2NameTextView);
            team1Score = itemView.findViewById(R.id.team1ScoreTextView);
            team2Score = itemView.findViewById(R.id.team2ScoreTextView);
            parentLayout = itemView.findViewById(R.id.game_parent_layout);

        }
    }
}
