package com.example.connectdrive;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gabfestgts on 04/10/16.
 */
public class ContentAdapter extends BaseExpandableListAdapter {
    private Activity context;
    private Map<String, List<String>> ParentListItems;
    private List<String> Items;

    public ContentAdapter(Activity ctx, HashMap<String, List<String>> Contents_Category, List<String> Content_List) {
        this.context = ctx;
        this.ParentListItems = Contents_Category;
        this.Items = Content_List;
    }

    public Object getChild(int groupPosition, int childPosition) {
        return ParentListItems.get(Items.get(groupPosition)).get(childPosition);
    }

    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }


    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View ListView, ViewGroup parent) {
        final String CoursesName = (String) getChild(groupPosition, childPosition);
        LayoutInflater inflater = context.getLayoutInflater();

        if (ListView == null) {
            ListView = inflater.inflate(R.layout.child_layout, null);
        }

        TextView item = (TextView) ListView.findViewById(R.id.child_txt);

        item.setText(CoursesName);
        return ListView;
    }

    public int getChildrenCount(int groupPosition) {
        return ParentListItems.get(Items.get(groupPosition)).size();
    }

    public Object getGroup(int groupPosition) {
        return Items.get(groupPosition);
    }

    public int getGroupCount() {
        return Items.size();
    }

    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    public View getGroupView(int groupPosition, boolean isExpanded,
                             View ListView, ViewGroup parent) {
        String CoursesFull = (String) getGroup(groupPosition);
        if (ListView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ListView = infalInflater.inflate(R.layout.parent_layout,null);
        }
        TextView item = (TextView) ListView.findViewById(R.id.folderView);
        ImageView folder=(ImageView) ListView.findViewById(R.id.fileiconview);
        if(CoursesFull.equals("Photos"))
        {

           folder.setImageResource(R.drawable.ic_photo_library_black_24dp);
        }
        if(CoursesFull.equals("Videos"))
        {

            folder.setImageResource(R.drawable.ic_video_library_black_24dp);
        }
        if(CoursesFull.equals("Docs"))
        {

            folder.setImageResource(R.drawable.ic_note_black_24dp);
        }
        if(CoursesFull.equals("Pdf"))
        {

            folder.setImageResource(R.drawable.ic_picture_as_pdf_black_24dp);
        }
        if(CoursesFull.equals("Others"))
        {

            folder.setImageResource(R.drawable.ic_queue_black_24dp);
        }
        item.setText(CoursesFull);
        return ListView;
    }

    public boolean hasStableIds() {
        return true;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}


