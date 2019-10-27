package com.example.roundnetstattracker;

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

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private static final String TAG = "RecyclerViewAdapter";

    private JsonArray arr;
    private Context context;

    public RecyclerViewAdapter(Context context, JsonArray arr) {
        this.arr = arr;
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

        holder.team1Name.setText(arr.get(position).getAsJsonObject().get("teamA").getAsJsonObject().get("name")+"");
        holder.team2Name.setText(arr.get(position).getAsJsonObject().get("teamB").getAsJsonObject().get("name")+"");
        holder.team1Score.setText(arr.get(position).getAsJsonObject().get("teamA").getAsJsonObject().get("score")+"");
        holder.team2Score.setText(arr.get(position).getAsJsonObject().get("teamB").getAsJsonObject().get("score")+"");
        System.out.println(arr.get(position).getAsJsonObject().get("teamA").getAsJsonObject().get("name")+"");
        System.out.println(arr.get(position).getAsJsonObject().get("teamB").getAsJsonObject().get("name")+"");

        final int copy = position;
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,GameDetails.class);
                intent.putExtra("GAME_OBJECT", arr.get(copy).getAsJsonObject().toString());
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return arr.size();
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
            parentLayout = itemView.findViewById(R.id.parent_layout);

        }
    }
}
