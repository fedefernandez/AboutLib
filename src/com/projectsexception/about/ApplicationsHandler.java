package com.projectsexception.about;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

public class ApplicationsHandler extends DefaultHandler {
    
    private List<Application> applications;
    
    private transient Application app;
    private transient Map<String, String> summaries;
    private transient boolean readingApp;
    private transient String lang;
    private transient boolean data;
    private transient StringBuilder buffer;
    
    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        applications = new ArrayList<Application>();
    }
    
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        if ("app".equals(getName(localName, qName))) {
            app = new Application();
            app.setTitle(attributes.getValue("title"));
            app.setPackageId(attributes.getValue("package"));
            app.setIcon(attributes.getValue("icon"));
            summaries = new HashMap<String, String>();
            readingApp = true;
        } else if (readingApp) {
            lang = getName(localName, qName);
            data = true;
            buffer = new StringBuilder();
        }
    }
    
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        if (data) {
            buffer.append(new String(ch, start, length));
        }
    }
    
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        if ("app".equals(getName(localName, qName))) {
            app.setSummaries(summaries);
            applications.add(app);
            readingApp = false;
        } else if (lang != null && lang.equals(getName(localName, qName))) {
            data = false;
            summaries.put(lang, buffer.toString().trim());
        }
    }

    public void lanzaParseador(InputStream stream) {
        if (stream != null) {
            try {
                final InputSource source = new InputSource(stream);
                SAXParserFactory spf = SAXParserFactory.newInstance();
                SAXParser sp = spf.newSAXParser();
                XMLReader xr = sp.getXMLReader();
                xr.setContentHandler(this);
                xr.parse(source);
            } catch (SAXException e) {
                Log.e("AboutLib", "Exception: " + e.getMessage());
            } catch (Exception e) {
                Log.e("AboutLib", "Exception: " + e.getMessage());
            } catch (Error e) {
                Log.e("AboutLib", "Exception: " + e.getMessage());
            } finally {
                try {
                    stream.close();
                } catch (IOException e) {
                    // No hacer nada
                }                
            }
        }
    }

    public List<Application> getApplications() {
        return applications;
    }
    
    private String getName(String localName, String qName) {
        if (localName == null || localName.length() == 0) {
            return qName;
        }
        return localName;
    }

}
