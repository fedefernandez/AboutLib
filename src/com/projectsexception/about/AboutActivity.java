package com.projectsexception.about;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Toast;

import com.projectsexception.about.adapters.ColumnsAdapter;
import com.viewpagerindicator.TitlePageIndicator;

public class AboutActivity extends Activity {
    
    private ViewPager viewPager;
    private ColumnsAdapter myAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        myAdapter = new ColumnsAdapter(this);
        viewPager.setAdapter(myAdapter);
        
        // Start at a custom position
        viewPager.setCurrentItem(0);
        
        // Parte nueva: Añadimos el Adapter al indicador
        TitlePageIndicator titleIndicator = (TitlePageIndicator)findViewById(R.id.indicator);
        titleIndicator.setViewPager(viewPager);
    }
    
    public void aboutLike(View view) {
        if (view.getId() == R.id.about_like) {
            Dialog dialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar);
            dialog.setContentView(R.layout.about_like_dialog);
            View.OnClickListener listener = new View.OnClickListener() {                
                @Override
                public void onClick(View v) {
                    aboutLike(v);
                }
            };
            dialog.findViewById(R.id.about_like_market).setOnClickListener(listener);
            dialog.findViewById(R.id.about_like_mail).setOnClickListener(listener);
            dialog.findViewById(R.id.about_like_facebook).setOnClickListener(listener);
            dialog.findViewById(R.id.about_like_twitter).setOnClickListener(listener);
            dialog.show();
        } else if (view.getId() == R.id.about_like_market) {           
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName()));
            startActivity(intent);
        } else if (view.getId() == R.id.about_like_mail) {
            Intent intent = new Intent(Intent.ACTION_SEND);            
            intent.setType("plain/text");
            intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.about_mail_subject));     
            intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.about_mail_body));
            startActivity(intent);
        } else if (view.getId() == R.id.about_like_facebook) {
            Uri uri = Uri.parse("http://m.facebook.com/sharer.php?u=" +
                    getString(R.string.about_facebook_url) + "&t=" + 
                    getString(R.string.about_facebook_title).replaceAll(" ", "+"));
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        } else if (view.getId() == R.id.about_like_twitter) {
            Intent intent = new Intent(Intent.ACTION_SEND);            
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.about_twitter_msg));    
            startActivity(intent);            
        }
    }
    
    public void aboutProblem(View view) {
        if (view.getId() == R.id.about_problem) {
            Dialog dialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar);
            dialog.setContentView(R.layout.about_problem_dialog);
            dialog.findViewById(R.id.about_problem_mail).setOnClickListener(new View.OnClickListener() {                
                @Override
                public void onClick(View v) {
                    aboutProblem(v);
                }
            });
            dialog.show();
        } else if (view.getId() == R.id.about_problem_mail) {
            Intent intent = new Intent(Intent.ACTION_SEND);            
            intent.setType("plain/text");
            intent.putExtra(Intent.EXTRA_EMAIL, new String[] {getString(R.string.about_problem_mail)});     
            intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.about_problem_subject));     
            intent.putExtra(Intent.EXTRA_TEXT, "");
            startActivity(intent);
        }
    }
    
    public void aboutPro(View view) {
        String proPackage = getString(R.string.about_pro_package);
        if (proPackage == null || proPackage.length() == 0) {
            // No se ha especificado el paquete de la versión PRO
            // Suponemos que aún no está disponible
            Toast.makeText(this, R.string.about_comments_pro_comming_soon, Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getString(R.string.about_pro_package)));
            startActivity(intent);
        }
    }
    
    public void openContact(View view) {
        Uri uri;
        if (view.getId() == R.id.about_contact_button_1) {
            uri = Uri.parse(getString(R.string.about_contact_button_1_url));
        } else if (view.getId() == R.id.about_contact_button_2) {
            uri = Uri.parse(getString(R.string.about_contact_button_2_url));
        } else {
            uri = null;
        }
        
        if (uri != null) {
            Intent myIntent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(myIntent);
        }
    }
    
}