package com.example.shereen.dipolmafinalproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import static android.content.Context.MODE_PRIVATE;
import static com.example.shereen.dipolmafinalproject.HomeActivity.products_lists;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserAccountFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserAccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserAccountFragment extends Fragment {
    SharedPreferences sharedPreferences;
    String user_details = "user_Details";

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
        rec.setLayoutManager(new LinearLayoutManager(getActivity()));

        RecycleAdapter recadapter = new RecycleAdapter(products_lists);
        rec.setAdapter(recadapter);
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
