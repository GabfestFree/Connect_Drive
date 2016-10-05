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
        Photos_content.add("gdgeg.jpg");
        Photos_content.add("gddgf.jpg");
        Photos_content.add("gddgfd.png");
        Photos_content.add("gdgsa.jpg");

        List<String> Videos_content = new ArrayList<String>();
        Videos_content.add("egwgw.mp4");
        Videos_content.add("egwage.mkv");
        Videos_content.add("egwgdaeg.mp4");
        Videos_content.add("egwgwage.mp4");

        List<String> Docs_content = new ArrayList<String>();
        Docs_content.add("weurhiu.txt");
        Docs_content.add("weurhifgn.doc");
        Docs_content.add("weurhfgb.txt");
        Docs_content.add("weuxgrhiu.");

        List<String> Pdf_content = new ArrayList<String>();
        Pdf_content.add("sfdgs.pdf");
        Pdf_content.add("sfdgfgs.pdf");
        Pdf_content.add("gfdhsfdgs.pdf");

        List<String> Others_content = new ArrayList<String>();
        Others_content.add("gsdhsh.zip");
        Others_content.add("gsdhsh.apk");
        Others_content.add("gsdhsh.sketch");
        Others_content.add("gsdhsh.zip");

        ContentDetails.put("Photos", Photos_content);
        ContentDetails.put("Videos", Videos_content);
        ContentDetails.put("Docs", Docs_content);
        ContentDetails.put("Pdf", Pdf_content);
        ContentDetails.put("Others", Others_content);

        return ContentDetails;


    }


}
