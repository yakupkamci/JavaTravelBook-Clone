package com.yakupkamci.javatravelbook.adaptor;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yakupkamci.javatravelbook.databinding.RecyclerRowBinding;
import com.yakupkamci.javatravelbook.model.Place;
import com.yakupkamci.javatravelbook.view.MapsActivity;

import java.util.ArrayList;
import java.util.List;

public class PlaceAdaptor extends RecyclerView.Adapter<PlaceAdaptor.PlaceHolder> {

   List<Place> placeList;


    public PlaceAdaptor(List<Place> places){
        this.placeList = places;
    }

    @NonNull
    @Override
    public PlaceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerRowBinding recyclerRowBinding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);

        return new PlaceHolder(recyclerRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceHolder holder, int position) {
        int ps = position;
        holder.binding.recyclerViewText.setText(placeList.get(position).name);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), MapsActivity.class);
                intent.putExtra("place",placeList.get(ps));
                intent.putExtra("Info","old");
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return placeList.size();
    }

    public class PlaceHolder extends RecyclerView.ViewHolder{
        private RecyclerRowBinding binding;

        public PlaceHolder(RecyclerRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}


