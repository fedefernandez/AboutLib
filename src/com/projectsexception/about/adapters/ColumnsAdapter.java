package com.projectsexception.about.adapters;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.projectsexception.about.R;
import com.projectsexception.about.tasks.AppsTask;
import com.projectsexception.about.tasks.LicenseTask;
import com.viewpagerindicator.TitleProvider;

public class ColumnsAdapter extends PagerAdapter implements TitleProvider {
    
    private static final int[] TITLES = {
        R.string.about_comments_title,
        R.string.about_contact_title,
        R.string.about_apps_title
    };
    
    private Context context;
    private LayoutInflater inflater;        
    
    public ColumnsAdapter(Context ctx) {
        this.context = ctx;
        this.inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void destroyItem(View view, int position, Object obj) {
        ((ViewPager) view).removeView((View) obj);
    }

    @Override
    public void finishUpdate(View arg0) {
        
    }

    @Override
    public int getCount() {            
        return TITLES.length;
    }

    @Override
    public Object instantiateItem(View view, int position) {
        ViewGroup viewGroup = (ViewGroup) view;
        View newView;
        
        String appName = context.getString(R.string.about_app_name);
        
        if (position == 0) {
            newView = inflater.inflate(R.layout.about_comments, null);
            Button button = (Button) newView.findViewById(R.id.about_like);
            button.setText(context.getString(R.string.about_comments_like, appName));
            String[] lines = context.getResources().getStringArray(R.array.about_comments_pro_features);
            if (lines.length == 0) {
                // No se han especificado características de la versión PRO
                // Suponemos por tanto que no hay versión PRO
                newView.findViewById(R.id.about_getpro).setVisibility(View.GONE);                
                newView.findViewById(R.id.about_comments_pro_title).setVisibility(View.GONE);
                newView.findViewById(R.id.about_comments_pro_features).setVisibility(View.GONE);                
            } else {
                TextView textView = (TextView) newView.findViewById(R.id.about_comments_pro_features);
                for (String line : lines) {
                    textView.append(line);                        
                    textView.append("\n");                        
                }
            }
        } else if (position == 1) {
            newView = inflater.inflate(R.layout.about_contact, null);
            TextView textView = (TextView) newView.findViewById(R.id.about_contact_title);
            textView.setText(appName);
            textView = (TextView) newView.findViewById(R.id.about_contact_license_content);                
            new LicenseTask(context).execute(textView);
        } else {
            newView = inflater.inflate(R.layout.about_apps, null);
            ListView listView = (ListView) newView.findViewById(android.R.id.list);
            ApplicationsAdapter adapter = new ApplicationsAdapter(context);
            listView.setAdapter(adapter);
            new AppsTask(context).execute(adapter);
        }
        
        viewGroup.addView(newView);
        return newView;
    }

    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == ((View) obj);
    }

    @Override
    public void restoreState(Parcelable arg0, ClassLoader arg1) {
        
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public void startUpdate(View arg0) {            
    }

    @Override
    public String getTitle(int pos) {
        return context.getString(TITLES[pos]);
    }
    
}
