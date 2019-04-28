package com.example.shereen.dipolmafinalproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by lenovo on 11/2/2018.
 */

public class RecycleAdapter extends RecyclerView.Adapter <RecycleAdapter.SecondRecyclerViewHolder> {


     public static ArrayList<Product> products = new ArrayList<>();
    public ItemClickListener mClickListener;
    private LayoutInflater mInflater;
    public static int flag=0 ;



public RecycleAdapter(ArrayList<Product> product, Context context) {
        this. products= product;
    this.mInflater = LayoutInflater.from(context);


}
    @Override
    public int getItemCount() {
        return products.size();
    }
@Override
public RecycleAdapter.SecondRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View row = mInflater.inflate(R.layout.profile_recyclerview_row, parent, false);
        SecondRecyclerViewHolder holder =new SecondRecyclerViewHolder(row);
        return holder;
        }

    @Override
    public void onBindViewHolder(SecondRecyclerViewHolder holder, int position) {
        Bitmap bmp = BitmapFactory.decodeByteArray(products.get(position).image, 0, products.get(position).image.length);
        holder.name.setText(products.get(position).name);
        holder.image.setImageBitmap(bmp);
    }



public  class SecondRecyclerViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {

    TextView name;
    ImageView image;
     public  ImageButton option;


    public SecondRecyclerViewHolder(View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.productnameprofile);
        image = (ImageView) itemView.findViewById(R.id.productimageprofile);
        option = (ImageButton) itemView.findViewById(R.id.options);

        itemView.setOnClickListener(this);
        option.setOnClickListener(this);

    }



    @Override
    public void onClick(final View v) {
        if (mClickListener != null) mClickListener.onItemClick(v, getAdapterPosition());

    }



    void edit_product(View v , int position ){
        flag=1;
        AddProductFragment addProductFragment = new AddProductFragment();
        FragmentManager fragmentManager=((AppCompatActivity)v.getContext()).getSupportFragmentManager();
        FragmentTransaction fragmenttranscation=fragmentManager.beginTransaction();
        fragmenttranscation.replace(R.id.fragment,addProductFragment);
        fragmenttranscation.commit();
        String name =products.get(position).name;
        int price = products.get(position).price;
        byte[] image=products.get(position).image;

    }





}
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    Product getItem(int id) {
        return products.get(id);
    }



     public void update(int position){

        products.remove(position);
        notifyDataSetChanged();
    }


}
