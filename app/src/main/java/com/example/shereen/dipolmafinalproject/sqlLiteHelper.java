package com.example.shereen.dipolmafinalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Shereen on 10/24/2018.
 */

public class sqlLiteHelper extends SQLiteOpenHelper {

    public static final String TAG="TEST1";

    public static final String database_name="rent_project";
    final String Table_Name1= "users";
    final String name_key= "name";
    final String phone_key= "phone";
    final String email_key= "email";
    final String lat_key= "lat";
    final String lng_key= "lng";

    final String password_key= "password";

    final String Table_Name2= "products";
    final String product_name= "pr_name";
    final String product_lat= "pr_lat";
    final String product_lng= "pr_lng";
    final String product_price= "pr_price";
    final String product_days= "pr_days";
    final String product_avail= "pr_avail";
    final String product_contact= "pr_contact";
    final String product_id= "pr_id";
    final public static String imagepath_key ="imagepath";


    public sqlLiteHelper(Context context) {
        super(context, database_name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql_users= " CREATE TABLE " + Table_Name1 + " ( " + name_key +" VARCHAR ," +
                phone_key+ " INTEGER ," +email_key+ " VARCHAR PRIMARY KEY," +lat_key+ " DOUBLE ,"+ lng_key + " DOUBLE ," +
                password_key + " VARCHAR )";
        String sql_products= " CREATE TABLE " + Table_Name2 + " ( " + product_name +" VARCHAR ," +
                product_lat+ " DOUBLE ,"+ product_lng + " DOUBLE ," +product_price+ " INTEGER ," +product_days+ " INTEGER ," +
                product_avail + " INTEGER ,"+ product_contact+" VARCHAR ,"+product_id+" INTEGER PRIMARY KEY ,"+imagepath_key+" BLOB )";

        db.execSQL(sql_users);
        db.execSQL(sql_products);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //function to insert a user in database
    public void insert_user(User user)
    {
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues values= new ContentValues();
        Log.d(TAG, "insert_user: " + user.email);
        values.put(name_key,user.name);
        values.put(phone_key,user.phone);
        values.put(lat_key,user.lat);
        values.put(lng_key,user.lng);
        values.put(email_key,user.email);
        values.put(password_key,user.password);

        db.insert(Table_Name1,null,values);
        // db.close();

    }
    //function to insert a product in database
    public void insert_product(Product product)
    {
        Log.d(TAG, "insert_product: ");
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues values= new ContentValues();

        values.put(product_name,product.name);
        values.put(product_lat,product.lat);
        values.put(product_lng,product.lng);
        values.put(product_price,product.price);
        values.put(product_days,product.days);
        values.put(product_avail,product.avail);
        values.put(product_contact,product.contact_name);
        values.put(product_id,product.id);
        values.put(imagepath_key,product.image);


        db.insert(Table_Name2,null,values);
        // db.close();
    }

    //Function to delete product
    public void delete_product(Product product)
    {
        SQLiteDatabase db= this.getWritableDatabase();
        db.delete(Table_Name2, product_id+" = "+product.id, null);
    }


    //Function to edit product
    public void edit_product(Product product)
    {
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put(product_name,product.name);
        values.put(product_lat,product.lat);
        values.put(product_lng,product.lng);
        values.put(product_price,product.price);
        values.put(product_days,product.days);
        values.put(product_avail,product.avail);
        values.put(product_contact,product.contact_name);
        values.put(product_id,product.id);
        values.put(imagepath_key,product.image);
        db.update(Table_Name2, values, product_id+" = "+product.id, null);
    }

    //Function to edit user
    public void edit_user(User user)
    {
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues values= new ContentValues();

        values.put(name_key,user.name);
        values.put(phone_key,user.phone);
        values.put(lat_key,user.lat);
        values.put(lng_key,user.lng);
        values.put(email_key,user.email);
        values.put(password_key,user.password);

        db.update(Table_Name1, values, email_key+"="+user.email, null);
    }

    //get a specific user data
    public User get_User(String user_email)
    {
        User found_user = null;
        String query= "SELECT * FROM " + Table_Name1;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst())
        {
            do
            {   String name=cursor.getString(0);
                int phone=Integer.parseInt(cursor.getString(1));
                String email=cursor.getString(2);
                double lat=cursor.getDouble(3);
                double lng=cursor.getDouble(4);
                String password=cursor.getString(5);
                if(user_email.equals(email))
                {
                    found_user = new User(name,phone,lat,lng,email,password);
                }

            }
            while (cursor.moveToNext());
        }
        return found_user;
    }
    //get a specific user data
    public Product get_product(int product_id)
    {
        Product found_product = null;
        String query= "SELECT * FROM " + Table_Name2;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst())
        {
            do
            {
                String pr_name=cursor.getString(0);
                double lat=Double.parseDouble(cursor.getString(1));
                double lng=Double.parseDouble(cursor.getString(2));
                int price=Integer.parseInt(cursor.getString(3));
                int days=Integer.parseInt(cursor.getString(4));
                int avail=Integer.parseInt(cursor.getString(5));
                String contact_name=cursor.getString(6);
                int id=Integer.parseInt(cursor.getString(7));
                byte [] image = cursor.getBlob(8);


                if(product_id==id)
                {
                    found_product = new Product(pr_name,lat,lng,price,days,avail,contact_name,id,image);
                }

            }
            while (cursor.moveToNext());
        }
        return found_product;
    }
    //return all users
    public ArrayList<User> get_Users_Data()
    {
        ArrayList<User> list = new ArrayList<User>();
        String query= "SELECT * FROM " + Table_Name1;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst())
        {
            do
            {   String name=cursor.getString(0);
                int phone=Integer.parseInt(cursor.getString(1));
                String email=cursor.getString(2);
                double lat=cursor.getDouble(3);
                double lng=cursor.getDouble(4);
                String password=cursor.getString(5);

                list.add( new User(name,phone,lat,lng,email,password));
            }
            while (cursor.moveToNext());
        }
        return list;
    }

    //returns all products in database
    public ArrayList<Product> get_Products_Data()
    {
        ArrayList<Product> list = new ArrayList<Product>();
        String query= "SELECT * FROM " + Table_Name2;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst())
        {
            do
            {
                String pr_name=cursor.getString(0);
                double lat=Double.parseDouble(cursor.getString(1));
                double lng=Double.parseDouble(cursor.getString(2));
                int price=Integer.parseInt(cursor.getString(3));
                int days=Integer.parseInt(cursor.getString(4));
                int avail=Integer.parseInt(cursor.getString(5));
                String contact_name=cursor.getString(6);
                int id=Integer.parseInt(cursor.getString(7));
                byte [] image = cursor.getBlob(8);


                list.add( new Product(pr_name,lat,lng,price,days,avail,contact_name,id,image));
            }

            while (cursor.moveToNext());
        }
        return list;
    }


}
