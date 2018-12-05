package com.example.shereen.dipolmafinalproject;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by lenovo on 11/2/2018.
 */

public class RecycleAdapter extends RecyclerView.Adapter <RecycleAdapter.SecondRecyclerViewHolder> {


     public static    ArrayList<Product> products = new ArrayList<>();


public RecycleAdapter(ArrayList<Product> product) {
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

public   class SecondRecyclerViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {

    TextView name;
    ImageView image;
    ImageButton option;

    public SecondRecyclerViewHolder(View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.productnameprofile);
        image = (ImageView) itemView.findViewById(R.id.productimageprofile);
        option = (ImageButton) itemView.findViewById(R.id.options);

        itemView.setOnClickListener(this);
        option.setOnClickListener(this);

    }

    Product getItem(int id) {
        return products.get(id);
    }
    @Override
    public void onClick(final View v) {
        final int position;
        if (v.getId() == option.getId()) {
            position=   getAdapterPosition();


            PopupMenu popup = new PopupMenu(v.getContext(), option);
            //Inflating the Popup using xml file
            popup.getMenuInflater()
                    .inflate(R.menu.home, popup.getMenu());

            //registering popup with OnMenuItemClickListener

            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {

                    String selected_item = item.toString();

                    if (selected_item.equals("Edit")) {

                    } else if (selected_item.equals("Delete")) {

                       delete_product(position,v);
                    }
                    return false;
                }
            });
            popup.show();
        }

    }


    public void delete_product(final int position , View v) {



        final Dialog dialog = new Dialog(itemView.getContext());
        dialog.setContentView(R.layout.activity_dialoug);
        //TextView dialoug_title = (TextView) dialog.findViewById(R.id.title1);
        //dialoug_title.setText("Save");
        TextView content = (TextView) dialog.findViewById(R.id.content);
        content.setText("Are you sure you want to deiete this product?");
        Button dialogButton = (Button) dialog.findViewById(R.id.positive_button);
        Button no = (Button) dialog.findViewById(R.id.negative_button);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();

            }
        });
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final sqlLiteHelper sqlLiteHelper = new sqlLiteHelper(v.getContext());
                final Product product=  sqlLiteHelper.get_product(products.get(position).getId());
                sqlLiteHelper.delete_product(product);
                dialog.dismiss();

                products.remove(position);
                notifyDataSetChanged();

            }

        });
        dialog.show();
    }

}}
