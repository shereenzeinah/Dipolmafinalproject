package com.example.shereen.dipolmafinalproject;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by lenovo on 10/24/2018.
 */

public class RecycleViewAdapter extends RecyclerView.Adapter <RecycleViewAdapter.RecyclerViewHolder>{


    @Override
    public RecycleViewAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_row , parent , false);
        RecyclerViewHolder holder =new RecyclerViewHolder(row);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecycleViewAdapter.RecyclerViewHolder holder, int position) {

    }



    @Override
    public int getItemCount() {
        return 0;
    }
    public class RecyclerViewHolder extends RecyclerView.ViewHolder{

        TextView name , price ;
        ImageView image ;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.productname);
            price= (TextView) itemView.findViewById(R.id.price);
            image=(ImageView) itemView.findViewById(R.id.productimage);

        }


    }
}
