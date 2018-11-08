/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dhbw.webprog;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Daniel
 */
public class GoogleTrendsAPITest {

    @Test
    public void testFrageZuKategorie() {
        GoogleTrendsAPI api = new GoogleTrendsAPI();

        String was = api.gibZufaelligeFrageZuKategorie(Statics.getWAS());
        String wann = api.gibZufaelligeFrageZuKategorie(Statics.getWANN());
        String wo = api.gibZufaelligeFrageZuKategorie(Statics.getWO());

        assertNotNull(was);
        assertNotNull(wann);
        assertNotNull(wo);
        assertTrue(!was.equals(""));
        assertTrue(!wann.equals(""));
        assertTrue(!wo.equals(""));
    }
}
