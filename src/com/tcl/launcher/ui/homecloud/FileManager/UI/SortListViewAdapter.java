package com.tcl.launcher.ui.homecloud.FileManager.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.tcl.launcher.R;

public class SortListViewAdapter extends BaseAdapter {  
  
    private LayoutInflater mInflater;  
    private ViewHolder holder;  
    private String [] item_list;
    private int current_sel_index;
  
    public SortListViewAdapter(Context context, String[] l) 
    {  
        this.mInflater = LayoutInflater.from(context);  
        this.item_list = l;
        current_sel_index = 0;
    }  
     
  
    public void select(int position) 
    {  
        current_sel_index = position;
        notifyDataSetChanged();  
    }  
  
    @Override  
    public int getCount() 
    {  
        return item_list.length;  
    }  
  
    @Override  
    public Object getItem(int position) 
    {  
        return item_list[position];  
    }  
  
    @Override  
    public long getItemId(int position) 
    {  
        return position;  
    }  
  
    @Override  
    public View getView(int position, View convertView, ViewGroup parent) 
    {  
        if (convertView == null) 
        {  
            holder = new ViewHolder();  
            convertView = mInflater.inflate(R.layout.hdfilter_item_sel_view, null);  
            holder.radioBtn = (RadioButton) convertView.findViewById(R.id.item_sel_btn);  
            holder.radioBtn.setClickable(false);  
            holder.textView = (TextView) convertView.findViewById(R.id.item_text);  
            convertView.setTag(holder);  
        } 
        else 
        {  
            holder = (ViewHolder) convertView.getTag();  
        }
        
        if(current_sel_index == position)
        	holder.radioBtn.setChecked(true);
        else
        	holder.radioBtn.setChecked(false);
        holder.textView.setText(item_list[position]);  
        return convertView;  
    }  
  
    class ViewHolder 
    {  
        RadioButton radioBtn;  
        TextView textView;  
    }  
}  
