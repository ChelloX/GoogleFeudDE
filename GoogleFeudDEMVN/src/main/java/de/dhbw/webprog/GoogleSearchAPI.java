/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dhbw.webprog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

public class GoogleSearchAPI {

    private final String GOOGLE_API_1 = "https://www.google.com/complete/search?output=toolbar&q=";
    private final String GOOGLE_API_2 = "&hl=de";
    
    public String machWas() {
        return "hier";
    }
    
    public static void main(String args[]) {
        GoogleSearchAPI a = new GoogleSearchAPI();
        a.gibAntwortenZuString("Karlsruhe");
    }

    public HashMap<Integer,String> gibAntwortenZuString(String suchString) {
        suchString = suchString.replace(" ", "%20");
        Content content = null;

        try {
            content = Request.Get(GOOGLE_API_1 + suchString + GOOGLE_API_2).execute().returnContent();
        } catch (IOException ex) {
            Logger.getLogger(GoogleSearchAPI.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (content != null) {
            JSONObject document = XML.toJSONObject(content.toString());
            JSONObject toplevel = (JSONObject) document.get("toplevel");
            JSONArray completesuggestion = toplevel.getJSONArray("CompleteSuggestion");

            HashMap<Integer, String> antworten = new HashMap<Integer, String>();
            for (int i = 0; i < completesuggestion.length(); i++) {
                JSONObject t1 = (JSONObject) completesuggestion.get(i);
                JSONObject t2 = (JSONObject) t1.get("suggestion");
                antworten.put(i+1, t2.getString("data"));
            }
            return antworten;
        } else {
            return null;
        }
    }
}

