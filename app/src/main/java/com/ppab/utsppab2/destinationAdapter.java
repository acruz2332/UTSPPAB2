package com.ppab.utsppab2;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class destinationAdapter extends RecyclerView.Adapter<destinationAdapter.ListViewHolder> {
    private ArrayList<destination> destinations;
    private Context context;
    private Activity activity;

    public destinationAdapter(ArrayList<destination> destinations, Context context, Activity activity) {
        this.destinations = destinations;
        this.context = context;
        this.activity = activity;
    }

    @NonNull
    @Override
    public destinationAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.item_wishlist, parent, false);

        destinationAdapter.ListViewHolder viewHolder = new destinationAdapter.ListViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull destinationAdapter.ListViewHolder holder, int position) {
        holder.tvCity.setText(destinations.get(position).getCity().toString());
        holder.tvState.setText(destinations.get(position).getState().toString());
        holder.tvCountry.setText(destinations.get(position).getCountry().toString());
//        Toast.makeText(context, ""+destinations.get(position).isVisited(), Toast.LENGTH_SHORT).show();
        if (destinations.get(position).isVisited()){
            holder.cvDestination.setCardBackgroundColor(Color.GREEN);
            holder.ivChecked.setVisibility(View.INVISIBLE);
        }

        holder.ivChecked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        destinations.get(position).setVisited(true);
                        holder.cvDestination.setCardBackgroundColor(Color.GREEN);
                    }
                }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).setMessage(R.string.mark);
                builder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return destinations.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder{
        TextView tvCity, tvState, tvCountry;
        ImageView ivChecked;
        CardView cvDestination;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCountry = itemView.findViewById(R.id.tvCountry);
            tvCity = itemView.findViewById(R.id.tvCity);
            tvState = itemView.findViewById(R.id.tvState);
            ivChecked = itemView.findViewById(R.id.ivChecked);
            cvDestination = itemView.findViewById(R.id.cvDestination);
        }
    }
}
