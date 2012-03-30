package com.projectsexception.about;

import java.util.Map;

public class Application {
    
    private String title;
    private Map<String, String> summaries;
    private String packageId;    
    private String icon;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Map<String, String> getSummaries() {
        return summaries;
    }

    public void setSummaries(Map<String, String> summaries) {
        this.summaries = summaries;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

}
