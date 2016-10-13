package com.example.connectdrive;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by gabfestgts on 04/10/16.
 */
public class DataProvider {


    public static HashMap<String, List<String>> getInfo()
    {
    int photosattach;
        int pdfattach;
        int videoattach;
        int docattach;
        int otherattach;


        HashMap<String, List<String>> ContentDetails = new HashMap<String, List<String>>();
        ArrayList<String> fileslist = new ArrayList<String>();
        ArrayList<String> Photos_content = new ArrayList<String>();
        ArrayList<String> Videos_content = new ArrayList<String>();
        ArrayList<String> Docs_content = new ArrayList<String>();
       ArrayList<String> Pdf_content = new ArrayList<String>();
        ArrayList<String> Others_content = new ArrayList<String>();
        try {
            Cursor photo = Gmail_Fragment.connectDatabase.rawQuery("SELECT DISTINCT attachmentName FROM attachment WHERE attachmentName LIKE '%.jpg' OR attachmentName LIKE '%.png' ", null);
            photosattach = photo.getColumnIndex("attachmentName");

            photo.moveToFirst();
            while (photo != null) {
                // Log.i("Attachment Name", c.getString(attachmentname));
                fileslist.add(photo.getString(photosattach));
                photo.moveToNext();
            }
        }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        try {
            Cursor  pdf= Gmail_Fragment.connectDatabase.rawQuery("SELECT DISTINCT attachmentName FROM attachment WHERE attachmentName LIKE '%.pdf'", null);
            pdfattach = pdf.getColumnIndex("attachmentName");

            pdf.moveToFirst();
            while (pdf != null) {
                // Log.i("Attachment Name", c.getString(attachmentname));
                Pdf_content.add(pdf.getString( pdfattach));
                pdf.moveToNext();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        try {
            Cursor  doc= Gmail_Fragment.connectDatabase.rawQuery("SELECT DISTINCT attachmentName FROM attachment WHERE attachmentName LIKE '%.txt'", null);
            docattach = doc.getColumnIndex("attachmentName");

            doc.moveToFirst();
            while (doc != null) {
                // Log.i("Attachment Name", c.getString(attachmentname));
                Docs_content.add(doc.getString(docattach));
               doc.moveToNext();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        try {
            Cursor  video= Gmail_Fragment.connectDatabase.rawQuery("SELECT DISTINCT attachmentName FROM attachment WHERE attachmentName LIKE '%.txt'", null);
            videoattach = video.getColumnIndex("attachmentName");

            video.moveToFirst();
            while (video != null) {
                // Log.i("Attachment Name", c.getString(attachmentname));
               Videos_content.add(video.getString(videoattach));
                video.moveToNext();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        try {
            Cursor  others= Gmail_Fragment.connectDatabase.rawQuery("SELECT DISTINCT attachmentName FROM attachment WHERE attachmentName NOT LIKE '%.pdf' AND attachmentName NOT LIKE '%.jpg' AND attachmentName NOT LIKE '%.png' AND attachmentName NOT LIKE '%.txt'", null);
            otherattach =others.getColumnIndex("attachmentName");

            others.moveToFirst();
            while (others != null) {
                // Log.i("Attachment Name", c.getString(attachmentname));
                Others_content.add(others.getString(otherattach));
                others.moveToNext();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }




            /*
           if(fileslist.get(i).endsWith(".png")||fileslist.get(i).endsWith(".jpg")||fileslist.get(i).endsWith(".jpeg")||fileslist.get(i).endsWith(".JPG")||fileslist.get(i).endsWith(".gif")||fileslist.get(i).endsWith(".svg"))
            {
                Photos_content.add(fileslist.get(i));
            }
           if(fileslist.get(i).endsWith(".mp4")||fileslist.get(i).endsWith(".MP4")||fileslist.get(i).endsWith(".3gp")||fileslist.get(i).endsWith(".mkv")||fileslist.get(i).endsWith(".webm")||fileslist.get(i).endsWith(".avi")||fileslist.get(i).endsWith(".wmv")||fileslist.get(i).endsWith(".mov"))
            {
                Videos_content.add(fileslist.get(i));
            }
            if (fileslist.get(i).endsWith(".doc") || fileslist.get(i).endsWith(".txt") || fileslist.get(i).endsWith(".docx") || fileslist.get(i).endsWith(".xls") || fileslist.get(i).endsWith(".xlsx")) {

                Docs_content.add(fileslist.get(i));
            }
            if(!fileslist.get(i).endsWith(".pdf")||!fileslist.get(i).endsWith(".png")||!fileslist.get(i).endsWith(".jpg")||!fileslist.get(i).endsWith(".jpeg")||!fileslist.get(i).endsWith(".JPG")||!fileslist.get(i).endsWith(".gif")||!fileslist.get(i).endsWith(".svg")||
            !fileslist.get(i).endsWith(".mp4")||!fileslist.get(i).endsWith(".MP4")||!fileslist.get(i).endsWith(".3gp")||!fileslist.get(i).endsWith(".mkv")||!fileslist.get(i).endsWith(".webm")||!fileslist.get(i).endsWith(".avi")||!fileslist.get(i).endsWith(".wmv")||!fileslist.get(i).endsWith(".mov")
                    ||!fileslist.get(i).endsWith(".doc") || !fileslist.get(i).endsWith(".txt") ||! fileslist.get(i).endsWith(".docx") || !fileslist.get(i).endsWith(".xls") || !fileslist.get(i).endsWith(".xlsx"))
            {
                Others_content.add(fileslist.get(i));

            }

        }
*/
      /*  Photos_content.add("profile.jpg");
        Photos_content.add("firstpage.gif");
        Photos_content.add("Newcar.jpg");
        Photos_content.add("Apple System.png");
        Photos_content.add("New Office.jpg");
        Photos_content.add("taj.jpg");
*/

        /*
        Videos_content.add(" Android tutorial.mp4");
        Videos_content.add("Java tutorial.mp4");
        Videos_content.add("Json.mp4");


         Docs_content.add("maven.doc");
         Docs_content.add("Readme.txt");
         Docs_content.add("gabfest tech.doc");
         Docs_content.add("Offer letter.doc");
         Docs_content.add("Salary Details.doc");
         Docs_content.add("Employees.doc");

/*
        Pdf_content.add("Resume.pdf");
        Pdf_content.add("payment.pdf");
        Pdf_content.add("Offer Letter.pdf");
        Pdf_content.add("Tickets.pdf");
        Pdf_content.add("Reservation.pdf");
        Pdf_content.add("Gabfest Data.pdf");


        Others_content.add("project.zip");
        Others_content.add("connect.sketch");
        Others_content.add("My Files.rar");
        Others_content.add("gmail.apk");
        Others_content.add("ConnectEZ.apk");
        Others_content.add("My Link.html");*/


        ContentDetails.put("Photos", fileslist);
        ContentDetails.put("Videos", Videos_content);
        ContentDetails.put("Docs", Docs_content);
        ContentDetails.put("Pdf", Pdf_content);
        ContentDetails.put("Others", Others_content);


        return ContentDetails;


    }


}