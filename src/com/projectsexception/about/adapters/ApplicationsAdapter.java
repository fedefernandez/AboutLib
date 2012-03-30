package com.projectsexception.about.adapters;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.projectsexception.about.Application;
import com.projectsexception.about.R;
import com.projectsexception.about.image.ImageLoader;

public class ApplicationsAdapter extends BaseAdapter {
    
    private static final String DEFAULT_LANG = "es";
    
    private LayoutInflater inflater;
    private List<Application> applications;
    public ImageLoader imageLoader; 
    
    public ApplicationsAdapter(Context context) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.applications = new ArrayList<Application>();
        this.imageLoader=new ImageLoader(context.getApplicationContext());
    }
    
    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }

    @Override
    public int getCount() {
        return applications.size();
    }

    @Override
    public Object getItem(int position) {
        return applications.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Application app = (Application) getItem(position);
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.about_app, null);                
        }
        ((TextView) view.findViewById(R.id.app_title)).setText(app.getTitle());
        String language = Locale.getDefault().getLanguage();
        if (app.getSummaries().containsKey(language)) {
            ((TextView) view.findViewById(R.id.app_summary)).setText(app.getSummaries().get(language));
        } else {
            ((TextView) view.findViewById(R.id.app_summary)).setText(app.getSummaries().get(DEFAULT_LANG));
        }
        if (app.getIcon() != null && app.getIcon().length() > 0) {
            imageLoader.displayImage(app.getIcon(), (ImageView) view.findViewById(R.id.app_image));
        }
        return view;
    }
    
}