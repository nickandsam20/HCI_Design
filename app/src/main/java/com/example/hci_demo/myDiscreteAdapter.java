package com.example.hci_demo;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class myDiscreteAdapter extends RecyclerView.Adapter<myDiscreteAdapter.ViewHolder> {

    ArrayList<menu_item_source> source_list;
    private  OnItemClickListener mListener;
    public interface OnItemClickListener{
         void onItemClick(int position);
    }

    public myDiscreteAdapter(ArrayList<menu_item_source>source_list){
        this.source_list=source_list;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.menu_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        int d=source_list.get(position).getDrawable();
        ImageButton.OnClickListener l=source_list.get(position).getListener();
        holder.btn.setBackgroundResource(d);
        holder.btn.setOnClickListener(l);
    }




    @Override
    public int getItemCount() {
        return 5;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageButton btn;

        public ViewHolder(View itemView) {
            super(itemView);
            btn = itemView.findViewById(R.id.menu_btn);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }
}
