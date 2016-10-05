package com.example.connectdrive;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Gmail_Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Gmail_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Gmail_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    HashMap<String, List<String>> Contents_category;
   static List<String> Content_list;
   static ExpandableListView Exp_list;
    static ContentAdapter exadapter;



    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
 static ListView filelist;
   static ArrayAdapter adapter;
    static ArrayList<String> files;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Gmail_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Gmail_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Gmail_Fragment newInstance(String param1, String param2) {
        Gmail_Fragment fragment = new Gmail_Fragment();
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
        View view=inflater.inflate(R.layout.fragment_gmail_, container, false);

        Exp_list = (ExpandableListView) view.findViewById(R.id.exp_list);
        Contents_category = DataProvider.getInfo();
        Content_list = new ArrayList<String>(Contents_category.keySet());
        exadapter = new ContentAdapter(getActivity(), Contents_category,Content_list);
        Exp_list.setAdapter(exadapter);


        // Inflate the layout for this fragment
      /* filelist=(ListView)view.findViewById(R.id.gmaillistview);
        files=new ArrayList<String>();
        files.add("Photos");
        files.add("Videos");
        files.add("Pdf");
        files.add("Docs");
        files.add("Others");
        adapter=new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,files);

        filelist.setAdapter(adapter);
        filelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getActivity(),"Your select item is"+files.get(position),Toast.LENGTH_SHORT).show();
                Bundle bundle=new Bundle();
                bundle.putString("FileType",files.get(position));
                Files fragment = new Files();
                fragment.setArguments(bundle);
                android.support.v4.app.FragmentTransaction fragmentTransaction =
                        getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container,fragment);
                fragmentTransaction.addToBackStack("Files");
                fragmentTransaction.commit();

            }
        });
        */

        return view;


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }



}
