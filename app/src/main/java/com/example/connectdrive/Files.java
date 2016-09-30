package com.example.connectdrive;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Files extends Fragment {

ArrayList<String> listfile;
    ArrayAdapter adapter;
    ListView filelistview;
    public Files() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_files, container, false);
        filelistview=(ListView)view.findViewById(R.id.files_list_view);
        // Inflate the layout for this fragment
        Bundle bundle=getArguments();
        String filetype=bundle.getString("FileType");
        if(filetype.equals("Photos"))
        {
            listfile=new ArrayList<>();
            listfile.add("profile.jpg");
            listfile.add("firstpage.gif");
            listfile.add("Newcar.jpg");
            listfile.add("Apple System.png");
            listfile.add("New Office.jpg");
            listfile.add("taj.jpg");
            adapter=new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,listfile);
            filelistview.setAdapter(adapter);
            filelistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(getActivity(),"Downloaded "+listfile.get(position),Toast.LENGTH_LONG).show();
                }
            });
        }
        else if(filetype.equals("Videos"))
        {
            listfile=new ArrayList<>();
            listfile.add(" Android tutorial.mp4");
            listfile.add("Java tutorial.mp4");
            listfile.add("Json.mp4");
            adapter=new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,listfile);
            filelistview.setAdapter(adapter);
            filelistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(getActivity(),"Downloaded "+listfile.get(position),Toast.LENGTH_LONG).show();
                }
            });

        } else if (filetype.equals("Pdf"))
                 {
                     listfile=new ArrayList<>();
                     listfile.add("Resume.pdf");
                     listfile.add("payment.pdf");
                     listfile.add("Offer Letter.pdf");
                     listfile.add("Tickets.pdf");
                     listfile.add("Reservation.pdf");
                     listfile.add("Gabfest Data.pdf");
                     adapter=new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,listfile);
                     filelistview.setAdapter(adapter);
                     filelistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                         @Override
                         public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                             Toast.makeText(getActivity(),"Downloaded "+listfile.get(position),Toast.LENGTH_LONG).show();
                         }
                     });

        }
        else if(filetype.equals("Docs"))
        {
            listfile=new ArrayList<>();
            listfile.add("maven.doc");
            listfile.add("Readme.txt");
            listfile.add("gabfest tech.doc");
            listfile.add("Offer letter.doc");
            listfile.add("Salary Details.doc");
            listfile.add("Employees.doc");
            adapter=new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,listfile);
            filelistview.setAdapter(adapter);
            filelistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(getActivity(),"Downloaded "+listfile.get(position),Toast.LENGTH_LONG).show();
                }
            });

        }
        else
        {
            listfile=new ArrayList<>();
            listfile.add("project.zip");
            listfile.add("connect.sketch");
            listfile.add("My Files.rar");
            listfile.add("gmail.apk");
            listfile.add("ConnectEZ.apk");
            listfile.add("My Link.html");
            adapter=new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,listfile);
            filelistview.setAdapter(adapter);
            filelistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(getActivity(),"Downloaded "+listfile.get(position),Toast.LENGTH_LONG).show();
                }
            });
        }


        return view;
    }

}
