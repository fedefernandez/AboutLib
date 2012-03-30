package com.projectsexception.about.tasks;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

public class LicenseTask extends AsyncTask<TextView, Void, String> {
    
    private Context context;
    private TextView textView;

    public LicenseTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(TextView... params) {
        textView = params[0];
        return readEula();
    }
    
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        textView.setText(result);
    }
    
    private String readEula() {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(context.getAssets().open("EULA")));
            String line;
            StringBuilder buffer = new StringBuilder();
            while ((line = in.readLine()) != null) buffer.append(line).append('\n');
            return buffer.toString();
        } catch (IOException e) {
            return "";
        } finally {
            closeStream(in);
        }
    }

    /**
     * Closes the specified stream.
     *
     * @param stream The stream to close.
     */
    private void closeStream(Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                // Ignore
            }
        }
    }
    
}