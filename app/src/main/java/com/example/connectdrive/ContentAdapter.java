package com.example.connectdrive;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by gabfestgts on 04/10/16.
 */
public class ContentAdapter extends BaseExpandableListAdapter {
   private Context ctx;
    private HashMap<String, List<String>> Contents_Category;
    private List<String> Content_List;

    public ContentAdapter(Context ctx, HashMap<String, List<String>> Contents_Category, List<String> Content_List)
    {
        this.ctx = ctx;
        this.Contents_Category = Contents_Category;
        this.Content_List = Content_List;
    }

    @Override
    public int getGroupCount() {
        return Content_List.size();
    }

    @Override
    public int getChildrenCount(int arg0) {
        return Contents_Category.get(Content_List.get(arg0)).size();
    }

    @Override
    public Object getGroup(int arg0) {
        return Content_List.get(arg0);
    }

    @Override
    public Object getChild(int parent, int child) {

        return Contents_Category.get(Content_List.get(parent)).get(child);
    }

    @Override
    public long getGroupId(int arg0) {
        return arg0;
    }

    @Override
    public long getChildId(int parent, int child) {
        return child;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int parent, boolean isExpanded, View convertView, ViewGroup parentview) {

        String group_title = (String) getGroup(parent);
        if(convertView == null){
            LayoutInflater inflator = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflator.inflate(R.layout.parent_layout,parentview,false);

        }
        TextView parent_textview = (TextView) convertView.findViewById(R.id.parent_txt);
        parent_textview.setTypeface(null, Typeface.BOLD);
        parent_textview.setText(group_title);

        return convertView;
    }

    @Override
    public View getChildView(int parent, int child, boolean lastChild, View convertView, ViewGroup parentView) {

        String child_title = (String) getChild(parent, child);
        if(convertView == null){

            LayoutInflater inflator = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflator.inflate(R.layout.child_layout, parentView, false);

        }

        TextView child_textview = (TextView) convertView.findViewById(R.id.child_txt);
        child_textview.setText(child_title);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }



}
