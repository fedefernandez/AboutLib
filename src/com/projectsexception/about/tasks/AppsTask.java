package com.projectsexception.about.tasks;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.projectsexception.about.Application;
import com.projectsexception.about.ApplicationsHandler;
import com.projectsexception.about.adapters.ApplicationsAdapter;

public class AppsTask extends AsyncTask<ApplicationsAdapter, Void, List<Application>> {
    
    protected static final String APPS_FILE = "apps.xml";
    
    private Context context;
    private ApplicationsAdapter adapter;
    
    public AppsTask(Context ctx) {
        this.context = ctx;
    }

    @Override
    protected List<Application> doInBackground(ApplicationsAdapter... params) {
        this.adapter = params[0];
        
        List<Application> applications = new ArrayList<Application>();
        
        boolean existeFichero; 
        FileInputStream fos;           
        try {
            fos = context.openFileInput(APPS_FILE);
            existeFichero = true;
        } catch (FileNotFoundException e) {                
            existeFichero = false;
        }
        
        if (!existeFichero) {
            //
            copyFromAssets();
        }
        
        try {
            fos = context.openFileInput(APPS_FILE);
            ApplicationsHandler handler = new ApplicationsHandler();
            handler.lanzaParseador(fos);
            applications = handler.getApplications();
        } catch (FileNotFoundException e) { 
            Log.e("AboutLib", "Can't load applications file");
        }
        
        return applications;
    }
    
    @Override
    protected void onPostExecute(List<Application> result) {
        super.onPostExecute(result);
        if (result != null && result.size() > 0) {
            adapter.setApplications(result);
            adapter.notifyDataSetChanged();
        }
    }
    
    private void copyFromAssets() {
        InputStream in = null;
        OutputStream out = null;
        try {
            in = context.getAssets().open(APPS_FILE);
            out = context.openFileOutput(APPS_FILE, Context.MODE_PRIVATE);
            copyFile(in, out);
            out.flush();
        } catch(Exception e) {
            Log.e("AboutLib", "Error: " + e.getMessage());
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
                Log.e("AboutLib", "Error: " + e.getMessage());
            }
        }
    }
    
    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
    }
}