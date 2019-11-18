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
import com.example.roundnetstattracker.model.Player;

import java.util.List;

public class PlayerRecyclerViewAdapter extends RecyclerView.Adapter<PlayerRecyclerViewAdapter.ViewHolder> {

    private List<Player> players;
    private Context context;

    public PlayerRecyclerViewAdapter(Context context, List<Player> players){
        this.context = context;
        this.players = players;
    }

    @NonNull
    @Override
    public PlayerRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_player_listitem, parent, false);
        PlayerRecyclerViewAdapter.ViewHolder holder = new PlayerRecyclerViewAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.playerName.setText(players.get(position).name);

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
        return players.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView playerName;
        RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            playerName = itemView.findViewById(R.id.playerListItemNameTextView);
            parentLayout = itemView.findViewById(R.id.player_parent_layout);
        }
    }
}
