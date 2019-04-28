package com.example.shereen.dipolmafinalproject;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static com.example.shereen.dipolmafinalproject.HomeActivity.products_lists;
import static com.example.shereen.dipolmafinalproject.HomeActivity.user_Email;
import static com.example.shereen.dipolmafinalproject.RecycleAdapter.products;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserAccountFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserAccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserAccountFragment extends Fragment  implements RecycleAdapter.ItemClickListener {
    SharedPreferences sharedPreferences;
    String user_details = "user_Details";
    public ArrayList<Product> myProducts_lists;


    static String TAG="TEST3";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public UserAccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserAccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserAccountFragment newInstance(String param1, String param2) {
        UserAccountFragment fragment = new UserAccountFragment();
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
        View v = inflater.inflate(R.layout.fragment_user_account, container, false);
        RecyclerView rec = (RecyclerView) v.findViewById(R.id.accountrecycler);
        int numberOfColumns = 1;
        rec.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));
        myProducts_lists = new ArrayList<>();

        sqlLiteHelper sql = new sqlLiteHelper(getActivity());
        myProducts_lists=sql.get_users_products(user_Email);
        products_lists=sql.get_Products_Data();

        RecycleAdapter recadapter = new RecycleAdapter(myProducts_lists , getActivity());
        recadapter.setClickListener(this);
        rec.setAdapter(recadapter);
        recadapter.notifyDataSetChanged();

        //   //add product button
        Button add_product = (Button) v.findViewById(R.id.addproduct);
        add_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddProductFragment addProductFragment = new AddProductFragment();
                FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                FragmentTransaction fragmenttranscation=fragmentManager.beginTransaction();
                fragmenttranscation.replace(R.id.fragment,addProductFragment);
                fragmenttranscation.commit();
            }
        });

        //get user first letter
        TextView profile_letter = (TextView) v.findViewById(R.id.profilealph_);
        sharedPreferences = this.getActivity().getSharedPreferences(user_details,MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "");
        char letter = email.charAt(0);
        profile_letter.setText(Character.toString(letter));

        //get user data including name , address etc.
        sqlLiteHelper sqlLiteHelper = new sqlLiteHelper(this.getActivity());
        User user = sqlLiteHelper.get_User(email);
        String username = user.name;
        TextView username_profile = (TextView) v.findViewById(R.id.usernameprofile);
        username_profile.setText(username);

        add_product = (Button) v.findViewById(R.id.addproduct);
        add_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddProductFragment addProductFragment = new AddProductFragment();
                FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                FragmentTransaction fragmenttranscation=fragmentManager.beginTransaction();
                fragmenttranscation.replace(R.id.fragment,addProductFragment);
                fragmenttranscation.commit();
            }
        });

        //  create pop window for options button -edit product- delete product



        return  v ;
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

    @Override
    public void onItemClick(final View v, final int position) {
        ImageButton option = (ImageButton) v.findViewById(R.id.options);


        if (v.getId() == option.getId()) {



            PopupMenu popup = new PopupMenu(v.getContext(), option);
            //Inflating the Popup using xml file
            popup.getMenuInflater().inflate(R.menu.home, popup.getMenu());

            //registering popup with OnMenuItemClickListener

            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {

                    String selected_item = item.toString();

                    if (selected_item.equals("Edit")) {



                    } else if (selected_item.equals("Delete")) {

                        delete_product(position,v);
                       /* Fragment frg = null;
                        frg = getFragmentManager().findFragmentByTag("UserAccountFragment");
                        Toast.makeText(getContext(),""+frg.getTag(),Toast.LENGTH_SHORT).show();
                        final FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.detach(frg);
                        ft.attach(frg);
                        ft.commitNow();
                        /*products.remove(position);
                        notifyDataSetChanged();*/

                    }
                    return false;
                }
            });
            popup.show();
        }

    }


    public void delete_product(final int position , View v) {

        final Dialog dialog = new Dialog(v.getContext());
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
                sqlLiteHelper sql = new sqlLiteHelper(getActivity());
                myProducts_lists=sql.get_users_products(user_Email);
                FragmentManager fragmentmanager =getActivity().getSupportFragmentManager();
                FragmentTransaction fragmenttranscation = fragmentmanager.beginTransaction().addToBackStack(null);
                UserAccountFragment fragment_user = new UserAccountFragment();
                fragmenttranscation.replace(R.id.fragment,fragment_user,"UserAccountFragment");
                fragmenttranscation.commit();


            }

        });
        dialog.show();
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

}
