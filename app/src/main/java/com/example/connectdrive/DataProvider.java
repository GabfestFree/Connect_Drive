package com.example.connectdrive;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by gabfestgts on 04/10/16.
 */
public class DataProvider {

    public static HashMap<String, List<String>> getInfo()
    {
        HashMap<String, List<String>> ContentDetails = new HashMap<String, List<String>>();
        List<String> Photos_content = new ArrayList<String>();
        Photos_content.add("profile.jpg");
        Photos_content.add("firstpage.gif");
        Photos_content.add("Newcar.jpg");
        Photos_content.add("Apple System.png");
        Photos_content.add("New Office.jpg");
        Photos_content.add("taj.jpg");

        List<String> Videos_content = new ArrayList<String>();
        Videos_content.add(" Android tutorial.mp4");
        Videos_content.add("Java tutorial.mp4");
        Videos_content.add("Json.mp4");

        List<String> Docs_content = new ArrayList<String>();
         Docs_content.add("maven.doc");
         Docs_content.add("Readme.txt");
         Docs_content.add("gabfest tech.doc");
         Docs_content.add("Offer letter.doc");
         Docs_content.add("Salary Details.doc");
         Docs_content.add("Employees.doc");

        List<String> Pdf_content = new ArrayList<String>();
        Pdf_content.add("Resume.pdf");
        Pdf_content.add("payment.pdf");
        Pdf_content.add("Offer Letter.pdf");
        Pdf_content.add("Tickets.pdf");
        Pdf_content.add("Reservation.pdf");
        Pdf_content.add("Gabfest Data.pdf");

        List<String> Others_content = new ArrayList<String>();
        Others_content.add("project.zip");
        Others_content.add("connect.sketch");
        Others_content.add("My Files.rar");
        Others_content.add("gmail.apk");
        Others_content.add("ConnectEZ.apk");
        Others_content.add("My Link.html");

        ContentDetails.put("Photos", Photos_content);
        ContentDetails.put("Videos", Videos_content);
        ContentDetails.put("Docs", Docs_content);
        ContentDetails.put("Pdf", Pdf_content);
        ContentDetails.put("Others", Others_content);

        return ContentDetails;


    }


}