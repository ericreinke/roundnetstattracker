package com.example.roundnetstattracker.recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roundnetstattracker.room.OnRecyclerViewPlayerClickListener;
import com.example.roundnetstattracker.R;
import com.example.roundnetstattracker.model.Player;

import java.util.List;

public class SelectPlayerRecyclerViewAdapter extends RecyclerView.Adapter<SelectPlayerRecyclerViewAdapter.ViewHolder> {

    private List<Player> players;
    private Context context;
    private OnRecyclerViewPlayerClickListener onRecyclerViewPlayerClickListener;

    public SelectPlayerRecyclerViewAdapter(Context context, List<Player> players, OnRecyclerViewPlayerClickListener onRecyclerViewPlayerClickListener){
        this.context = context;
        this.players = players;
        this.onRecyclerViewPlayerClickListener = onRecyclerViewPlayerClickListener;
    }

    @NonNull
    @Override
    public SelectPlayerRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_player_listitem, parent, false);
        SelectPlayerRecyclerViewAdapter.ViewHolder holder = new SelectPlayerRecyclerViewAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SelectPlayerRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.playerName.setText(players.get(position).name);

        final int copy = position;
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRecyclerViewPlayerClickListener.onPlayerClick(players.get(copy));
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
