/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dhbw.webprog;

public class GoogleTrendsAPI {

    //Hier richtige Logik implementieren
    public String  gibZufaelligeFrageZuKategorie(String thema) {
        if(thema.equals(Statics.getWAS())) {
            return "was passierte am";
        }
        
        if(thema.equals(Statics.getWANN())) {
            return "wann gab es das erste";
        }
        
        if(thema.equals(Statics.getWO())) {
            return "wo gab es das erste";
        }
        return "";
    }
}
