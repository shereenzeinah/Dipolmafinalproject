package com.example.shereen.dipolmafinalproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by lenovo on 10/24/2018.
 */

public class RecycleViewAdapter extends RecyclerView.Adapter <RecycleViewAdapter.RecyclerViewHolder >{

    ArrayList<Product> product_list = new ArrayList<>();

    private LayoutInflater mInflater;
    public ItemClickListener mClickListener;

    public RecycleViewAdapter(ArrayList<Product> product_list , Context context) {


        this.mInflater = LayoutInflater.from(context);
        this.product_list = product_list;

    }

    @Override
    public RecycleViewAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = mInflater.inflate(R.layout.recyclerview_row, parent, false);
        RecyclerViewHolder holder =new RecyclerViewHolder(row);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecycleViewAdapter.RecyclerViewHolder holder, int position) {

        Bitmap bmp = BitmapFactory.decodeByteArray(product_list.get(position).image, 0, product_list.get(position).image.length);

        holder.name.setText(product_list.get(position).name);
        holder.price.setText( String.valueOf(product_list.get(position).price));
        holder.image.setImageBitmap(bmp);



    }



    @Override
    public int getItemCount() {
        return product_list.size();
    }
    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView name , price ;
       public ImageView image ;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.productname);
            price= (TextView) itemView.findViewById(R.id.price);
            image=(ImageView) itemView.findViewById(R.id.productimage);
            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            if (mClickListener != null) mClickListener.onItemClick(v, getAdapterPosition());
        }
    }
    Product getItem(int id) {
        return product_list.get(id);
    }
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }


    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
