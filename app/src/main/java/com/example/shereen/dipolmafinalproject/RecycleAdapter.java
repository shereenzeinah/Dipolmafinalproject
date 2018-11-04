package com.example.shereen.dipolmafinalproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by lenovo on 11/2/2018.
 */

public class RecycleAdapter extends RecyclerView.Adapter <RecycleAdapter.SecondRecyclerViewHolder> {


        ArrayList<Product> products = new ArrayList<>();

public RecycleAdapter(ArrayList<Product> product ) {
        this. products= product;

}
@Override
public RecycleAdapter.SecondRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_recyclerview_row , parent , false);
        SecondRecyclerViewHolder holder =new SecondRecyclerViewHolder(row);
        return holder;
        }

    @Override
    public void onBindViewHolder(SecondRecyclerViewHolder holder, int position) {
        Bitmap bmp = BitmapFactory.decodeByteArray(products.get(position).image, 0, products.get(position).image.length);
        holder.name.setText(products.get(position).name);
        holder.image.setImageBitmap(bmp);
    }

@Override
public int getItemCount() {
        return products.size();
        }

public class SecondRecyclerViewHolder extends RecyclerView.ViewHolder{

    TextView name  ;
    ImageView image ;
    Button edit , delete ;
    public SecondRecyclerViewHolder(View itemView) {
        super(itemView);
        name=(TextView)itemView.findViewById(R.id.productnameprofile);

        image=(ImageView) itemView.findViewById(R.id.productimageprofile);
        edit= (Button) itemView.findViewById(R.id.edit);
        delete= (Button) itemView.findViewById(R.id.delete);
    }


}
}
