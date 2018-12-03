package com.example.shereen.dipolmafinalproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.text.DecimalFormat;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProductDetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProductDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductDetailsFragment extends Fragment {
    public final static String TAG = "TEST001";
    SharedPreferences sharedPreferences;
    String user_details = "user_Details";
    public  double user_lat,user_lng;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER*
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
   public Product product_list_details_page ;
    private OnFragmentInteractionListener mListener;

    public ProductDetailsFragment() {

    }

    @SuppressLint("ValidFragment")
    public ProductDetailsFragment(Product products) {
        // Required empty public constructor
        this.product_list_details_page=products;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductDetailsFragment newInstance(String param1, String param2) {
        ProductDetailsFragment fragment = new ProductDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_product_details, container, false);
        ImageView productimage = (ImageView) v.findViewById(R.id.product_detail_image);
        TextView productname = (TextView) v.findViewById(R.id.pd_productname);
        TextView productprice = (TextView) v.findViewById(R.id.pd_price);
        TextView secondprice = (TextView) v.findViewById(R.id.pd_productprice);
        TextView rentdays = (TextView) v.findViewById(R.id.pd_rentdays);
        TextView ownwername = (TextView) v.findViewById(R.id.pd_ownername);
        TextView ownwerphone = (TextView) v.findViewById(R.id.pd_ownerpphone);
        TextView ownermail = (TextView) v.findViewById(R.id.pd_ownwermail);
        TextView location = (TextView) v.findViewById(R.id.pd_productlocation);
        Button avaialable = (Button) v.findViewById(R.id.available);
        final Button rent = (Button) v.findViewById(R.id.rent);
        TextView delivery = (TextView) v.findViewById(R.id.delivery);

        // set product information in product details page
        productname.setText(product_list_details_page.getName());
        productprice.setText(String.valueOf(product_list_details_page.getPrice()));
        secondprice.setText(String.valueOf(product_list_details_page.getPrice()));
        rentdays.setText(String.valueOf(product_list_details_page.getDays()));
        double pr_lat,pr_lng;
        pr_lat = product_list_details_page.getLat();
        pr_lng = product_list_details_page.getLng();

        // convert product image from bytes to bitmap then set it
        Bitmap bmp = BitmapFactory.decodeByteArray(product_list_details_page.getImage(), 0, product_list_details_page.getImage().length);
        productimage.setImageBitmap(bmp);

        // set user information in product details page
        sqlLiteHelper sqlLiteHelper = new sqlLiteHelper(getActivity());
        User user=  sqlLiteHelper.get_User(product_list_details_page.getContact_name());

        //get my user location
        sharedPreferences = getContext().getSharedPreferences(user_details,MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "");
        User my_user=  sqlLiteHelper.get_User(email);
        user_lat = my_user.getLat();
        user_lng = my_user.getLng();

        Log.d(TAG, "onCreateView: distance " +pr_lat+" "+ pr_lng+ " "+ user_lat+" "+user_lng);
        double distance = CalculationByDistance(pr_lat,pr_lng,user_lat,user_lng);
        if(distance>10)
        {
            delivery.setText("Out of your range("+distance+"Km away)");
        }
        else
        {
            delivery.setText("In your range!" + distance);
        }

        ownwername.setText(user.getName());
        ownwerphone.setText(String.valueOf(user.getPhone()));
        ownermail.setText(user.getEmail());

        // set product is available is not
        int aval ;
        aval=product_list_details_page.getAvail();
        if(aval==1)
            {
                avaialable.setText("available");
            }
        else
            {
            avaialable.setText(" Not available");
            avaialable.setBackgroundColor(avaialable.getContext().getResources().getColor(R.color.red));
             }

        // pop up menu appear
           rent.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   //Creating the instance of PopupMenu
                   PopupMenu popup = new PopupMenu(getActivity(), rent);
                   //Inflating the Popup using xml file
                   popup.getMenuInflater()
                           .inflate(R.menu.rent_days, popup.getMenu());

                   //registering popup with OnMenuItemClickListener

                   popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                       @Override
                       public boolean onMenuItemClick(MenuItem item) {
                           return false;
                       }
                   });
                   popup.show();
               }
           });

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    public double CalculationByDistance(double pr_lat,double pr_lng, double user_lat,double user_lng) {
        int Radius = 6371;// radius of earth in Km
        double lat1 = pr_lat;
        double lat2 = user_lat;
        double lon1 = pr_lng;
        double lon2 = user_lng;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
        Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
                + " Meter   " + meterInDec);

        return kmInDec;
    }

}
