/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dhbw.webprog;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 *
 * @author DSemling
 */
public class GoogleTrendsAPI {

    //Hier richtige Logik implementieren
    public String  gibZufaelligeFrageZuKategorie(String thema) {
        if(thema.equals("Was")) {
            return "was passierte am";
        }
        
        if(thema.equals("Wann")) {
            return "wann gab es das erste";
        }
        
        if(thema.equals("Wo")) {
            return "wo gab es das erste";
        }
        return "";
    }
}
